package com.bigbird.foodorderingapp;

import android.app.Application;

import com.bigbird.foodorderingapp.utils.helpers;
import com.google.firebase.FirebaseApp;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }
}
