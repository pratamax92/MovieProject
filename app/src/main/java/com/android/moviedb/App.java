package com.android.moviedb;

import android.app.Application;

import com.orm.SugarApp;

import timber.log.Timber;

public class App extends SugarApp {


    @Override
    public void onCreate() {
        super.onCreate();

        Injector.INSTANCE.initializeApplication(this);

        // Initalize Timber Log
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

    }
}
