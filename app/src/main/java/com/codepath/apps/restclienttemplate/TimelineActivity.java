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
import org.json.JSONObject;

public class TimelineActivity extends ActionBarActivity {

  private static final int COMPOSE_REQUEST = 42;
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
    client = TwitterApplication.getRestClient();

    ActionBar actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    populateTimeline();
  }

  private void populateTimeline() {
    client.getHomeTimeline(0, new JsonHttpResponseHandler() {
      @Override public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
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

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == COMPOSE_REQUEST && resultCode == RESULT_OK) {
      tweetsArrayAdapter.clear();
      populateTimeline();
    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      Intent intent = new Intent(this, ComposeActivity.class);
      startActivityForResult(intent, COMPOSE_REQUEST);
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
