package com.codepath.apps.restclienttemplate.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.fragments.TweetListFragment;

/**
 * Created by harris on 2/26/15.
 */
public class BaseActivity extends ActionBarActivity implements TweetListFragment.ProgressBarListner{

  private ProgressBar progressBar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActionBar actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);

    progressBar = new ProgressBar(this);
    progressBar.setVisibility(View.GONE);
    progressBar.setIndeterminate(true);
    actionBar.setDisplayShowCustomEnabled(true);
    actionBar.setCustomView(progressBar);
    supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
  }

  @Override public void showProgressBar() {
    progressBar.setIndeterminate(true);
    progressBar.setVisibility(View.VISIBLE);
  }
  @Override public void hideProgressBar() {
    progressBar.setIndeterminate(false);
    progressBar.setVisibility(View.INVISIBLE);
  }

}
