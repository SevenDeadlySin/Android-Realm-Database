package com.raksa.realmdemoapp;


import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		//setting up realm in Application

		Realm.init(this);
		RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
				.name("myFirstRealm.realm")
				.build();

		Realm.setDefaultConfiguration(realmConfiguration);

	}
}
