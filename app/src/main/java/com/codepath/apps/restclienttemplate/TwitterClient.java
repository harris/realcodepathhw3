package com.codepath.apps.restclienttemplate;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.FlickrApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.scribe.builder.api.TwitterApi;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
  public static final String REST_URL = "https://api.twitter.com/1.1";
  public static final String REST_CONSUMER_KEY = "BWRpP4xdTPGLvIVpNGhAEMfyg";
  public static final String REST_CONSUMER_SECRET = "4RvIDB8wK2BYY9HXaD2feQNDQ2n0k9FIPBPma2jxSVBZMDb1LS";
  public static final String REST_CALLBACK_URL = "oauth://harriscodepath";

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

  public void getHomeTimeline(int page, AsyncHttpResponseHandler handler) {
    String apiUrl = getApiUrl("statuses/home_timeline.json");
    RequestParams params = new RequestParams();
    params.put("page", String.valueOf(page));
    getClient().get(apiUrl, params, handler);
  }
  // RestClient.java
  public void postTweet(String body, AsyncHttpResponseHandler handler) {
    String apiUrl = getApiUrl("statuses/update.json");
    RequestParams params = new RequestParams();
    params.put("status", body);
    getClient().post(apiUrl, params, handler);
  }
}