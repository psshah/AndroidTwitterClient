package com.codepath.apps.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;


public class Tweet {
	private String body;
	private long uid;
	private String createdAt;
	private User user;
	private static long lowestUid;
	
	public String getBody() {
		return body;
	}
	public long getUid() {
		return uid;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public User getUser() {
		return user;
	}
	public static long getLowestUid() {
		return lowestUid;
	}
	
	public static Tweet fromJSON(JSONObject jsonObject) {
		Tweet tweet = new Tweet();
		try {
			tweet.body = jsonObject.getString("text");
			tweet.uid = jsonObject.getLong("id");
			tweet.createdAt = jsonObject.getString("created_at");
			tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return tweet;
	}
	
	public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray) {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		for(int i = 0; i < jsonArray.length(); i++) {
			try {
				Tweet tweet = Tweet.fromJSON(jsonArray.getJSONObject(i));
				if(tweet != null) {
					tweets.add(tweet);
					Log.d("debug", "adding tweet #" + tweet.uid);
					lowestUid = tweet.uid;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return tweets;
	}
}
