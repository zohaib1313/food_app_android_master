package com.bigbird.foodorderingapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bigbird.foodorderingapp.R;
import com.bigbird.foodorderingapp.activities.admin.AdminDashboardActivity;
import com.bigbird.foodorderingapp.activities.admin.SignUpAdminActivity;
import com.bigbird.foodorderingapp.activities.kitchen_owner.KitchenOwnerDashboardActivity;
import com.bigbird.foodorderingapp.activities.kitchen_owner.SignUpKitchenOwnerActivity;
import com.bigbird.foodorderingapp.activities.user.SignUpUserActivity;
import com.bigbird.foodorderingapp.activities.user.UserDashboardActivity;
import com.bigbird.foodorderingapp.utils.AppConstant;

public class SignInActivity extends AppCompatActivity {
    String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        userType = getIntent().getStringExtra(AppConstant.UserType);


    }


    public void gotoSignUpScreen(View view) {
        Intent intent;
        switch (userType) {
            case "UserTypeUser":
                intent = new Intent(SignInActivity.this, SignUpUserActivity.class);
                break;
            case "UserTypeKitchen":
                intent = new Intent(SignInActivity.this, SignUpKitchenOwnerActivity.class);
                break;
            case "UserTypeAdmin":
                intent = new Intent(SignInActivity.this, SignUpAdminActivity.class);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + userType);
        }
        startActivity(intent);
    }

    public void gotoDashBoard(View view) {
        Intent intent;
        switch (userType) {
            case "UserTypeUser":
                intent = new Intent(SignInActivity.this, UserDashboardActivity.class);
                break;
            case "UserTypeKitchen":
                intent = new Intent(SignInActivity.this, KitchenOwnerDashboardActivity.class);
                break;
            case "UserTypeAdmin":
                intent = new Intent(SignInActivity.this, AdminDashboardActivity.class);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + userType);
        }
        startActivity(intent);

    }
}