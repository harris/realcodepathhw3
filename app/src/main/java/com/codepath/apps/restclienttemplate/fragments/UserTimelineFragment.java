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
public class UserTimelineFragment extends TweetListFragment {
  private TwitterClient client;

  public static UserTimelineFragment newInstance(String screenName) {
    UserTimelineFragment userTimelineFragment = new UserTimelineFragment();
    Bundle bundle = new Bundle();
    bundle.putString("screen_name", screenName);
    userTimelineFragment.setArguments(bundle);
    return userTimelineFragment;
  }
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
    String screenName = getArguments().getString("screen_name");
    if (!((TwitterApplication)getActivity().getApplication()).canSendCall(getActivity())) {
      return;
    }
    listener.showProgressBar();
    client.getUserTimeLine(screenName, page, new JsonHttpResponseHandler() {
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
