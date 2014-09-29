package com.codepath.apps.adapters;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

public abstract class EndlessScrollListener implements OnScrollListener {
	// The minimum amount of items to have below your current scroll position
	// before loading more.
	private int visibleThreshold = 5;
	// True if we are still waiting for the last set of data to load.
	private boolean loading = true;
	// The total number of items in the dataset after the last load
	private int previousTotalItemCount = 0;

	// Sets the starting page index
	private int startingPageIndex = 0;
	// The current offset index of data you have loaded
	private int currentPage = 0;

	public EndlessScrollListener() {
	}

	public EndlessScrollListener(int visibleThreshold) {
		this.visibleThreshold = visibleThreshold;
	}

	public EndlessScrollListener(int visibleThreshold, int startPage) {
		this.visibleThreshold = visibleThreshold;
		this.startingPageIndex = startPage;
		this.currentPage = startPage;
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// If the total item count is zero and the previous isn't, assume the
		// list is invalidated and should be reset back to initial state
		if (totalItemCount < previousTotalItemCount) {
			this.currentPage = this.startingPageIndex;
			this.previousTotalItemCount = totalItemCount;
			if (totalItemCount == 0) {
				this.loading = true;
			}
		}

		// If still loading, check if dataset count has changed. 
		// If yes, conclude it finished loading and update totalItemCount and current page number
		if (loading && (totalItemCount > previousTotalItemCount)) {
			loading = false;
			previousTotalItemCount = totalItemCount;
			currentPage++;
		}
		
		// If not loading, check if we've reached visible threshold.
		// If yes, reload more data. Execute onLoadMore() 
		if (!loading && (totalItemCount - visibleItemCount)<=(firstVisibleItem + visibleThreshold)) {
		    onLoadMore(currentPage + 1, totalItemCount);
		    loading = true;
		}

	}

	public abstract void onLoadMore(int i, int totalItemCount);
	

}