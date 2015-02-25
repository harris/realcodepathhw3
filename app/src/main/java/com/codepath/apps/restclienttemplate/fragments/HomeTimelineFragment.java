package com.codepath.apps.restclienttemplate.fragments;

import android.os.Bundle;
import android.util.Log;
import com.codepath.apps.restclienttemplate.Tweet;
import com.codepath.apps.restclienttemplate.TwitterApplication;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by harris on 2/25/15.
 */
public class HomeTimelineFragment extends TweetListFragment {
  private TwitterClient client;

  @Override public void repopulate() {
    populateTimeline(0);
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    client = TwitterApplication.getRestClient();
    populateTimeline(0);
  }

  @Override public void customLoadMoreDataFromApi(int page) {
    populateTimeline(page);
  }

  private void populateTimeline(int page) {
    client.getHomeTimeline(page, new JsonHttpResponseHandler() {
      @Override public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
        addAll(Tweet.fromJsonArray(json));
      }

      @Override public void onFailure(int statusCode, Header[] headers, Throwable throwable,
          JSONObject errorResponse) {
        if (errorResponse == null) {
          Log.d("DEBUG", "unknown errors");
        } else {
          Log.d("DEBUG", errorResponse.toString());
        }
      }
    });
  }

}
