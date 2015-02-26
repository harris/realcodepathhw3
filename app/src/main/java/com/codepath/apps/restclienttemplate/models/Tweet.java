package com.codepath.apps.restclienttemplate.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by harris on 2/21/15.
 */
public class Tweet {
  private User user;
  private String body;
  private long uid;
  private String createdAt;

  public User getUser() {
    return user;
  }

  public String getBody() {
    return body;
  }

  public long getUid() {
    return uid;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public String getCreatedAtShort() {
    String relativeTimeSpanString =
        android.text.format.DateUtils.getRelativeTimeSpanString(getCreatedAtLong(),
            new Date().getTime(), android.text.format.DateUtils.MINUTE_IN_MILLIS).toString();

    String pattern = "(\\d+)\\s([a-z])(.*)";
    Pattern r = Pattern.compile(pattern);

    Matcher m = r.matcher(relativeTimeSpanString);
    if (m.find( )) {
      return m.group(1) + m.group(2);
    } else {
      return relativeTimeSpanString;
    }
  }

  public long getCreatedAtLong() {
    final String TWITTER="EEE MMM dd HH:mm:ss ZZZZZ yyyy";
    SimpleDateFormat sf = new SimpleDateFormat(TWITTER);
    sf.setLenient(true);
    try {
      return sf.parse(getCreatedAt()).getTime();
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return 1;
  }

  public static Tweet fromJSON(JSONObject jsonObject) {
    Tweet tweet = new Tweet();
    try {
      tweet.body = jsonObject.getString("text");
      tweet.uid = jsonObject.getLong("id");
      tweet.createdAt = jsonObject.getString("created_at");
      tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return tweet;
  }

  public static List<Tweet> fromJsonArray(JSONArray json) {
    List<Tweet> tweets = new ArrayList<>();
    for (int i = 0; i < json.length(); i++) {
      try {
        Tweet tweet = Tweet.fromJSON(json.getJSONObject(i));
        tweets.add(tweet);
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
    return tweets;
  }

}
