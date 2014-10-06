package com.codepath.apps.simpletwitterclient;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;
import android.util.Log;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "CpU7WSYt66TCtTqwoGiB3tLLq";       // Change this
	public static final String REST_CONSUMER_SECRET = "AIQPvL9XWpLgKKcH31UcM0MpNYv1jD95qfF1TgLJqVuGVESq1K"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://psshahTweets"; // Change this (here and in manifest)

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	public void getHomeTimeline(RequestParams params, AsyncHttpResponseHandler handler) {
		Log.d("INFO", "uri = " + REST_URL + "statuses/home_timeline.json");
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		//RequestParams params = new RequestParams();
		//params.put("since_id", "1");
		if(params == null) {
			client.get(apiUrl, null, handler);
		}
		else {
			client.get(apiUrl, params, handler);
		}
	}

	public void postTweet(RequestParams params, AsyncHttpResponseHandler handler) {
		Log.d("INFO", "uri = " + REST_URL + "/statuses/update.json");
		String apiUrl = getApiUrl("statuses/update.json");
		if(params == null) {
			client.post(apiUrl, null, handler);
		}
		else {
			client.post(apiUrl, params, handler);
		}		
	}

	public void getMentionsTimeline(RequestParams params,
			AsyncHttpResponseHandler handler) {
		Log.d("INFO", "uri = " + REST_URL + "statuses/mentions_timeline.json");
		String apiUrl = getApiUrl("statuses/mentions_timeline.json");
		if(params == null) {
			client.get(apiUrl, null, handler);
		}
		else {
			client.get(apiUrl, params, handler);
		}
	}

	public void getMyProfileInfo(RequestParams params,
			AsyncHttpResponseHandler handler) {
		Log.d("INFO", "uri = " + REST_URL + "account/verify_credentials.json");
		String apiUrl = getApiUrl("account/verify_credentials.json");
		client.get(apiUrl, null, handler);
	}

	public void getUserTimeline(RequestParams params,
			AsyncHttpResponseHandler handler) {
		Log.d("INFO", "uri = " + REST_URL + "/statuses/user_timeline.json");
		String apiUrl = getApiUrl("statuses/user_timeline.json");
		if(params == null) {
			client.get(apiUrl, null, handler);
		}
		else {
			client.get(apiUrl, params, handler);
		}		
	}


	/*
	// CHANGE THIS
	// DEFINE METHODS for different API endpoints here
	public void getInterestingnessList(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("?nojsoncallback=1&method=flickr.interestingness.getList");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("format", "json");
		client.get(apiUrl, params, handler);
	}
	*/

	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
}