package com.bigbird.foodorderingapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.bigbird.foodorderingapp.MainActivity;
import com.bigbird.foodorderingapp.R;
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
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent;
              if(SessionManager.getInstance(SplashActivity.this).isLoggedIn()){
                  mainIntent = new Intent(SplashActivity.this, MainActivity.class);
              }else {
                  mainIntent = new Intent(SplashActivity.this, ChooseUserLoginTypeActivity.class);
              }
                startActivity(mainIntent);


                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}