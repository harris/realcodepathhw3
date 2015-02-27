package com.codepath.apps.restclienttemplate.activities;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.activeandroid.util.Log;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TweetsArrayAdapter;
import com.codepath.apps.restclienttemplate.TwitterApplication;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;
import com.codepath.apps.restclienttemplate.fragments.UserTimelineFragment;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;
import org.apache.http.Header;
import org.json.JSONObject;

public class ProfileActivity extends BaseActivity implements TweetsArrayAdapter.TweetClickedListener{

  private TwitterClient client;
  private User user;
  private ImageView loggedInImageView;
  private TextView loggedInRealName;
  private TextView loggedInUsername;
  private ImageView proifleViewBackground;
  private TextView numFollowers;
  private TextView numFollowings;
  private TextView numTweets;
  private TextView userDescription;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile);
    String screenName = getIntent().getStringExtra("screen_name");
    if (savedInstanceState == null) {
      FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
      ft.replace(R.id.ftContainer, UserTimelineFragment.newInstance(screenName));
      ft.commit();
    }

    loggedInImageView = (ImageView) findViewById(R.id.loggedInImageView);
    loggedInRealName = (TextView) findViewById(R.id.loggedInRealName);
    loggedInUsername = (TextView) findViewById(R.id.loggedInUserName);
    userDescription = (TextView) findViewById(R.id.userDescription);

    proifleViewBackground = (ImageView) findViewById(R.id.backgroundImage);

    numTweets = (TextView) findViewById(R.id.numTweets);
    numFollowings = (TextView) findViewById(R.id.numFollowings);
    numFollowers = (TextView) findViewById(R.id.numFollowers);

    client = TwitterApplication.getRestClient();
    if (screenName == null) {
      populateSelfProfile();
    } else {
      populateUserInfo(screenName);
    }
  }

  private void populateUserInfo(String screenName) {
    if (!((TwitterApplication)getApplication()).canSendCall(this)) {
      return;
    }
    showProgressBar();
    client.getUserInfo(screenName, new JsonHttpResponseHandler() {
      @Override public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        hideProgressBar();
        populateProfileInfo(response);
      }

      @Override public void onFailure(int statusCode, Header[] headers, Throwable throwable,
          JSONObject errorResponse) {
        hideProgressBar();
        Log.d(errorResponse.toString());
      }
    });
  }

  private void populateProfileInfo(JSONObject response) {
    user = User.fromJSON(response);
    getSupportActionBar().setTitle("@" + user.getScreenName());
    Picasso.with(this).load(user.getProfileImageUrl()).into(loggedInImageView);
    loggedInRealName.setText(user.getName());
    loggedInUsername.setText("@" + user.getScreenName());
    Picasso.with(this)
        .load(user.getBackgroundUrl())
        .into(proifleViewBackground);
    numTweets.setText(user.getTweetsCount() + "\nTWEETS");
    numFollowings.setText(user.getFriendsCount() + "\nFOLLOWING");
    numFollowers.setText(user.getFollowersCount() + "\nFOLLOWERS");
    userDescription.setText(user.getDescription());
  }

  private void populateSelfProfile() {
    if (!((TwitterApplication)getApplication()).canSendCall(this)) {
      return;
    }

    showProgressBar();
    client.getCredential(new JsonHttpResponseHandler() {
      @Override public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        hideProgressBar();
        populateProfileInfo(response);
      }

      @Override public void onFailure(int statusCode, Header[] headers, Throwable throwable,
          JSONObject errorResponse) {
        hideProgressBar();
        Log.d(errorResponse.toString());
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_profile, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override public void tweetClicked(Tweet tweet) {
    Intent intent = new Intent(this, ProfileActivity.class);
    intent.putExtra("screen_name", tweet.getUser().getScreenName());
    startActivity(intent);
  }
}
