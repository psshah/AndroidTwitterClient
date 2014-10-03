package com.codepath.apps.adapters;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.net.ParseException;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.models.Tweet;
import com.codepath.apps.restclienttemplate.R;
import com.nostra13.universalimageloader.core.ImageLoader;


public class TweetArrayAdapter extends ArrayAdapter<Tweet> {
	private static class ViewHolder {
		TextView tvUserName;
		TextView tvBody;
		TextView tvCreationTime;
		ImageView ivProfileImage;
	}
	
	public TweetArrayAdapter(Context context, List<Tweet> tweets) {
		super(context, 0, tweets);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Get item at position
		Tweet tweet = getItem(position); 
		ViewHolder viewHolder;

		// Check if this is recycled view, if not, create / inflate it.
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.tweet_item, parent, false);
			// Get resources from view to populate
			viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
			viewHolder.tvBody = (TextView) convertView.findViewById(R.id.tvBody);
			viewHolder.tvCreationTime = (TextView) convertView.findViewById(R.id.tvCreationTime);
			viewHolder.ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
			convertView.setTag(viewHolder);
		} 
		else {
			viewHolder = (ViewHolder) convertView.getTag();
		}


		// Clear recycled view
		viewHolder.ivProfileImage.setImageResource(0);
		
		// Populate resources
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(tweet.getUser().getProfileImageUrl(), viewHolder.ivProfileImage);
		viewHolder.tvUserName.setText(tweet.getUser().getScreenName());
		viewHolder.tvBody.setText(tweet.getBody());
		viewHolder.tvCreationTime.setText(getRelativeTimeAgo(tweet.getCreatedAt()));
		
		return convertView;
	}

	// getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
	public String getRelativeTimeAgo(String rawJsonDate) {
		String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
		SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
		sf.setLenient(true);
	 
		String relativeDate = "";
		long dateMillis;
		try {
			dateMillis = sf.parse(rawJsonDate).getTime();
			relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
					System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
	 
		return relativeDate;
	}
	
}
