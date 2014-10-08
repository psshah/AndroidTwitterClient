package com.codepath.apps.models;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Column.ForeignKeyAction;
import com.activeandroid.annotation.Table;

@Table(name = "Tweet")
public class Tweet extends Model implements Serializable {
    @Column(name = "body")
	private String body;
    @Column(name = "uid", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
	private long uid;
    @Column(name = "createdAt")
	private String createdAt;
    @Column(name = "mediaUrl")
    private String mediaUrl;
    @Column(name = "user", onUpdate = ForeignKeyAction.CASCADE, onDelete = ForeignKeyAction.CASCADE)
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
	public String getMediaUrl() {
		return mediaUrl;
	}
	public User getUser() {
		return user;
	}
	public static long getLowestUid() {
		return lowestUid;
	}

    // Make sure to have a default constructor for every ActiveAndroid model
	public Tweet(){
		user = new User();
	}

	public static Tweet fromJSON(JSONObject jsonObject) {
		Tweet tweet = new Tweet();
		try {
			tweet.body = jsonObject.getString("text");
			tweet.uid = jsonObject.getLong("id");
			tweet.createdAt = jsonObject.getString("created_at");
			JSONObject entities = jsonObject.getJSONObject("entities");
			if(entities.has("media")) {
				JSONArray mediaArr = entities.getJSONArray("media");
				if(mediaArr.length() > 0) {
					tweet.mediaUrl = mediaArr.getJSONObject(0).getString("media_url");
				}
			}
			tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		tweet.save();
		return tweet;
	}
	
	public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray) {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		for(int i = 0; i < jsonArray.length(); i++) {
			try {
				Tweet tweet = Tweet.fromJSON(jsonArray.getJSONObject(i));
				if(tweet != null) {
					tweets.add(tweet);
					//Log.d("debug", "adding tweet #" + tweet.uid);
					lowestUid = tweet.uid;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return tweets;
	}
	
}
