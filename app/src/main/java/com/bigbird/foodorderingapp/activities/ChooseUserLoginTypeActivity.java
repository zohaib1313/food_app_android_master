package com.bigbird.foodorderingapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bigbird.foodorderingapp.MainActivity;
import com.bigbird.foodorderingapp.R;
import com.bigbird.foodorderingapp.utils.AppConstant;

public class ChooseUserLoginTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_user_login_type);
    }



    public void gotoTermsAndConditions(View view) {
        Intent  intent = new Intent(ChooseUserLoginTypeActivity.this, TermsAndConditionActivity.class);
        startActivity(intent);
    }

    public void orderFoodLogin(View view) {
        Intent  intent = new Intent(ChooseUserLoginTypeActivity.this, SignInActivity.class);
        intent.putExtra(AppConstant.UserType,AppConstant.UserTypeUser);
        startActivity(intent);

    }
    public void kitchenLogin(View view) {
        Intent  intent = new Intent(ChooseUserLoginTypeActivity.this, SignInActivity.class);
        intent.putExtra(AppConstant.UserType,AppConstant.UserTypeKitchen);
        startActivity(intent);
    }

    public void adminLogin(View view) {
        Intent  intent = new Intent(ChooseUserLoginTypeActivity.this, SignInActivity.class);
        intent.putExtra(AppConstant.UserType,AppConstant.UserTypeAdmin);
        startActivity(intent);
    }
}