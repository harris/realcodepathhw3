package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import com.codepath.apps.restclienttemplate.fragments.TweetListFragment;
import com.loopj.android.http.JsonHttpResponseHandler;
import java.util.ArrayList;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

public class TimelineActivity extends ActionBarActivity{
  private static final int COMPOSE_REQUEST = 42;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_timeline);

    ActionBar actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
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
      TweetListFragment tweetListFragment =
          (TweetListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_timeline);
      tweetListFragment.clearAndRepopulate();
    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.action_compose) {
      Intent intent = new Intent(this, ComposeActivity.class);
      startActivityForResult(intent, COMPOSE_REQUEST);
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
