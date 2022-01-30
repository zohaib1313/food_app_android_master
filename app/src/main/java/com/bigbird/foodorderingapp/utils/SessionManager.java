package com.bigbird.foodorderingapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.bigbird.foodorderingapp.models.ModelUser;
import com.google.gson.Gson;


public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;
    SPManager mSPManager;
    ModelUser user = null;
    // Context
    Context mContext;
    // Shared pref mode
    int PRIVATE_MODE = 0;

    private static SessionManager instance;
    // User user = null;


    public static SessionManager getInstance(Context context) {
        if (instance == null) {
            instance = new SessionManager(context);
        }
        return instance;
    }


    private SessionManager(Context context) {
        this.mContext = context;
        pref = mContext.getSharedPreferences(AppConstant.MyPref, PRIVATE_MODE);
        mSPManager = SPManager.getInstance(context);
        editor = pref.edit();
        // getUser();
    }

    public SPManager getSPManager() {
        return mSPManager;
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(AppConstant.KEY_IS_LOGGED_IN, false);
    }

    public void setLoggedIn(boolean isLoggedIn) {
        editor.putBoolean(AppConstant.KEY_IS_LOGGED_IN, isLoggedIn);
        // commit changes
        editor.commit();

    }

    //
    public void clearSession() {
        editor.clear();
        editor.commit();

    }


    public void createUserLoginSession(ModelUser user) {
        clearSession();
        // Storing login value as TRUE
        editor.putBoolean(AppConstant.KEY_IS_LOGGED_IN, true);

        // commit changes
        editor.commit();

        updateUserSession(user);
    }

    //
//
//
//
//
    public void updateUserSession(ModelUser user) {
        this.user = user;
        // Storing login value as TRUE
        Gson gson = new Gson();
        String json = gson.toJson(user); // myObject - instance of MyObject
        // Storing in pref
        editor.putString(AppConstant.USER_INFO, json);

        // commit changes
        editor.commit();

    }

    //
//
    public ModelUser getUser() {
        if (user == null) {
            String str = pref.getString(AppConstant.USER_INFO, null);
            if (str != null) {
                user = new Gson().fromJson(str, ModelUser.class);
            }
        }
        return user;
    }



}
