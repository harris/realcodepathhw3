package com.codepath.apps.restclienttemplate.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TweetsArrayAdapter;
import com.codepath.apps.restclienttemplate.fragments.HomeTimelineFragment;
import com.codepath.apps.restclienttemplate.fragments.MentionsTimelineFragment;
import com.codepath.apps.restclienttemplate.fragments.TweetListFragment;
import com.codepath.apps.restclienttemplate.models.Tweet;

public class TimelineActivity extends BaseActivity implements TweetsArrayAdapter.TweetClickedListener{
  private static final int COMPOSE_REQUEST = 42;
  private ViewPager viewPager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_timeline);

    viewPager = (ViewPager) findViewById(R.id.viewpager);
    viewPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager()));

    PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
    tabsStrip.setTextColor(Color.parseColor("#01AEEB"));
    tabsStrip.setViewPager(viewPager);
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
      TweetListFragment tweetListFragment = (TweetListFragment)getSupportFragmentManager().findFragmentByTag(
          "android:switcher:" + R.id.viewpager + ":" + viewPager.getCurrentItem());
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

  public void onProfileView(MenuItem item) {
    Intent intent = new Intent(this, ProfileActivity.class);
    startActivity(intent);
  }

  @Override public void tweetClicked(Tweet tweet) {
    Intent intent = new Intent(this, ProfileActivity.class);
    intent.putExtra("screen_name", tweet.getUser().getScreenName());
    startActivity(intent);
  }

  public class TweetsPagerAdapter extends FragmentPagerAdapter {
    private String tabTitles[] = {"Home", "Mentions"};

    public TweetsPagerAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override public Fragment getItem(int position) {
      if (position == 0) {
        return new HomeTimelineFragment();
      } else {
        return new MentionsTimelineFragment();
      }
    }

    @Override public CharSequence getPageTitle(int position) {
      return tabTitles[position];
    }

    @Override public int getCount() {
      return tabTitles.length;
    }
  }
}
