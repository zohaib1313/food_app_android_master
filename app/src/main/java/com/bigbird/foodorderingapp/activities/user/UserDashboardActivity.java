package com.bigbird.foodorderingapp.activities.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bigbird.foodorderingapp.R;
import com.bigbird.foodorderingapp.activities.ChooseUserLoginTypeActivity;
import com.bigbird.foodorderingapp.activities.SignInActivity;
import com.bigbird.foodorderingapp.activities.kitchen_owner.KitchenOwnerDashboardActivity;
import com.bigbird.foodorderingapp.models.ProductItemModel;
import com.bigbird.foodorderingapp.utils.SessionManager;

import java.util.ArrayList;

public class UserDashboardActivity extends AppCompatActivity {

    private com.factor.bouncy.BouncyRecyclerView rvCartItem;
    private UserCartAdapter userCartAdapter;
    private ArrayList<ProductItemModel> modelArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("User Dashboard");
        setContentView(R.layout.activity_user_dashboard);
        rvCartItem = findViewById(R.id.rvCartUser);
        rvCartItem.setLayoutManager(new LinearLayoutManager(this));
        modelArrayList.add(new ProductItemModel("1", "Burger", 10.2, 0));
        modelArrayList.add(new ProductItemModel("1", "Burger", 10.2, 0));
        modelArrayList.add(new ProductItemModel("1", "Burger", 10.2, 0));
        modelArrayList.add(new ProductItemModel("1", "Burger", 10.2, 0));
        modelArrayList.add(new ProductItemModel("1", "Burger", 10.2, 0));
        modelArrayList.add(new ProductItemModel("1", "Burger", 10.2, 0));
        modelArrayList.add(new ProductItemModel("1", "Burger", 10.2, 0));

        userCartAdapter = new UserCartAdapter(modelArrayList, this);
        rvCartItem.setAdapter(userCartAdapter);
        userCartAdapter.notifyDataSetChanged();


    }

    public void placeOrder(View view) {
        Intent intent = new Intent(UserDashboardActivity.this, OrderPlacedWaitActivity.class);
        startActivity(intent);
    }

    public void scheduleLater(View view) {
    }

    public void logout(View view) {
        SessionManager.getInstance(UserDashboardActivity.this).clearSession();
        Intent intent = new Intent(UserDashboardActivity.this, ChooseUserLoginTypeActivity.class);
        startActivity(intent);
        finish();
    }
}