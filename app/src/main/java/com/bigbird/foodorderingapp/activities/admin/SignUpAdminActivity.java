package com.bigbird.foodorderingapp.activities.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.bigbird.foodorderingapp.R;
import com.bigbird.foodorderingapp.models.ModelAdminUser;
import com.bigbird.foodorderingapp.utils.helpers;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpAdminActivity extends AppCompatActivity {
    EditText etName, etContact, etEmail, etLocation, etPassword;
    View activity;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_admin);
        etName = findViewById(R.id.etName);
        etContact = findViewById(R.id.etContact);
        etEmail = findViewById(R.id.etMail);
        etLocation = findViewById(R.id.etLocation);
        etPassword = findViewById(R.id.etPassword);
        activity = findViewById(R.id.adminSignUp);
        db = FirebaseFirestore.getInstance();

    }

    public void signUpAdmin(View view) {

        if (validate()) {
            ModelAdminUser user = new ModelAdminUser();
            user.setName(etName.getText().toString());
            user.setContact(etContact.getText().toString());
            user.setEmail(etEmail.getText().toString());
            user.setLocation(etLocation.getText().toString());
            user.setPassword(etName.getText().toString());
            user.setType("UserTypeAdmin");
            helpers.showLoader(this);

            //check if user does exists already or not
            DocumentReference docRef = db.collection("admins").document(etEmail.getText().toString());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        helpers.print(document.toString());


                        if (document.exists()) {
                            helpers.hideLoader();
                                Snackbar.make(activity, "User with email already exists", Snackbar.LENGTH_LONG).show();

                        } else {
                            db.collection("admins").document(etEmail.getText().toString()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    helpers.hideLoader();
                                    if (task.isSuccessful()) {
                                        // Snackbar.make(activity, "User created successfully", Snackbar.LENGTH_LONG).show();
                                        helpers.showDialog(SignUpAdminActivity.this, "User created successfully go to login");
                                    } else {
                                        Snackbar.make(activity, "User creation failed", Snackbar.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    } else {
                        helpers.hideLoader();
                        Snackbar.make(activity, task.getException().toString(), Snackbar.LENGTH_LONG).show();
                    }
                }
            });



        }


    }

    private boolean validate() {
        boolean result = true;
        if (etName.getText().toString().isEmpty()) {
            Snackbar.make(activity, "Enter name", Snackbar.LENGTH_LONG).show();
            result = false;
        } else if (etContact.getText().toString().isEmpty()) {
            Snackbar.make(activity, "Enter contact", Snackbar.LENGTH_LONG).show();

            result = false;


        } else if (etEmail.getText().toString().isEmpty()) {
            Snackbar.make(activity, "Enter mail", Snackbar.LENGTH_LONG).show();

            result = false;


        } else if (etLocation.getText().toString().isEmpty()) {
            Snackbar.make(activity, "Enter location", Snackbar.LENGTH_LONG).show();

            result = false;


        } else if (etPassword.getText().toString().isEmpty()) {
            Snackbar.make(activity, "Enter password", Snackbar.LENGTH_LONG).show();

            result = false;


        }
        return result;
    }


}