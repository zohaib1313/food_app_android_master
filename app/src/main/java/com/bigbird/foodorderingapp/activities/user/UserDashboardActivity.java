package com.bigbird.foodorderingapp.activities.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bigbird.foodorderingapp.R;
import com.bigbird.foodorderingapp.activities.ChooseUserLoginTypeActivity;
import com.bigbird.foodorderingapp.models.ModelOrder;
import com.bigbird.foodorderingapp.models.ModelUserTypeUser;
import com.bigbird.foodorderingapp.models.ProductItemModel;
import com.bigbird.foodorderingapp.utils.AppConstant;
import com.bigbird.foodorderingapp.utils.IOnItemClickListener;
import com.bigbird.foodorderingapp.utils.SessionManager;
import com.bigbird.foodorderingapp.utils.helpers;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.google.type.DateTime;

import java.time.LocalDateTime;
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
    String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = SessionManager.getInstance(this).getUserTypeUser();
        getSupportActionBar().setTitle(user.getName() + "'s Dashboard");
        setContentView(R.layout.activity_user_dashboard);
        rvCartItem = findViewById(R.id.rvCartUser);
        rvCartItem.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        userCartAdapter = new UserCartAdapter(modelArrayList, this);
        rvCartItem.setAdapter(userCartAdapter);
        userCartAdapter.notifyDataSetChanged();
        etTotalPrice = findViewById(R.id.etTotalPrice);
        activity = findViewById(R.id.activityUser);
        db = FirebaseFirestore.getInstance();
        getAllProducts();

        address = user.getLocation();
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
                .get(Source.SERVER)
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task2) {
                        helpers.hideLoader();
                        if (task2.isSuccessful()) {

                            List<DocumentSnapshot> myListOfDocuments = task2.getResult().getDocuments();
                            for (DocumentSnapshot snapshot : myListOfDocuments) {
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
                        helpers.hideLoader();
                        userCartAdapter.notifyDataSetChanged();
                    }
                });

    }

    public void placeOrder(View view) {
        if (totalPrice > 0.0) {
            showAddressPickDialog();
        } else {
            helpers.showSnackBar(activity, "Please select any dish to order");
        }
    }

    private void uploadOrderData() {

        helpers.showLoader(UserDashboardActivity.this);
        List<Task> tasks = new ArrayList<>();
        for (ProductItemModel product : modelArrayList) {
            if (product.getCount() > 0) {
                ModelOrder modelOrder = new ModelOrder();
                String orderId = db.collection(AppConstant.Orders).document().getId();
                modelOrder.setId(orderId);
                modelOrder.setDateTime("");
                modelOrder.setDishItem(product);
                modelOrder.setScheduled(false);
                modelOrder.setOrderPlacer(user);
                modelOrder.setAddress(address);
                tasks.add(db.collection(AppConstant.Orders)
                        .document(orderId)
                        .set(modelOrder));
            }
        }

        Tasks.whenAllSuccess(tasks).addOnCompleteListener(new OnCompleteListener<List<Object>>() {
            @Override
            public void onComplete(@NonNull Task<List<Object>> task) {

                if (task.isSuccessful()) {
                    helpers.hideLoader();
                    getAllProducts();
                    Intent intent = new Intent(UserDashboardActivity.this, OrderPlacedWaitActivity.class);
                    startActivity(intent);
                } else {
                    helpers.showDialog(activity.getContext(), "Error while placing order " + task.getException().toString());
                }


            }
        });
    }

    private void showAddressPickDialog() {
         EditText taskEditText = new EditText(activity.getContext());
        taskEditText.setText(address);
        AlertDialog dialog = new AlertDialog.Builder(activity.getContext())
                .setTitle("Change Address")
                .setMessage("Chane Address or use default")
                .setView(taskEditText)
                .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        address = String.valueOf(taskEditText.getText());
                        uploadOrderData();
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();

    }

    public void scheduleLater(View view) {
        if (totalPrice > 0.0) {
            Intent intent = new Intent(UserDashboardActivity.this, ScheduleOrderUserActivity.class);
            ArrayList<ProductItemModel> itemModelArrayList = new ArrayList<>();
            for (ProductItemModel item : modelArrayList) {
                if (item.getCount() > 0) {
                    itemModelArrayList.add(item);
                }
            }
            intent.putExtra("list", itemModelArrayList);
            startActivity(intent);
            getAllProducts();
        } else {
            helpers.showSnackBar(activity, "Please select any dish to order");
        }

    }

    public void logout(View view) {
        SessionManager.getInstance(UserDashboardActivity.this).clearSession();
        Intent intent = new Intent(UserDashboardActivity.this, ChooseUserLoginTypeActivity.class);
        startActivity(intent);
        finish();
    }
}