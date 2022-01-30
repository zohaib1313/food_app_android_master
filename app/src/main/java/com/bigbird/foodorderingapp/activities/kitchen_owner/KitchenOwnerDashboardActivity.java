package com.bigbird.foodorderingapp.activities.kitchen_owner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.bigbird.foodorderingapp.R;
import com.bigbird.foodorderingapp.activities.user.OrderPlacedWaitActivity;
import com.bigbird.foodorderingapp.activities.user.UserCartAdapter;
import com.bigbird.foodorderingapp.activities.user.UserDashboardActivity;
import com.bigbird.foodorderingapp.models.ProductItemModel;

import java.util.ArrayList;

public class KitchenOwnerDashboardActivity extends AppCompatActivity {
    private com.factor.bouncy.BouncyRecyclerView rvKitchen;
    private KitchenAdapter kitchenAdapter;
    private ArrayList<ProductItemModel> modelArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Kitchen");
        setContentView(R.layout.activity_kitchen_owner_dashboard);
        rvKitchen = findViewById(R.id.rvKitchen);
        rvKitchen.setLayoutManager(new LinearLayoutManager(this));
        modelArrayList.add(new ProductItemModel("1", "Burger", 10.2, 0));
        modelArrayList.add(new ProductItemModel("1", "Burger", 10.2, 0));
        modelArrayList.add(new ProductItemModel("1", "Burger", 10.2, 0));
        modelArrayList.add(new ProductItemModel("1", "Burger", 10.2, 0));
        modelArrayList.add(new ProductItemModel("1", "Burger", 10.2, 0));
        modelArrayList.add(new ProductItemModel("1", "Burger", 10.2, 0));
        modelArrayList.add(new ProductItemModel("1", "Burger", 10.2, 0));
        modelArrayList.add(new ProductItemModel("1", "Burger", 10.2, 0));
        modelArrayList.add(new ProductItemModel("1", "Burger", 10.2, 0));
        modelArrayList.add(new ProductItemModel("1", "Burger", 10.2, 0));
        modelArrayList.add(new ProductItemModel("1", "Burger", 10.2, 0));
        modelArrayList.add(new ProductItemModel("1", "Burger", 10.2, 0));
        modelArrayList.add(new ProductItemModel("1", "Burger", 10.2, 0));
        modelArrayList.add(new ProductItemModel("1", "Burger", 10.2, 0));

        kitchenAdapter = new KitchenAdapter(modelArrayList, this);
        rvKitchen.setAdapter(kitchenAdapter);
        kitchenAdapter.notifyDataSetChanged();

    }

    public void addNewDish(View view) {

        Intent intent = new Intent(KitchenOwnerDashboardActivity.this, AddNewDishDialog.class);
        startActivity(intent);
    }

 /*   public void showDialogg(Activity activity){
        Dialog dialog = new Dialog(activity);

        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);



        Button btnAdd = (Button) dialog.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }*/
}