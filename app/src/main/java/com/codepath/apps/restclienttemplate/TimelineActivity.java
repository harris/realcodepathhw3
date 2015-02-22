package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import com.loopj.android.http.JsonHttpResponseHandler;
import java.util.ArrayList;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TimelineActivity extends ActionBarActivity {

  private ListView lvTweets;
  private TwitterClient client;
  private ArrayList<Tweet> tweets;
  private TweetsArrayAdapter tweetsArrayAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_timeline);
    lvTweets = (ListView) findViewById(R.id.lv_tweets);
    tweets = new ArrayList<Tweet>();
    tweetsArrayAdapter = new TweetsArrayAdapter(this, tweets);
    lvTweets.setAdapter(tweetsArrayAdapter);
    client = RestApplication.getRestClient();

    //ActionBar actionBar = getSupportActionBar();
    //actionBar.setDisplayUseLogoEnabled(true);
    //actionBar.setLogo(R.drawable.ic_launcher);
    //actionBar.setDispl
    populateTimeline();
  }

  private void populateTimeline() {
    client.getHomeTimeline(0, new JsonHttpResponseHandler() {
      @Override public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
        Log.d("Debug", json.toString());
        tweetsArrayAdapter.addAll(Tweet.fromJsonArray(json));
        tweetsArrayAdapter.notifyDataSetChanged();
      }

      @Override public void onFailure(int statusCode, Header[] headers, Throwable throwable,
          JSONObject errorResponse) {
        Log.d("Debug", errorResponse.toString());
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_timeline, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}