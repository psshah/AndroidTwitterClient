package com.codepath.apps.activities;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.codepath.apps.fragments.HomeTimelineFragment;
import com.codepath.apps.fragments.MentionsTimelineFragment;
import com.codepath.apps.listeners.FragmentTabListener;
import com.codepath.apps.restclienttemplate.R;
//import org.apache.http.Header;


public class TimelineActivity extends FragmentActivity {
	private final int REQUEST_CODE = 20;
	private final int REQUEST_CODE_DETAILED = 30;
	public static String TWEET = "tweet";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		//setupListViewListeners();         
		setupTabs();
	}

	private void setupTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		Tab tab1 = actionBar
			.newTab()
			.setText("Home")
			.setIcon(R.drawable.ic_home)
			.setTag("HomeTimelineFragment")
			.setTabListener(
				new FragmentTabListener<HomeTimelineFragment>(R.id.flContainer, this, "first",
								HomeTimelineFragment.class));

		actionBar.addTab(tab1);
		actionBar.selectTab(tab1);

		Tab tab2 = actionBar
			.newTab()
			.setText("Mentions")
			.setIcon(R.drawable.ic_mentions)
			.setTag("MentionsTimelineFragment")
			.setTabListener(
			    new FragmentTabListener<MentionsTimelineFragment>(R.id.flContainer, this, "second",
								MentionsTimelineFragment.class));

		actionBar.addTab(tab2);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.compose, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	

	/* 
	 * Re-enable
    @Override
    protected void onResume() {
		Log.d("debug", "on resume");
	    populateTimeline(0);
        super.onResume();
    }
	 */

    
    public void onCompose(MenuItem mi) {
    	Intent i = new Intent(this, ComposeActivity.class);
		startActivityForResult(i, REQUEST_CODE);
	}
    
    public void onProfileView(MenuItem mi) {
    	Intent i = new Intent(this, ProfileActivity.class);
		i.putExtra("started_from", "menu");
		startActivity(i);
	}

    /*
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
        */



    /*
	 * Re-enable
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("debug", "got intent result");
    	if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
    		Log.d("debug", "updating new timeline");
    		populateTimeline(0);
    	}
    }
     */
    
}
