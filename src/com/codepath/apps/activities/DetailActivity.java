package com.codepath.apps.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.fragments.TweetsListFragment;
import com.codepath.apps.models.Tweet;
import com.codepath.apps.restclienttemplate.R;
import com.nostra13.universalimageloader.core.ImageLoader;

public class DetailActivity extends Activity {
	private ImageView dvivProfileImageUrl;
	private TextView dvtvUserName;
	private TextView dvtvScreenName;
	private TextView dvtvBody;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);

		dvivProfileImageUrl = (ImageView) findViewById(R.id.dvivProfileImage);
		dvtvUserName = (TextView) findViewById(R.id.dvtvUserName);
		dvtvScreenName = (TextView) findViewById(R.id.dvtvScreenName);
		dvtvBody = (TextView) findViewById(R.id.dvtvBody);
		
		Tweet selectedTweet = (Tweet) getIntent().getSerializableExtra(TweetsListFragment.TWEET);
		if (selectedTweet != null) {
			dvtvUserName.setText(selectedTweet.getUser().getName());
			dvtvScreenName.setText(selectedTweet.getUser().getScreenName());
			ImageLoader imageLoader = ImageLoader.getInstance();
			imageLoader.displayImage(selectedTweet.getUser().getProfileImageUrl(), dvivProfileImageUrl);
			Log.d("INFO", "body is " + selectedTweet.getBody());
			dvtvBody.setText(selectedTweet.getBody());
		}
		else {
			Log.d("ERROR", "did not find selected tweet");
		}
	}
}
