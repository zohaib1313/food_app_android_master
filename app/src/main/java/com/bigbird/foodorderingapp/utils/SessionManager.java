package com.bigbird.foodorderingapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.bigbird.foodorderingapp.models.ModelAdminUser;
import com.bigbird.foodorderingapp.models.ModelKitchenUser;
import com.bigbird.foodorderingapp.models.ModelUserTypeUser;
import com.google.gson.Gson;


public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;
    SPManager mSPManager;
    ModelAdminUser adminUser = null;
    ModelUserTypeUser modelUserTypeUser=null;
    ModelKitchenUser modelKitchenUser=null;
    String  type=null;
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

    public String getType() {
        return pref.getString(AppConstant.UserType, null);
    }
    public void setType(String type){
        editor.putString(AppConstant.UserType, type);
        editor.commit();
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
    public void clearSession() {
        editor.clear();
        editor.putBoolean(AppConstant.KEY_IS_LOGGED_IN, false);
        editor.commit();

    }

        ////////////////////////////admin////////////////////
    public void createAdminUserLoginSession(ModelAdminUser user) {
        clearSession();
        // Storing login value as TRUE
        editor.putBoolean(AppConstant.KEY_IS_LOGGED_IN, true);
        setType(AppConstant.UserTypeAdmin);

        // commit changes
        editor.commit();

        updateAdminUserSession(user);
    }
    public void updateAdminUserSession(ModelAdminUser user) {
        this.adminUser = user;
        // Storing login value as TRUE
        Gson gson = new Gson();
        String json = gson.toJson(user); // myObject - instance of MyObject
        // Storing in pref
        editor.putString(AppConstant.USER_INFO, json);
        // commit changes
        editor.commit();
    }
    public ModelAdminUser getAdminUser() {
        if (adminUser == null) {
            String str = pref.getString(AppConstant.USER_INFO, null);
            if (str != null) {
                adminUser = new Gson().fromJson(str, ModelAdminUser.class);
            }
        }
        return adminUser;
    }

            ////////////////////kitchen//////////////
    public void createKitchenUserLoginSession(ModelKitchenUser user) {
    clearSession();
    // Storing login value as TRUE
    editor.putBoolean(AppConstant.KEY_IS_LOGGED_IN, true);
setType(AppConstant.UserTypeKitchen);
    // commit changes
    editor.commit();

    updateKitchenUserSession(user);
}
    public void updateKitchenUserSession(ModelKitchenUser user) {
        this.modelKitchenUser = user;
        // Storing login value as TRUE
        Gson gson = new Gson();
        String json = gson.toJson(user); // myObject - instance of MyObject
        // Storing in pref
        editor.putString(AppConstant.USER_INFO, json);
        // commit changes
        editor.commit();
    }
    public ModelKitchenUser getKitchenUser() {
        if (modelKitchenUser == null) {
            String str = pref.getString(AppConstant.USER_INFO, null);
            if (str != null) {
                modelKitchenUser = new Gson().fromJson(str, ModelKitchenUser.class);
            }
        }
        return modelKitchenUser;
    }

    ////////////////////////////UserTypeUser////////////////////
    public void createUserTypeUserLoginSession(ModelUserTypeUser user) {
        clearSession();
        // Storing login value as TRUE
        editor.putBoolean(AppConstant.KEY_IS_LOGGED_IN, true);
        setType(AppConstant.UserTypeUser);

        // commit changes
        editor.commit();

        updateUserTypeUserSession(user);
    }
    public void updateUserTypeUserSession(ModelUserTypeUser user) {
        this.modelUserTypeUser = user;
        // Storing login value as TRUE
        Gson gson = new Gson();
        String json = gson.toJson(user); // myObject - instance of MyObject
        // Storing in pref
        editor.putString(AppConstant.USER_INFO, json);
        // commit changes
        editor.commit();
    }
    public ModelUserTypeUser getUserTypeUser() {
        if (modelUserTypeUser == null) {
            String str = pref.getString(AppConstant.USER_INFO, null);
            if (str != null) {
                modelUserTypeUser = new Gson().fromJson(str, ModelUserTypeUser.class);
            }
        }
        return modelUserTypeUser;
    }


}
