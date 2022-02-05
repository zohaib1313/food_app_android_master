package com.bigbird.foodorderingapp.activities.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.bigbird.foodorderingapp.R;
import com.bigbird.foodorderingapp.activities.ChooseUserLoginTypeActivity;
import com.bigbird.foodorderingapp.models.Actions;
import com.bigbird.foodorderingapp.models.AdminViewsModel;
import com.bigbird.foodorderingapp.models.ModelAdminUser;
import com.bigbird.foodorderingapp.models.ModelKitchenUser;
import com.bigbird.foodorderingapp.models.ModelOrder;
import com.bigbird.foodorderingapp.models.ProductItemModel;
import com.bigbird.foodorderingapp.utils.AppConstant;
import com.bigbird.foodorderingapp.utils.SessionManager;
import com.bigbird.foodorderingapp.utils.helpers;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminDashboardActivity extends AppCompatActivity {
    private com.factor.bouncy.BouncyRecyclerView rvAdmin;
    private AdminAdapter adminAdapter;
    private ArrayList<AdminViewsModel> modelArrayList = new ArrayList<>();
    FirebaseFirestore db;
    private ModelAdminUser currentUser;
    SwipyRefreshLayout activity;
    EditText etUserNameKit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Admin Dashboard");
        setContentView(R.layout.activity_admin_dashboard);
        activity = findViewById(R.id.activity_admin_dashboardId);
        rvAdmin = findViewById(R.id.rvAdmin);
        rvAdmin.setLayoutManager(new LinearLayoutManager(this));
        adminAdapter = new AdminAdapter(modelArrayList, this);
        rvAdmin.setAdapter(adminAdapter);
        adminAdapter.notifyDataSetChanged();
        currentUser = SessionManager.getInstance(this).getAdminUser();
        db = FirebaseFirestore.getInstance();
        etUserNameKit = findViewById(R.id.etUserNameKit);
        loadData();
        activity.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                if (activity.isRefreshing()) {
                    loadData();
                    activity.setRefreshing(false);
                }

            }
        });
    }

    private void loadData() {
        modelArrayList.clear();

        helpers.showLoader(activity.getContext());
        List<Task> listOfTasks = new ArrayList<>();


        listOfTasks.add(loadKitchens());
        //   listOfTasks.add(loadAdmins());
        listOfTasks.add(loadUsers());
        listOfTasks.add(loadOrders());
        Tasks.whenAllSuccess(listOfTasks).addOnSuccessListener(new OnSuccessListener<List<Object>>() {
            @Override
            public void onSuccess(List<Object> objects) {
                helpers.hideLoader();
                adminAdapter.notifyDataSetChanged();
            }
        });


    }

    private Task loadOrders() {
        return db.collection(AppConstant.Orders).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                AdminViewsModel viewsModel = new AdminViewsModel("Orders", "", Actions.actionTotalOrders, R.color.white, R.color.Chocolate);

                String value = "Total:" + queryDocumentSnapshots.size();
                int completed = 0;
                int scheduled = 0;
                for (QueryDocumentSnapshot snapshots : queryDocumentSnapshots) {
                    ModelOrder modelOrder = snapshots.toObject(ModelOrder.class);
                    if (modelOrder.isCompleted()) {
                        completed++;
                    } else if (!modelOrder.isCompleted() && modelOrder.isScheduled()) {
                        scheduled++;
                    }

                }
                value = value + " Completed:" + completed + " Scheduled:" + scheduled;
                viewsModel.setValue(value);
                modelArrayList.add(viewsModel);
            }
        });
    }

    private Task loadUsers() {

        return db.collection(AppConstant.UserTypeUser).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                modelArrayList.add(new AdminViewsModel("Users", queryDocumentSnapshots.size() + "", Actions.actionUsers, R.color.white, R.color.teal_200));

                for (QueryDocumentSnapshot snapshots : queryDocumentSnapshots) {


                }

            }
        });
    }

    private Task loadAdmins() {
        return db.collection(AppConstant.UserTypeAdmin).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                modelArrayList.add(new AdminViewsModel("Admins", queryDocumentSnapshots.size() + "", Actions.actionAdmin, R.color.white, R.color.green));

                for (QueryDocumentSnapshot snapshots : queryDocumentSnapshots) {


                }

            }
        });
    }

    private Task loadKitchens() {
        return db.collection(AppConstant.UserTypeKitchen).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                AdminViewsModel viewsModel = new AdminViewsModel("Kitchen", "", Actions.actionKitchen, R.color.white, R.color.green);
                int total = queryDocumentSnapshots.size();
                String value = "Total:" + total;

                int approved = 0;

                for (QueryDocumentSnapshot snapshots : queryDocumentSnapshots) {
                    ModelKitchenUser modelOrder = snapshots.toObject(ModelKitchenUser.class);
                    helpers.print(snapshots.getData().toString() + "");
                    helpers.print(modelOrder.isApproved() + "");
                    if (modelOrder.isApproved()) {
                        approved++;
                    }
                }
                viewsModel.setValue(value + " Approved:" + approved + " Not-Approved:" + (total - approved));
                modelArrayList.add(viewsModel);
            }
        });
    }

    public void logout(View view) {
        SessionManager.getInstance(AdminDashboardActivity.this).clearSession();
        Intent intent = new Intent(AdminDashboardActivity.this, ChooseUserLoginTypeActivity.class);
        startActivity(intent);
        finish();
    }

    public void done(View view) {
        if (etUserNameKit.getText().toString().isEmpty()) {
            helpers.showDialog(activity.getContext(), "Enter username");
        } else {
            helpers.showLoader(activity.getContext());
            db.collection(AppConstant.UserTypeKitchen)
                    .document(etUserNameKit.getText().toString())
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    helpers.hideLoader();
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            helpers.print("doc exists");
                            ModelKitchenUser modelKitchenUser = document.toObject(ModelKitchenUser.class);
                            helpers.print(modelKitchenUser.toString());
                            showUpdateDialog(modelKitchenUser);

                        } else {
                            helpers.showDialog(activity.getContext(), "User does not exists");
                        }
                    } else {
                        helpers.print("doc get failed");

                    }

                }
            });


        }
    }

    private void showUpdateDialog(ModelKitchenUser modelKitchenUser) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity.getContext());
        alertDialog.setTitle("Update user");
        alertDialog.setMessage("Choose to update");
        alertDialog.create();
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Approve", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                helpers.showLoader(activity.getContext());

                modelKitchenUser.setApproved(true);
                db.collection(AppConstant.UserTypeKitchen).document(modelKitchenUser.getEmail()).set(modelKitchenUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        helpers.hideLoader();

                        helpers.showSnackBar(activity, "Updated");
                        dialogInterface.dismiss();
                    }
                });


            }
        });
        alertDialog.setNegativeButton("Not-Approved", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                helpers.showLoader(activity.getContext());
                modelKitchenUser.setApproved(false);
                db.collection(AppConstant.UserTypeKitchen).document(modelKitchenUser.getEmail()).set(modelKitchenUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        helpers.hideLoader();
                        helpers.showSnackBar(activity, "Updated");
                        dialogInterface.dismiss();
                    }
                });

            }
        });
        alertDialog.show();
    }
}