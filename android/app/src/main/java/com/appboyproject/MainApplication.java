package com.appboyproject;

import android.app.Application;
import android.util.Log;

import com.appboy.AppboyLifecycleCallbackListener;
import com.appboy.reactbridge.AppboyReactPackage;
import com.appboy.support.AppboyLogger;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;

import java.util.Arrays;
import java.util.List;

public class MainApplication extends Application implements ReactApplication {

  private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
    @Override
    public boolean getUseDeveloperSupport() {
      return BuildConfig.DEBUG;
    }

    @Override
    protected List<ReactPackage> getPackages() {
      return Arrays.<ReactPackage>asList(
          new MainReactPackage(),
          new AppboyReactPackage()
      );
    }
  };

  @Override
  public ReactNativeHost getReactNativeHost() {
      return mReactNativeHost;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    registerActivityLifecycleCallbacks(new AppboyLifecycleCallbackListener());
    AppboyLogger.setLogLevel(Log.VERBOSE);
  }
}
