package com.bigbird.foodorderingapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.bigbird.foodorderingapp.R;
import com.bigbird.foodorderingapp.activities.admin.AdminDashboardActivity;
import com.bigbird.foodorderingapp.activities.admin.SignUpAdminActivity;
import com.bigbird.foodorderingapp.activities.kitchen_owner.KitchenOwnerDashboardActivity;
import com.bigbird.foodorderingapp.activities.kitchen_owner.SignUpKitchenOwnerActivity;
import com.bigbird.foodorderingapp.activities.user.SignUpUserActivity;
import com.bigbird.foodorderingapp.activities.user.UserDashboardActivity;
import com.bigbird.foodorderingapp.models.ModelAdminUser;
import com.bigbird.foodorderingapp.utils.AppConstant;
import com.bigbird.foodorderingapp.utils.SessionManager;
import com.bigbird.foodorderingapp.utils.helpers;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignInActivity extends AppCompatActivity {
    String userType;
    EditText etUserId, etUserPassword;
    FirebaseFirestore db;
    View activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        userType = getIntent().getStringExtra(AppConstant.UserType);
        etUserId = findViewById(R.id.etUserId);
        etUserPassword = findViewById(R.id.etPassword);
        activity = findViewById(R.id.signInActivity);
        db = FirebaseFirestore.getInstance();

    }


    public void gotoSignUpScreen(View view) {
        Intent intent;
        switch (userType) {
            case "UserTypeUser":
                intent = new Intent(SignInActivity.this, SignUpUserActivity.class);
                break;
            case "UserTypeKitchen":
                intent = new Intent(SignInActivity.this, SignUpKitchenOwnerActivity.class);
                break;
            case "UserTypeAdmin":
                intent = new Intent(SignInActivity.this, SignUpAdminActivity.class);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + userType);
        }
        startActivity(intent);
    }


    public void gotoDashBoard() {
        Intent intent;
        switch (userType) {
            case "UserTypeUser":
                intent = new Intent(SignInActivity.this, UserDashboardActivity.class);
                break;
            case "UserTypeKitchen":
                intent = new Intent(SignInActivity.this, KitchenOwnerDashboardActivity.class);
                break;
            case "UserTypeAdmin":
                intent = new Intent(SignInActivity.this, AdminDashboardActivity.class);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + userType);
        }
        startActivity(intent);

    }


    public void login(View view) {


        if (etUserId.getText().toString().isEmpty()) {
            Snackbar.make(view, "Enter user id", Snackbar.LENGTH_LONG).show();
            return;
        } else if (etUserPassword.getText().toString().isEmpty()) {
            Snackbar.make(view, "Enter password", Snackbar.LENGTH_LONG).show();
            return;
        }

        switch (userType) {
            case "UserTypeUser":
                userLogin();
                break;
            case "UserTypeKitchen":
                kitchenLogin();
                break;
            case "UserTypeAdmin":
                adminLogin();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + userType);
        }

    }

    private void adminLogin() {
        helpers.showLoader(this);

        DocumentReference docRef = db.collection("admins").document(etUserId.getText().toString());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                helpers.hideLoader();
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        ModelAdminUser user = document.toObject(ModelAdminUser.class);
                        if (user.getPassword().equals(etUserPassword.getText().toString())) {

                            helpers.print(user.toString());
                            SessionManager.getInstance(SignInActivity.this).createUserLoginSession(user);
                            gotoDashBoard();
                        } else {
                            Snackbar.make(activity, "Wrong user id or password", Snackbar.LENGTH_LONG).show();

                        }


                    } else {
                        Snackbar.make(activity, "Wrong user id or password", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(activity, task.getException().toString(), Snackbar.LENGTH_LONG).show();


                }
            }
        });

       /* db
                .collection("admins")

                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    helpers.hideLoader();
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                    }
                } else {

                }
            }
        });*/

    }

    private void kitchenLogin() {

    }

    private void userLogin() {

    }
}