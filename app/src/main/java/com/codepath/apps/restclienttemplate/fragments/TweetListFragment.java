package com.codepath.apps.restclienttemplate.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.codepath.apps.restclienttemplate.EndlessScrollListener;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.Tweet;
import com.codepath.apps.restclienttemplate.TweetsArrayAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by harris on 2/25/15.
 */

public abstract class TweetListFragment extends Fragment {
  private List<Tweet> tweets;
  private TweetsArrayAdapter tweetsArrayAdapter;
  private ListView lvTweets;

  @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
    lvTweets = (ListView) v.findViewById(R.id.lv_tweets);
    lvTweets.setAdapter(tweetsArrayAdapter);
    lvTweets.setOnScrollListener(new EndlessScrollListener() {
      @Override public void onLoadMore(int page, int totalItemsCount) {
        customLoadMoreDataFromApi(page);
      }
    });
    return v;
  }

  public void clearAndRepopulate() {
    tweetsArrayAdapter.clear();
    repopulate();
  }

  public abstract void repopulate();

  public abstract void customLoadMoreDataFromApi(int page);

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    tweets = new ArrayList<Tweet>();
    tweetsArrayAdapter = new TweetsArrayAdapter(getActivity(), tweets);
  }

  public void addAll(List<Tweet> tweets) {
    tweetsArrayAdapter.addAll(tweets);
  }

  public void clear() {
    tweetsArrayAdapter.clear();
  }
}
