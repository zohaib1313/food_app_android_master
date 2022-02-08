package com.bigbird.foodorderingapp.activities.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.bigbird.foodorderingapp.R;
import com.bigbird.foodorderingapp.models.Actions;
import com.bigbird.foodorderingapp.models.AdminViewsModel;
import com.bigbird.foodorderingapp.models.ModelAdminUser;
import com.bigbird.foodorderingapp.models.ModelKitchenUser;
import com.bigbird.foodorderingapp.models.ModelUserTypeUser;
import com.bigbird.foodorderingapp.utils.AppConstant;
import com.bigbird.foodorderingapp.utils.IStatusChangeListener;
import com.bigbird.foodorderingapp.utils.SessionManager;
import com.bigbird.foodorderingapp.utils.helpers;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ChangeUserStatusActivity extends AppCompatActivity {
    private ChangeUserStatusAdapter changeUserStatusAdapter;
    private ArrayList<ChangeUserStatusModel> modelArrayList = new ArrayList<>();
    FirebaseFirestore db;
    View activity;
    private ModelAdminUser currentUser;
    private com.factor.bouncy.BouncyRecyclerView rv;
    String userToChangeType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userToChangeType = getIntent().getStringExtra(AppConstant.UserType);
        getSupportActionBar().setTitle(userToChangeType);
        setContentView(R.layout.activity_change_user_status);
        activity = findViewById(R.id.changeUserStatusAct);
        rv = findViewById(R.id.changeUserStatusRv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        changeUserStatusAdapter = new ChangeUserStatusAdapter(modelArrayList, this);
        rv.setAdapter(changeUserStatusAdapter);
        currentUser = SessionManager.getInstance(this).getAdminUser();
        db = FirebaseFirestore.getInstance();
        if (userToChangeType.equals(AppConstant.UserTypeKitchen)) {
            modelArrayList.clear();
            loadKitchenUser();
        } else if (userToChangeType.equals(AppConstant.UserTypeUser)) {
            modelArrayList.clear();
            loadEmployementUser();
        }
        changeUserStatusAdapter.setiStatusChangeListener(new IStatusChangeListener() {
            @Override
            public void onItemClick(int position, boolean status, String userType, String userMail) {
                helpers.showLoader(activity.getContext());
                db.collection(userType).document(userMail).update("approved", status)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                modelArrayList.get(position).setStatus(status);
                                changeUserStatusAdapter.notifyDataSetChanged();
                                helpers.hideLoader();
                                helpers.showSnackBar(activity, "Updated");

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        helpers.showSnackBar(activity, "Updation Failed " + e.getLocalizedMessage().toString());

                    }
                });

            }
        });

    }

    private void loadEmployementUser() {
        modelArrayList.clear();
        helpers.print("Loading  user");
        db.collection(AppConstant.UserTypeUser).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                helpers.print("Loading  user on success");
                for (QueryDocumentSnapshot snapshots : queryDocumentSnapshots) {
                    ModelUserTypeUser user = snapshots.toObject(ModelUserTypeUser.class);
                    ChangeUserStatusModel model = new ChangeUserStatusModel();
                    model.setUserName(user.getEmail());
                    model.setCnicFront("");
                    model.setCnicBack("");
                    model.setContact(user.getContact());
                    model.setStatus(user.isApproved());
                    model.setUserType(user.getType());
                    model.setEmployCard(user.getImg());
                    modelArrayList.add(model);
                }
                changeUserStatusAdapter.notifyDataSetChanged();
                helpers.hideLoader();
            }
        });
    }

    private void loadKitchenUser() {
        modelArrayList.clear();
        helpers.print("Loading kitchen user");
        db.collection(AppConstant.UserTypeKitchen).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                helpers.print("Loading kitchen user on success");
                for (QueryDocumentSnapshot snapshots : queryDocumentSnapshots) {
                    ModelKitchenUser user = snapshots.toObject(ModelKitchenUser.class);
                    ChangeUserStatusModel model = new ChangeUserStatusModel();
                    model.setUserName(user.getEmail());
                    model.setCnicFront(user.getCninFron());
                    model.setCnicBack(user.getCninBack());
                    model.setContact(user.getContact());
                    model.setStatus(user.isApproved());
                    model.setUserType(user.getType());
                    model.setEmployCard("");
                    modelArrayList.add(model);
                }
                changeUserStatusAdapter.notifyDataSetChanged();
                helpers.hideLoader();
            }
        });
    }


}