package com.example.myapplication.application;

import android.app.Application;

import timber.log.Timber;

public class Vezbe3Application extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}
