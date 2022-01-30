package com.bigbird.foodorderingapp.activities.kitchen_owner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bigbird.foodorderingapp.R;

public class SignUpKitchenOwnerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Kitchen Dashboard");

        setContentView(R.layout.activity_sign_up_kitchen);
    }
}