package com.codepath.apps.activities;

import java.util.ArrayList;

import org.apache.http.client.HttpResponseException;
import org.json.JSONArray;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.codepath.apps.adapters.EndlessScrollListener;
import com.codepath.apps.adapters.TweetArrayAdapter;
import com.codepath.apps.models.Tweet;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.simpletwitterclient.TwitterApplication;
import com.codepath.apps.simpletwitterclient.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;
//import org.apache.http.Header;
public class TimelineActivity extends Activity {
	private TwitterClient client;
	private ArrayList<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweets;
	private PullToRefreshListView lvTweets;
	private final int REQUEST_CODE = 20;
	private final int REQUEST_CODE_DETAILED = 30;
	public static String TWEET = "tweet";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		client = TwitterApplication.getRestClient();
		lvTweets = (PullToRefreshListView) findViewById(R.id.lvTweets);
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(this, tweets);
		lvTweets.setAdapter(aTweets);
		setupListViewListeners();                
		Log.d("debug", "regular population");
		populateTimeline(0);
	}

	
	private void setupListViewListeners() {
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
		    @Override
		    public void onLoadMore(int page, int totalItemsCount) {
	                // Triggered only when new data needs to be appended to the list
	                // Add whatever code is needed to append new items to your AdapterView
		    	populateTimeline(page); 
	                // or customLoadMoreDataFromApi(totalItemsCount); 
		    }
	
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				
			}
        });
		
        lvTweets.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call listView.onRefreshComplete() when
                // once the network request has completed successfully.
    			Log.d("debug", "refreshing list");
            	populateTimeline(0);
            }
        });
        
        lvTweets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    		@Override
    		public void onItemClick(AdapterView<?> parent, View view, 
    				int position, long id) {
    			// bring up edit activity with items[position]
    			Intent i = new Intent(TimelineActivity.this, DetailActivity.class);
    			Tweet selectedTweet = (Tweet) tweets.get(position);
    			i.putExtra(TWEET, selectedTweet);
    			startActivityForResult(i, REQUEST_CODE_DETAILED);
    		}
		});

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.compose, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	

    @Override
    protected void onResume() {
		Log.d("debug", "on resume");
	    populateTimeline(0);
        super.onResume();
    }

    
    public void onCompose(MenuItem mi) {
    	Intent i = new Intent(this, ComposeActivity.class);
		startActivityForResult(i, REQUEST_CODE);
	}
	

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("debug", "got intent result");
    	if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
    		Log.d("debug", "updating new timeline");
    		populateTimeline(0);
    	}
    }
    

	private void populateTimeline(int page) {
		if(!isNetworkAvailable()) {
			Toast.makeText(this, "Internet is not connected, showing older tweets", Toast.LENGTH_SHORT).show();
			// XXX: load offline from sqlite			
			return;
		}

		RequestParams params = new RequestParams();
		if(page == 0) {
			// clear existing data
			aTweets.clear();
			/*
			new Delete().from(Tweet.class).execute(); 
			new Delete().from(User.class).execute(); 
			*/
			params.put("since_id", "1");
			Log.d("debug", "since_id=1");
		} else {
			params.put("max_id", Long.toString(Tweet.getLowestUid()-1));
			//Log.d("debug", "max_id=" + Tweet.getLowestUid());
		}
				
		client.getHomeTimeline(params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, JSONArray jsonArray) {
				//Log.d("INFO", jsonArray.toString());
				Log.d("INFO", "success response in get");
				aTweets.addAll(Tweet.fromJSONArray(jsonArray));
            	lvTweets.onRefreshComplete();
			}

        	@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("error", s.toString());
				//XXX: check for 429
				HttpResponseException hre = (HttpResponseException) e;
				int statusCode = hre.getStatusCode();
				Log.d("error", "status code=" + statusCode);
				if(statusCode == 429) {
					Toast.makeText(getBaseContext(), "Twitter is rate limiting this app,  please be patient", Toast.LENGTH_SHORT).show();
				}
			}
			
		});
	}
	
	/* Check network connectivity */
	private Boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
	}

}
