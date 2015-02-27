package com.codepath.apps.restclienttemplate.fragments;

import android.os.Bundle;
import android.util.Log;
import com.codepath.apps.restclienttemplate.activities.BaseActivity;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.TwitterApplication;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by harris on 2/25/15.
 */
public class MentionsTimelineFragment extends TweetListFragment{
  private TwitterClient client;

  @Override public void repopulate() {
    populateMentionTimeline(0);
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    client = TwitterApplication.getRestClient();
    populateMentionTimeline(0);
  }

  @Override public void customLoadMoreDataFromApi(int page) {
    populateMentionTimeline(page);
  }

  private void populateMentionTimeline(int page) {
    if (!((TwitterApplication)getActivity().getApplication()).canSendCall(getActivity())) {
      return;
    }
    listener.showProgressBar();
    client.getMentions(page, new JsonHttpResponseHandler() {
      @Override public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
        listener.hideProgressBar();
        addAll(Tweet.fromJsonArray(json));
      }

      @Override public void onFailure(int statusCode, Header[] headers, Throwable throwable,
          JSONObject errorResponse) {
        listener.hideProgressBar();
        if (errorResponse == null) {
          Log.d("DEBUG", "unknown errors");
        } else {
          Log.d("DEBUG", errorResponse.toString());
        }
      }
    });
  }

}
