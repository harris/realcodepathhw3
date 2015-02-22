package com.codepath.apps.restclienttemplate;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.activeandroid.util.Log;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

public class ComposeActivity extends ActionBarActivity {

  private EditText composeTweet;
  private TwitterClient client;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_compose);
    ActionBar actionBar = getSupportActionBar();
    actionBar.setDisplayUseLogoEnabled(true);
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setLogo(R.drawable.ic_launcher);
    composeTweet = (EditText) findViewById(R.id.compose_tweet);
    client = TwitterApplication.getRestClient();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_compose, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_tweet) {
      Log.d("DEBUG", composeTweet.getText().toString());
      client.postTweet(composeTweet.getText().toString(),  new JsonHttpResponseHandler() {
        @Override public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
          Toast.makeText(ComposeActivity.this, "Success", Toast.LENGTH_SHORT).show();
          setResult(RESULT_OK);
          finish();
        }

        @Override public void onFailure(int statusCode, Header[] headers, Throwable throwable,
            JSONObject errorResponse) {
          Toast.makeText(ComposeActivity.this, errorResponse.toString(), Toast.LENGTH_SHORT).show();
        }
      });
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
