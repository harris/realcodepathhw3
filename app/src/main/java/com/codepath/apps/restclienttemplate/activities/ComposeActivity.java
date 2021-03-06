package com.codepath.apps.restclienttemplate.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.activeandroid.util.Log;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TwitterApplication;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.codepath.apps.restclienttemplate.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;
import org.apache.http.Header;
import org.json.JSONObject;

public class ComposeActivity extends BaseActivity {

  private EditText composeTweet;
  private TwitterClient client;
  private MenuItem charLimit;
  private ImageView loggedInImageView;
  private TextView loggedInRealName;
  private TextView loggedInUsername;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_compose);
    ActionBar actionBar = getSupportActionBar();
    actionBar.setDisplayShowCustomEnabled(true);
    actionBar.setDisplayShowTitleEnabled(false);
    composeTweet = (EditText) findViewById(R.id.compose_tweet);
    client = TwitterApplication.getRestClient();

    loggedInImageView = (ImageView) findViewById(R.id.loggedInImageView);
    loggedInRealName = (TextView) findViewById(R.id.loggedInRealName);
    loggedInUsername = (TextView) findViewById(R.id.loggedInUserName);
    client.getCredential(new JsonHttpResponseHandler() {

      @Override public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        User loggedInUser = User.fromJSON(response);
        Picasso.with(ComposeActivity.this).load(loggedInUser.getProfileImageUrl()).into(loggedInImageView);
        loggedInRealName.setText(loggedInUser.getName());
        loggedInUsername.setText("@" + loggedInUser.getScreenName());
      }

      @Override public void onFailure(int statusCode, Header[] headers, Throwable throwable,
          JSONObject errorResponse) {
        Log.d(errorResponse.toString());
      }
    });


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
      if (!((TwitterApplication)getApplication()).canSendCall(this)) {
        return true;
      }
      showProgressBar();
      client.postTweet(composeTweet.getText().toString(),  new JsonHttpResponseHandler() {
        @Override public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
          hideProgressBar();
          Toast.makeText(ComposeActivity.this, "Success", Toast.LENGTH_SHORT).show();
          setResult(RESULT_OK);
          finish();
        }

        @Override public void onFailure(int statusCode, Header[] headers, Throwable throwable,
            JSONObject errorResponse) {
          hideProgressBar();
          Toast.makeText(ComposeActivity.this, errorResponse.toString(), Toast.LENGTH_SHORT).show();
        }
      });
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
