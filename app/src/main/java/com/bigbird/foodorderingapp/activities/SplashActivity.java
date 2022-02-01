package com.bigbird.foodorderingapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.bigbird.foodorderingapp.R;
import com.bigbird.foodorderingapp.activities.admin.AdminDashboardActivity;
import com.bigbird.foodorderingapp.activities.kitchen_owner.KitchenOwnerDashboardActivity;
import com.bigbird.foodorderingapp.activities.user.UserDashboardActivity;
import com.bigbird.foodorderingapp.models.ModelAdminUser;
import com.bigbird.foodorderingapp.utils.SessionManager;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent;
                if (SessionManager.getInstance(SplashActivity.this).isLoggedIn()) {

                    ModelAdminUser user = SessionManager.getInstance(SplashActivity.this).getUser();
                    switch (user.getType()) {

                        case "UserTypeUser":
                            mainIntent = new Intent(SplashActivity.this, UserDashboardActivity.class);

                            break;
                        case "UserTypeKitchen":
                            mainIntent = new Intent(SplashActivity.this, KitchenOwnerDashboardActivity.class);

                            break;
                        case "UserTypeAdmin":
                            mainIntent = new Intent(SplashActivity.this, AdminDashboardActivity.class);

                            break;
                        default:
                            mainIntent = new Intent(SplashActivity.this, UserDashboardActivity.class);

                    }


                } else {
                    mainIntent = new Intent(SplashActivity.this, ChooseUserLoginTypeActivity.class);
                }
                startActivity(mainIntent);


                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}