package com.codepath.apps.restclienttemplate;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
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
  private MenuItem charLimit;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_compose);
    ActionBar actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setDisplayShowCustomEnabled(true);
    actionBar.setDisplayShowTitleEnabled(false);
    composeTweet = (EditText) findViewById(R.id.compose_tweet);
    client = TwitterApplication.getRestClient();

    composeTweet.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      }

      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
      }

      @Override public void afterTextChanged(Editable s) {
        int charLeft = 140 - composeTweet.getText().toString().length();
        charLimit.setTitle("" + charLeft);
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_compose, menu);
    charLimit = menu.getItem(0);
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
      if (Integer.valueOf(charLimit.getTitle().toString()) < 0) {
        Toast.makeText(ComposeActivity.this, "Please keep tweet within 140 characters", Toast.LENGTH_SHORT).show();
        return true;
      }
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
