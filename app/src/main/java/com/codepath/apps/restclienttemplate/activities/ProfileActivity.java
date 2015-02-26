package com.codepath.apps.restclienttemplate.activities;

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
import com.codepath.apps.restclienttemplate.TwitterApplication;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.codepath.apps.restclienttemplate.models.User;
import com.codepath.apps.restclienttemplate.fragments.UserTimelineFragment;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;
import org.apache.http.Header;
import org.json.JSONObject;

public class ProfileActivity extends ActionBarActivity {

  private TwitterClient client;
  private User user;
  private ImageView loggedInImageView;
  private TextView loggedInRealName;
  private TextView loggedInUsername;
  private ImageView proifleViewBackground;

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
    proifleViewBackground = (ImageView) findViewById(R.id.backgroundImage);

    client = TwitterApplication.getRestClient();
    client.getCredential(new JsonHttpResponseHandler() {

      @Override public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        user = User.fromJSON(response);
        getSupportActionBar().setTitle("@" + user.getScreenName());
        Picasso.with(ProfileActivity.this).load(user.getProfileImageUrl()).into(
            loggedInImageView);
        loggedInRealName.setText(user.getName());
        loggedInUsername.setText("@" + user.getScreenName());
        Picasso.with(ProfileActivity.this).load(user.getBackgroundUrl()).into(proifleViewBackground);
      }

      @Override public void onFailure(int statusCode, Header[] headers, Throwable throwable,
          JSONObject errorResponse) {
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
}
