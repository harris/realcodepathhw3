package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by harris on 2/21/15.
 */
public class User {
  private String name;
  private long uid;
  private String screenName;
  private String profileImageUrl;
  private int followersCount;
  private int friendsCount;
  private int tweetsCount;
  private String description;
  private String backgroundUrl;

  public String getName() {
    return name;
  }

  public long getUid() {
    return uid;
  }

  public String getScreenName() {
    return screenName;
  }

  public String getProfileImageUrl() {
    return profileImageUrl;
  }

  public int getFollowersCount() {
    return followersCount;
  }

  public int getFriendsCount() {
    return friendsCount;
  }

  public int getTweetsCount() {
    return tweetsCount;
  }

  public String getDescription() {
    return description;
  }

  public String getBackgroundUrl() {
    return backgroundUrl;
  }

  public static User fromJSON(JSONObject jsonObject) {
    User user = new User();
    try {
      user.name = jsonObject.getString("name");
      user.uid = jsonObject.getLong("id");
      user.screenName = jsonObject.getString("screen_name");
      user.profileImageUrl = jsonObject.getString("profile_image_url");
      user.followersCount = jsonObject.getInt("followers_count");
      user.friendsCount = jsonObject.getInt("friends_count");
      user.tweetsCount = jsonObject.getInt("statuses_count");
      user.description = jsonObject.getString("description");
      user.backgroundUrl = jsonObject.getString("profile_banner_url");

    } catch (JSONException e) {
      e.printStackTrace();
    }
    return user;
  }

}
