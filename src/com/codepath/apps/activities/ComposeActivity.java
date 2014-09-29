package com.codepath.apps.activities;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.simpletwitterclient.TwitterApplication;
import com.codepath.apps.simpletwitterclient.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ComposeActivity extends Activity {
	private TwitterClient client;
	private EditText etTweet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		client = TwitterApplication.getRestClient();
		etTweet = (EditText) findViewById(R.id.etTweet);
	}

	public void createTweet(View v) {
		Log.d("INFO", "creating tweet with body = " + etTweet.getText().toString());
		if(etTweet.getText().toString().isEmpty())
		{
			finish();
			return;
		}
		//postTweet	
		RequestParams params = new RequestParams();
		Log.d("debug", "new tweet=" + etTweet.getText().toString());
		params.put("status", etTweet.getText().toString());
		client.postTweet(params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, JSONObject arg1) {
				Log.d("debug", "succeeded in post");
		    	Intent data = new Intent();
		    	setResult(RESULT_OK, data);
		    	finish();
			}

			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("error", s.toString());
		    	finish();
			}
			
		});
	}
	
	public void onCancel(View v) {
		Log.d("INFO", "cancelling");
		finish();
	}
}
