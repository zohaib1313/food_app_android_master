package com.bigbird.foodorderingapp.activities.kitchen_owner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bigbird.foodorderingapp.R;

public class AddNewDishDialog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Add new Dish");

        setContentView(R.layout.activity_add_new_dish_dialog);
    }
}