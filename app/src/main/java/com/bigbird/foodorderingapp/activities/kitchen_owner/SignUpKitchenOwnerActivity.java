package com.bigbird.foodorderingapp.activities.kitchen_owner;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bigbird.foodorderingapp.R;
import com.bigbird.foodorderingapp.models.ModelKitchenUser;
import com.bigbird.foodorderingapp.utils.helpers;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpKitchenOwnerActivity extends AppCompatActivity {
    View activity;
    FirebaseFirestore db;
    EditText etName, etContact, etRestaurantName, getEtRestaurantLocation, etPasswordKitchen;
    ImageView ivCnicFron, ivCnicBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Kitchen Sign up");
        setContentView(R.layout.activity_sign_up_kitchen);
        activity = findViewById(R.id.signupKitchen);
        db = FirebaseFirestore.getInstance();
        etName = findViewById(R.id.etNameKitchen);
        etContact = findViewById(R.id.etContactKitchen);
        etRestaurantName = findViewById(R.id.etRestaurantNameKitchen);
        getEtRestaurantLocation = findViewById(R.id.etRestaurantLocationKitchen);
        ivCnicFron = findViewById(R.id.ivCnicFrontKitchen);
        ivCnicBack = findViewById(R.id.ivCnicBackKitchen);
        etPasswordKitchen = findViewById(R.id.etPasswordKitchen);




        ivCnicFron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage(1);
            }
        });

        ivCnicBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage(2);
            }
        });

    }


    public void signUpKitchen(View view) {
        if (validate()) {

            ModelKitchenUser modelKitchenUser = new ModelKitchenUser();
            modelKitchenUser.setName(etName.getText().toString());
            modelKitchenUser.setContact(etContact.getText().toString());
            modelKitchenUser.setRestaurantName(etRestaurantName.getText().toString());
            modelKitchenUser.setRestaurantLocation(getEtRestaurantLocation.getText().toString());
            modelKitchenUser.setPassword(etPasswordKitchen.getText().toString());


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
        } else if (etRestaurantName.getText().toString().isEmpty()) {
            Snackbar.make(activity, "Enter restaurant name", Snackbar.LENGTH_LONG).show();
            result = false;
        } else if (getEtRestaurantLocation.getText().toString().isEmpty()) {
            Snackbar.make(activity, "Enter restaurant location", Snackbar.LENGTH_LONG).show();
            result = false;
        } else if (etPasswordKitchen.getText().toString().isEmpty()) {
            Snackbar.make(activity, "Enter password", Snackbar.LENGTH_LONG).show();
            result = false;
        }
        return result;
    }

    @SuppressLint("IntentReset")
    public void pickImage(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("outputX", 256);
        intent.putExtra("outputY", 256);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, requestCode);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        helpers.print("on result image "+requestCode+" :: "+resultCode);

        if (resultCode == RESULT_OK && requestCode == 1) {
            final Bundle extras = data.getExtras();
            if (extras != null) {
                //Get image
                Bitmap frontImage = extras.getParcelable("data");
                ivCnicFron.setImageBitmap(frontImage);
                helpers.print("front image "+frontImage.toString());

            }
        }else  if (resultCode == RESULT_OK && requestCode == 2) {
            final Bundle extras = data.getExtras();
            if (extras != null) {
                //Get image
                Bitmap backImage = extras.getParcelable("data");
                ivCnicFron.setImageBitmap(backImage);
                helpers.print("back image "+backImage.toString());

            }
        }

    }


}