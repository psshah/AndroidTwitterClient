package com.codepath.apps.fragments;

import java.util.ArrayList;

import org.apache.http.client.HttpResponseException;
import org.json.JSONArray;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
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

public class TweetsListFragment extends Fragment {
	public enum RequestType {
		HOME_TIMELINE, MENTIONS_TIMELINE, USER_TIMELINE
	}	

	private ArrayList<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweets;
	private PullToRefreshListView lvTweets;
	private TwitterClient client;
	private RequestType requestType;
	private long userId;
	//private ImageButton ivProfileImage;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Non-view initialization
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(getActivity(), tweets);	// use getActivity() here
		client = TwitterApplication.getRestClient();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_tweets_list, container, false); // do not inflate yet
		lvTweets = (PullToRefreshListView) v.findViewById(R.id.lvTweets);
		lvTweets.setAdapter(aTweets);
		setupListViewListeners();
		//ivProfileImage = (ImageButton) v.findViewById(R.id.ivProfileImage);
		//setupImageButtomListeners();
		return v;
	}
	
	public void addAll(ArrayList<Tweet> tweets) {
		aTweets.addAll(tweets);
	}

	public void clearAll() {
		aTweets.clear();
	}
	
    
	public void setRequestType(RequestType type) {
		this.requestType = type;
	}
	
	public void setUserId(long userid) {
		userId = userid;
	}
	
	public void populateTimeline(int page) {
		//Log.d("debug", "page=" + page);
		/*Re-enable
		if(!isNetworkAvailable(getActivity())) {
			Toast.makeText(this, "Internet is not connected, showing older tweets", Toast.LENGTH_SHORT).show();
			// XXX: load offline from sqlite			
			return;
		}
		*/

		RequestParams params = new RequestParams();
		if(page == 0) {
			// clear existing data
			clearAll();
			params.put("since_id", "1");
			Log.d("debug", "since_id=1");
		} else {
			params.put("max_id", Long.toString(Tweet.getLowestUid()-1));
			//Log.d("debug", "max_id=" + Tweet.getLowestUid());
		}
				
		switch (this.requestType) {
		case HOME_TIMELINE:
		client.getHomeTimeline(params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, JSONArray jsonArray) {
				//Log.d("INFO", jsonArray.toString());
				addAll(Tweet.fromJSONArray(jsonArray));
            	/* Re-enable
            	 * lvTweets.onRefreshComplete();
            	 */
			}

        	@Override
			public void onFailure(Throwable e, String s) {
        		handleFailure(e, s);
        	}
		});
			break;
		case MENTIONS_TIMELINE:
			client.getMentionsTimeline(params, new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(int arg0, JSONArray jsonArray) {
					//Log.d("INFO", jsonArray.toString());
					addAll(Tweet.fromJSONArray(jsonArray));
	            	/* Re-enable
	            	 * lvTweets.onRefreshComplete();
	            	 */
				}

	        	@Override
				public void onFailure(Throwable e, String s) {
	        		handleFailure(e, s);
				}			
			});
		case USER_TIMELINE:
			if(userId != -1) {
				Log.d("DEBUG", "adding userid= " + userId);
				params.put("user_id", Long.toString(userId));
			}
			client.getUserTimeline(params, new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(int arg0, JSONArray jsonArray) {
					addAll(Tweet.fromJSONArray(jsonArray));
				}

	        	@Override
				public void onFailure(Throwable e, String s) {
	        		handleFailure(e, s);
				}			
			});

		default:
			break;
		}
	}

	private void handleFailure(Throwable e, String s) {
		Log.d("debug", e.toString());
		Log.d("error", s.toString());
		//XXX: check for 429
		HttpResponseException hre = (HttpResponseException) e;
		int statusCode = hre.getStatusCode();
		Log.d("error", "status code=" + statusCode);
		if(statusCode == 429) {
			/*
			 * Re-enable: how to pass context for toast
			 */
			Toast.makeText(getActivity().getBaseContext(), "Twitter is rate limiting this app,  please be patient", Toast.LENGTH_SHORT).show();
		}
	}
	
	
	private void setupListViewListeners() {
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
		    @Override
		    public void onLoadMore(int page, int totalItemsCount) {
	                // Triggered only when new data needs to be appended to the list
	                // Add whatever code is needed to append new items to your AdapterView
		    		populateTimeline(page);
		    }
	
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				
			}
        });
	}	

}
