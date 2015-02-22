package com.codepath.apps.restclienttemplate;

import java.util.ArrayList;
import java.util.List;
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
