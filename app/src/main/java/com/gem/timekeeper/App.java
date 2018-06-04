package com.gem.timekeeper;

import android.support.multidex.MultiDexApplication;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.gem.timekeeper.realm.AppModul;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Application
 * Created by NEO on 11/7/2016.
 */

public class App extends MultiDexApplication {

  private static App sInstance;

  @Override
  public void onCreate() {
    super.onCreate();
    Fresco.initialize(this);
    sInstance = this;
    Realm.init(this);
    RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
        .name(Realm.DEFAULT_REALM_NAME)
        .schemaVersion(0)
        .modules(new AppModul())
        .deleteRealmIfMigrationNeeded()
        .build();
    Realm.setDefaultConfiguration(realmConfiguration);

  }

  public static App getInstance() {
    return sInstance;
  }
}
