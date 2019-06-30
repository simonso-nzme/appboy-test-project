package com.appboyproject;

import com.appboy.Constants;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.react.ReactActivity;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import com.appboy.enums.Channel;
import com.appboy.IAppboyNavigator;
import com.appboy.ui.AppboyNavigator;
import com.appboy.ui.actions.NewsfeedAction;
import com.appboy.ui.actions.UriAction;

public class MainActivity extends ReactActivity {
  private static final String TAG = String.format("%s.%s", Constants.APPBOY_LOG_TAG_PREFIX, MainActivity.class.getName());

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Intent intent = getIntent();
    Uri data = intent.getData();
    if (data != null) {
      Toast.makeText(this, "Activity opened by deep link: " + data.toString(), Toast.LENGTH_LONG);
      Log.i(TAG, "Deep link is " + data.toString());
    }

    AppboyNavigator.setAppboyNavigator(new IAppboyNavigator() {
      @Override
      public void gotoNewsFeed(Context context, NewsfeedAction newsfeedAction) {
          new AppboyNavigator().gotoNewsFeed(context, newsfeedAction);
      }

      @Override
      public void gotoUri(Context context, UriAction uriAction) {
          if (uriAction.getChannel().equals(Channel.PUSH)) {
              Intent intent = new Intent(Intent.ACTION_VIEW);
              intent.setData(uriAction.getUri());

              if (uriAction.getExtras() != null) {
                  intent.putExtras(uriAction.getExtras());
              }
              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
              if (intent.resolveActivity(context.getPackageManager()) != null) {
                  context.startActivity(intent);
              }
          } else {
              new AppboyNavigator().gotoUri(context, uriAction);
          }
      }
    });    

  }

  /**
   * Returns the name of the main component registered from JavaScript.
   * This is used to schedule rendering of the component.
   */
  @Override
  protected String getMainComponentName() {
      return "AppboyProject";
  }
}
