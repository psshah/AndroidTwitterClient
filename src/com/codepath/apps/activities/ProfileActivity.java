package com.codepath.apps.activities;

import org.apache.http.client.HttpResponseException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.fragments.UserTimelineFragment;
import com.codepath.apps.models.User;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.simpletwitterclient.ConnectionMgr;
import com.codepath.apps.simpletwitterclient.TwitterApplication;
import com.codepath.apps.simpletwitterclient.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity {
	private TwitterClient client;
	private ImageView ivProfileImg;
	private TextView tvAccountUserName;
	private TextView tvTagline;
	private TextView tvFollowers;
	private TextView tvFollowing;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		client = TwitterApplication.getRestClient();
		User u = ((User) getIntent().getSerializableExtra("userprof"));
		attachFragment(u);
		initResources();
		if(getIntent().getStringExtra("started_from").equalsIgnoreCase("menu")) {
			loadProfileInfo();	
		} else {
			if (getIntent().getStringExtra("started_from").equalsIgnoreCase("imageclick")) {
				populateUserInfo(u);
			}
		}
	}

	private void attachFragment(User u) {
		long userid=-1;
		if(u != null) {
			userid = u.getUserid();
		}
    	// Magic to hot swap fragment one into the container
    	// Construct a fragment transaction
    	android.support.v4.app.FragmentTransaction ft = 
    			getSupportFragmentManager().beginTransaction();
    	UserTimelineFragment fragmentUserTimeline = UserTimelineFragment.newInstance(userid);
    	// Make changes to your fragments
    	ft.replace(R.id.flUserContainer, fragmentUserTimeline);
    	// Commit fragment transaction
    	ft.addToBackStack(null);	// without this line, it won't be added to history
    	ft.commit();
	}	

	private void initResources() {
		tvAccountUserName = (TextView) findViewById(R.id.tvAccountUserName);
		tvTagline = (TextView) findViewById(R.id.tvTagLine);
		tvFollowers = (TextView) findViewById(R.id.tvFollowers);
		tvFollowing = (TextView) findViewById(R.id.tvFollowing);
		ivProfileImg = (ImageView) findViewById(R.id.ivProfileImg);
	}

	
	private void loadProfileInfo() {
		if(!ConnectionMgr.isNetworkAvailable(this)) {
			Toast.makeText(this, "Internet is not connected, showing older tweets", Toast.LENGTH_SHORT).show();
			// XXX: load offline from sqlite			
			return;
		}

		RequestParams params = new RequestParams();
		client.getMyProfileInfo(params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, JSONObject jsonObject) {
				User u = User.fromJSON(jsonObject);
				populateUserInfo(u);
			}

        	@Override
			public void onFailure(Throwable e, String s) {
        		handleFailure(e, s);
        	}
		});
	}
	
	protected void populateUserInfo(User u) {
		getActionBar().setTitle("@" + u.getScreenName());
		tvAccountUserName.setText(u.getName());
		tvTagline.setText(u.getTagline());
		tvFollowers.setText(Integer.toString(u.getFollowersCount()) + " follwers");
		tvFollowing.setText(Integer.toString(u.getFollowingCount()) + " following");
		ImageLoader.getInstance().displayImage(u.getProfileImageUrl(), ivProfileImg);
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
			Toast.makeText(this, "Twitter is rate limiting this app,  please be patient", Toast.LENGTH_SHORT).show();
		}
	}
	

}
