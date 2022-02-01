package com.bigbird.foodorderingapp.activities.kitchen_owner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bigbird.foodorderingapp.R;
import com.bigbird.foodorderingapp.activities.admin.SignUpAdminActivity;
import com.bigbird.foodorderingapp.models.ModelKitchenUser;
import com.bigbird.foodorderingapp.utils.helpers;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.installations.Utils;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class SignUpKitchenOwnerActivity extends AppCompatActivity {
    View activity;
    FirebaseFirestore db;
    EditText etName, etContact, etMail, etRestaurantName, getEtRestaurantLocation, etPasswordKitchen;
    ImageView ivCnicFron, ivCnicBack;
    Bitmap frontImage;
    Bitmap backImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Kitchen Sign up");
        setContentView(R.layout.activity_sign_up_kitchen);
        activity = findViewById(R.id.signupKitchen);
        etName = findViewById(R.id.etNameKitchen);
        etContact = findViewById(R.id.etContactKitchen);
        etContact = findViewById(R.id.etContactKitchen);
        etMail = findViewById(R.id.etMail);
        etRestaurantName = findViewById(R.id.etRestaurantNameKitchen);
        getEtRestaurantLocation = findViewById(R.id.etRestaurantLocationKitchen);
        ivCnicFron = findViewById(R.id.ivCnicFrontKitchen);
        ivCnicBack = findViewById(R.id.ivCnicBackKitchen);
        etPasswordKitchen = findViewById(R.id.etPasswordKitchen);
        db = FirebaseFirestore.getInstance();


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
            modelKitchenUser.setType("UserTypeKitchen");
            modelKitchenUser.setContact(etContact.getText().toString());
            modelKitchenUser.setRestaurantName(etRestaurantName.getText().toString());
            modelKitchenUser.setRestaurantLocation(getEtRestaurantLocation.getText().toString());
            modelKitchenUser.setPassword(etPasswordKitchen.getText().toString());
            modelKitchenUser.setEmail(etMail.getText().toString());


            DocumentReference docRef = db.collection("UserTypeKitchen").document(modelKitchenUser.getEmail().toString());
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
                            uploadFile(true,false, modelKitchenUser, frontImage, modelKitchenUser.getEmail(), "FrontImageCnic");

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
        } else if (etRestaurantName.getText().toString().isEmpty()) {
            Snackbar.make(activity, "Enter restaurant name", Snackbar.LENGTH_LONG).show();
            result = false;
        } else if (etMail.getText().toString().isEmpty()) {
            Snackbar.make(activity, "Enter mail", Snackbar.LENGTH_LONG).show();
            result = false;
        } else if (getEtRestaurantLocation.getText().toString().isEmpty()) {
            Snackbar.make(activity, "Enter restaurant location", Snackbar.LENGTH_LONG).show();
            result = false;
        } else if (etPasswordKitchen.getText().toString().isEmpty()) {
            Snackbar.make(activity, "Enter password", Snackbar.LENGTH_LONG).show();
            result = false;
        } else if (frontImage == null) {
            Snackbar.make(activity, "Set front CNIC", Snackbar.LENGTH_LONG).show();
            result = false;
        } else if (backImage == null) {
            Snackbar.make(activity, "Set back CNINC", Snackbar.LENGTH_LONG).show();
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
        helpers.print("on result image " + requestCode + " :: " + resultCode);

        if (resultCode == RESULT_OK && requestCode == 1) {
            final Bundle extras = data.getExtras();
            if (extras != null) {
                //Get image

                frontImage = extras.getParcelable("data");
                ivCnicFron.setImageBitmap(frontImage);
                helpers.print("front image " + frontImage.toString());
            }
        } else if (resultCode == RESULT_OK && requestCode == 2) {
            final Bundle extras = data.getExtras();
            if (extras != null) {
                //Get image

                backImage = extras.getParcelable("data");
                ivCnicBack.setImageBitmap(backImage);
                helpers.print("back image " + backImage.toString());

            }
        }

    }


    private void uploadFile(boolean isFront,boolean isDone, ModelKitchenUser modelKitchenUser, Bitmap bitmap, String name, String ref) {
        helpers.showLoader(SignUpKitchenOwnerActivity.this);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://foodorderingapp-49cb0.appspot.com/");
        StorageReference mountainImagesRef = storageRef.child(modelKitchenUser.getEmail()+"/"+ref  + name + ".jpg");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = mountainImagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                helpers.hideLoader();
                Snackbar.make(activity, "Failed to upload image", Snackbar.LENGTH_LONG).show();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                mountainImagesRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            helpers.print(task.getResult().toString());
                            helpers.hideLoader();
                            if (isFront) {
                                modelKitchenUser.setCninFron(task.getResult().toString());
                            } else {
                                modelKitchenUser.setCninBack(task.getResult().toString());
                            }
                            if(isDone){
                                uploadUserToFireStore(modelKitchenUser);
                            }else{
                            uploadFile(false,true, modelKitchenUser, backImage, modelKitchenUser.getEmail(), "BackImageCnic");
                            }
                        } else {
                            helpers.hideLoader();
                            helpers.print("Failed to upload image");
                            Snackbar.make(activity, "Failed to upload image", Snackbar.LENGTH_LONG).show();

                        }

                    }
                });
            }
        });

    }

    private void uploadUserToFireStore(ModelKitchenUser modelKitchenUser) {

        db.collection("UserTypeKitchen").document(modelKitchenUser.getEmail().toString()).set(modelKitchenUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                helpers.hideLoader();
                if (task.isSuccessful()) {
                    // Snackbar.make(activity, "User created successfully", Snackbar.LENGTH_LONG).show();
                    helpers.showDialog(SignUpKitchenOwnerActivity.this, "User created successfully go to login");
                } else {
                    Snackbar.make(activity, "User creation failed", Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }

}