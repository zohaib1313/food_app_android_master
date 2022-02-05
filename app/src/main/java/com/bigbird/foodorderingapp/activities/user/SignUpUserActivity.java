package com.bigbird.foodorderingapp.activities.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.bigbird.foodorderingapp.R;
import com.bigbird.foodorderingapp.activities.admin.SignUpAdminActivity;
import com.bigbird.foodorderingapp.models.ModelAdminUser;
import com.bigbird.foodorderingapp.models.ModelUserTypeUser;
import com.bigbird.foodorderingapp.utils.helpers;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpUserActivity extends AppCompatActivity {
    EditText etName, etContact, etMail, etLocation, etPassword;
    View activity;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_user);
        etName=findViewById(R.id.etNameUser);
        etContact=findViewById(R.id.etContactUser);
        etMail=findViewById(R.id.etMailUser);
        etLocation=findViewById(R.id.etLocationUser);
        etPassword=findViewById(R.id.etPasswordlUser);
        activity=findViewById(R.id.actUserSignUp);
        db = FirebaseFirestore.getInstance();


    }

    public void signUpUser(View view) {
        if (validate()) {
            ModelUserTypeUser user=new ModelUserTypeUser();
            user.setName(etName.getText().toString());
            user.setContact(etContact.getText().toString());
            user.setEmail(etMail.getText().toString());
            user.setLocation(etLocation.getText().toString());
            user.setPassword(etPassword.getText().toString());
            user.setType("UserTypeUser");
            helpers.showLoader(this);

            //check if user does exists already or not
            DocumentReference docRef = db.collection(user.getType()).document(user.getEmail());
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
                            db.collection(user.getType()).document(user.getEmail()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    helpers.hideLoader();
                                    if (task.isSuccessful()) {
                                        // Snackbar.make(activity, "User created successfully", Snackbar.LENGTH_LONG).show();
                                        helpers.gotoLogin(SignUpUserActivity.this, "User created successfully go to login");
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
        } else if (etMail.getText().toString().isEmpty()) {
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