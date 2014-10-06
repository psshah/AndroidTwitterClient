package com.codepath.apps.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class UserTimelineFragment extends TweetsListFragment {
	private long userId;
	
    public static UserTimelineFragment newInstance(long userid) {
    	UserTimelineFragment fragmentUserTl = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putLong("userid", userid);
        fragmentUserTl.setArguments(args);
        return fragmentUserTl;
    }

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		userId = getArguments().getLong("userid");	
		setRequestType(RequestType.USER_TIMELINE);
		setUserId(userId);
    	Toast.makeText(getActivity(), "on create, set user id=" +userId, Toast.LENGTH_SHORT).show();
		populateTimeline(0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}
}
