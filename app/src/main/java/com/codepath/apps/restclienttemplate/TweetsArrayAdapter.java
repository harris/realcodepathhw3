package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.Date;
import java.util.List;

/**
 * Created by harris on 2/21/15.
 */
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {

  public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
    super(context, android.R.layout.simple_list_item_1, tweets);
  }

  private static class ViewHolder {
    ImageView ivProfileImage;
    TextView tvBody;
    TextView tvUserName;
    TextView realName;
    TextView createdAt;
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    Tweet tweet = getItem(position);
    ViewHolder viewHolder;

    if (convertView == null) {
      viewHolder = new ViewHolder();
      convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
      viewHolder.ivProfileImage = (ImageView) convertView.findViewById(R.id.imageView);
      viewHolder.tvBody = (TextView) convertView.findViewById(R.id.tvBody);
      viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
      viewHolder.realName = (TextView) convertView.findViewById(R.id.realName);
      viewHolder.createdAt = (TextView) convertView.findViewById(R.id.createdAt);

      convertView.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder) convertView.getTag();
    }

    viewHolder.createdAt.setText(tweet.getCreatedAtShort());
    viewHolder.tvUserName.setText("@" + tweet.getUser().getScreenName());
    viewHolder.realName.setText(tweet.getUser().getName());

    viewHolder.tvBody.setText(tweet.getBody());
    viewHolder.ivProfileImage.setImageResource(android.R.color.transparent);
    Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(viewHolder.ivProfileImage);
    return convertView;
  }
}
