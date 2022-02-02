package com.bigbird.foodorderingapp.activities.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigbird.foodorderingapp.R;
import com.bigbird.foodorderingapp.activities.ChooseUserLoginTypeActivity;
import com.bigbird.foodorderingapp.models.ModelUserTypeUser;
import com.bigbird.foodorderingapp.models.ProductItemModel;
import com.bigbird.foodorderingapp.utils.AppConstant;
import com.bigbird.foodorderingapp.utils.IOnItemClickListener;
import com.bigbird.foodorderingapp.utils.SessionManager;
import com.bigbird.foodorderingapp.utils.helpers;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UserDashboardActivity extends AppCompatActivity {

    private com.factor.bouncy.BouncyRecyclerView rvCartItem;
    private UserCartAdapter userCartAdapter;
    private ArrayList<ProductItemModel> modelArrayList = new ArrayList<>();
    View activity;
    FirebaseFirestore db;
    private ModelUserTypeUser user;
    private TextView etTotalPrice;
    private double totalPrice = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = SessionManager.getInstance(this).getUserTypeUser();
        getSupportActionBar().setTitle(user.getName() + "'s Dashboard");
        setContentView(R.layout.activity_user_dashboard);
        rvCartItem = findViewById(R.id.rvCartUser);
        rvCartItem.setLayoutManager(new LinearLayoutManager(this));
        userCartAdapter = new UserCartAdapter(modelArrayList, this);
        rvCartItem.setAdapter(userCartAdapter);
        userCartAdapter.notifyDataSetChanged();
        etTotalPrice = findViewById(R.id.etTotalPrice);
        activity = findViewById(R.id.activityUser);
        db = FirebaseFirestore.getInstance();
        getAllProducts();

        userCartAdapter.setAddClickListener(new IOnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ProductItemModel itemModel = modelArrayList.get(position);
                int count = itemModel.getCount();
                itemModel.setCount(count + 1);
                modelArrayList.set(position, itemModel);
                userCartAdapter.notifyDataSetChanged();
                calculateTotalPrice();
            }
        });
        userCartAdapter.setRemoveClickListener(new IOnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ProductItemModel itemModel = modelArrayList.get(position);
                int count = itemModel.getCount();
                if (count > 0) {
                    itemModel.setCount(count - 1);
                }
                modelArrayList.set(position, itemModel);
                userCartAdapter.notifyDataSetChanged();
                calculateTotalPrice();
            }
        });

    }

    private void calculateTotalPrice() {
        totalPrice = 0.0;

        for (ProductItemModel productItemModel : modelArrayList) {

            if (productItemModel.getCount() > 0) {
                double price = productItemModel.getPrice() * productItemModel.getCount();
                totalPrice = totalPrice + price;
            }

        }


        etTotalPrice.setText(totalPrice + " Pkr");
    }

    private void getAllProducts() {

        modelArrayList.clear();
        helpers.showLoader(activity.getContext());
        db
                .collection(AppConstant.Dishes)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                            for (DocumentSnapshot documentSnapshot : myListOfDocuments) {
                                helpers.print(documentSnapshot.getData() + "");
                                db
                                        .collection(AppConstant.Dishes).document(documentSnapshot.getId())
                                        .collection(documentSnapshot.getId()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                        if (task.isSuccessful()) {
                                            for (DocumentSnapshot snapshot : task.getResult().getDocuments()) {
                                                helpers.print(snapshot.getData() + "");
                                                modelArrayList.add(snapshot.toObject(ProductItemModel.class));
                                            }
                                            userCartAdapter.notifyDataSetChanged();
                                            if (modelArrayList.isEmpty()) {
                                                helpers.print("No Product found");
                                            }
                                            calculateTotalPrice();
                                        } else {
                                            helpers.hideLoader();
                                            helpers.showSnackBar(activity, "Error getting data");
                                        }
                                    }
                                });
                            }
                        } else {
                            helpers.hideLoader();
                            helpers.showSnackBar(activity, "Error getting data");

                        }
                        helpers.hideLoader();
                        userCartAdapter.notifyDataSetChanged();
                    }
                });
        /*db.collection("Dishes").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                helpers.print(task.getResult().getDocuments().toString());
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        helpers.print(document.getData().toString());
                    }

                } else {
                    helpers.print("Error getting doc "+task.getException().getLocalizedMessage());

                }
            }
        });*/

    }

    public void placeOrder(View view) {
        if (totalPrice > 0.0) {
            Intent intent = new Intent(UserDashboardActivity.this, OrderPlacedWaitActivity.class);
            startActivity(intent);
            getAllProducts();
        } else {
       helpers.showSnackBar(activity,"Please select any dish to order");
        }
    }

    public void scheduleLater(View view) {
        if (totalPrice > 0.0) {
//            Intent intent = new Intent(UserDashboardActivity.this, OrderPlacedWaitActivity.class);
//            startActivity(intent);
//            getAllProducts();
        } else {
            helpers.showSnackBar(activity,"Please select any dish to order");
        }

    }

    public void logout(View view) {
        SessionManager.getInstance(UserDashboardActivity.this).clearSession();
        Intent intent = new Intent(UserDashboardActivity.this, ChooseUserLoginTypeActivity.class);
        startActivity(intent);
        finish();
    }
}