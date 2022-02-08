package com.bigbird.foodorderingapp.activities.kitchen_owner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.bigbird.foodorderingapp.R;
import com.bigbird.foodorderingapp.activities.ChooseUserLoginTypeActivity;
import com.bigbird.foodorderingapp.activities.admin.AdminDashboardActivity;
import com.bigbird.foodorderingapp.activities.user.OrderPlacedWaitActivity;
import com.bigbird.foodorderingapp.activities.user.UserCartAdapter;
import com.bigbird.foodorderingapp.activities.user.UserDashboardActivity;
import com.bigbird.foodorderingapp.models.ModelKitchenUser;
import com.bigbird.foodorderingapp.models.ProductItemModel;
import com.bigbird.foodorderingapp.utils.AppConstant;
import com.bigbird.foodorderingapp.utils.IOnItemClickListener;
import com.bigbird.foodorderingapp.utils.SPManager;
import com.bigbird.foodorderingapp.utils.SessionManager;
import com.bigbird.foodorderingapp.utils.helpers;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class KitchenOwnerDashboardActivity extends AppCompatActivity {
    private com.factor.bouncy.BouncyRecyclerView rvKitchen;
    private KitchenAdapter kitchenAdapter;
    private ArrayList<ProductItemModel> modelArrayList = new ArrayList<>();
    FirebaseFirestore db;
    private ModelKitchenUser modelKitchenUser;
    View activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        modelKitchenUser = SessionManager.getInstance(this).getKitchenUser();
        getSupportActionBar().setTitle(modelKitchenUser.getName()
                + "'s " + "Kitchen");
        setContentView(R.layout.activity_kitchen_owner_dashboard);
        db = FirebaseFirestore.getInstance();
        rvKitchen = findViewById(R.id.rvKitchen);
        rvKitchen.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        kitchenAdapter = new KitchenAdapter(modelArrayList, this);
        rvKitchen.setAdapter(kitchenAdapter);
        kitchenAdapter.notifyDataSetChanged();
        activity = findViewById(R.id.kitchenUserDashBoard);
        kitchenAdapter.setiOnItemClickListener(new IOnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                helpers.print(position + "");
                showMyDialog(position);
            }
        });
       // helpers.showLoader(activity.getContext());
        loadData();
    }

    public void addNewDish(View view) {
        Intent intent = new Intent(KitchenOwnerDashboardActivity.this, AddNewDishDialog.class);
        startActivity(intent);
    }

    public void logout(View view) {
        SessionManager.getInstance(KitchenOwnerDashboardActivity.this).clearSession();
        Intent intent = new Intent(KitchenOwnerDashboardActivity.this, ChooseUserLoginTypeActivity.class);
        startActivity(intent);
        finish();
    }


    private void showMyDialog(int position) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(KitchenOwnerDashboardActivity.this);
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Are you sure you want delete this?");
        // alertDialog.setIcon(R.drawable.delete);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                helpers.showLoader(KitchenOwnerDashboardActivity.this);
                db.collection(AppConstant.Dishes).document(modelKitchenUser.getEmail()).collection(modelKitchenUser.getEmail())
                        .document(modelArrayList.get(position).getId())
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                helpers.hideLoader();
                                helpers.print("Success Delete");
                                helpers.showSnackBar(activity, "Deletion Success");
                                modelArrayList.remove(position);
                                kitchenAdapter.notifyDataSetChanged();
                                //loadData();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                helpers.hideLoader();
                                helpers.showSnackBar(activity, "Deletion Failed");
                                helpers.print("Error Delete");
                            }
                        });

            }
        });


        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    void loadData() {
        db.collection(AppConstant.Dishes)
                .whereEqualTo("ownerId", modelKitchenUser.getEmail())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            helpers.print("listen:error " + e.getLocalizedMessage());
                            return;
                        }
                        //   modelArrayList.clear();
                        for (DocumentChange dc : snapshots.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                helpers.print("Item added");
                                ProductItemModel itemModel = dc.getDocument().toObject(ProductItemModel.class);
                                helpers.print(itemModel.toString());
                                modelArrayList.add(itemModel);
                                kitchenAdapter.notifyDataSetChanged();

                            }
//                            else if (dc.getType() == DocumentChange.Type.REMOVED) {
//                                ProductItemModel itemModel = dc.getDocument().toObject(ProductItemModel.class);
//                                helpers.print("Item removed");
//                                modelArrayList.remove(itemModel);
//                                kitchenAdapter.notifyDataSetChanged();
//
//                            }
                        }
                    }
                });
    }
}