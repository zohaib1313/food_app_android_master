package com.bigbird.foodorderingapp.activities.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.bigbird.foodorderingapp.R;
import com.bigbird.foodorderingapp.activities.kitchen_owner.KitchenAdapter;
import com.bigbird.foodorderingapp.models.AdminViewsModel;
import com.bigbird.foodorderingapp.models.ProductItemModel;

import java.util.ArrayList;

public class AdminDashboardActivity extends AppCompatActivity {
    private com.factor.bouncy.BouncyRecyclerView rvAdmin;
    private AdminAdapter adminAdapter;
    private ArrayList<AdminViewsModel> modelArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Admin Dashboard");
        setContentView(R.layout.activity_admin_dashboard);

        rvAdmin = findViewById(R.id.rvAdmin);
        rvAdmin.setLayoutManager(new LinearLayoutManager(this));
        modelArrayList.add(new AdminViewsModel("Number of user:", "12"));
        modelArrayList.add(new AdminViewsModel("Number of user:", "12"));
        modelArrayList.add(new AdminViewsModel("Number of user:", "12"));
        modelArrayList.add(new AdminViewsModel("Number of user:", "12"));
        modelArrayList.add(new AdminViewsModel("Number of user:", "12"));
        modelArrayList.add(new AdminViewsModel("Number of user:", "12"));
        modelArrayList.add(new AdminViewsModel("Number of user:", "12"));
        modelArrayList.add(new AdminViewsModel("Number of user:", "12"));
        modelArrayList.add(new AdminViewsModel("Number of user:", "12"));

        adminAdapter = new AdminAdapter(modelArrayList, this);
        rvAdmin.setAdapter(adminAdapter);
        adminAdapter.notifyDataSetChanged();
    }
}