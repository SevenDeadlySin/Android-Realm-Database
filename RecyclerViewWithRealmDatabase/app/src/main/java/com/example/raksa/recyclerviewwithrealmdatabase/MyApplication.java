package com.example.raksa.recyclerviewwithrealmdatabase;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Raksa on 9/11/2017.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //Setting Up Realm
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().name("Book_Data").deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfiguration);

    }
}
