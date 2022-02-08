package com.bigbird.foodorderingapp.activities.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bigbird.foodorderingapp.R;
import com.bigbird.foodorderingapp.models.ModelUserTypeUser;
import com.bigbird.foodorderingapp.utils.AppConstant;
import com.bigbird.foodorderingapp.utils.helpers;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class SignUpUserActivity extends AppCompatActivity {
    EditText etName, etContact, etMail, etLocation, etPassword;
    View activity;
    FirebaseFirestore db;
    Bitmap employmentCard;
    ImageView employmentCardIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_user);
        etName = findViewById(R.id.etNameUser);
        etContact = findViewById(R.id.etContactUser);
        etMail = findViewById(R.id.etMailUser);
        etLocation = findViewById(R.id.etLocationUser);
        etPassword = findViewById(R.id.etPasswordlUser);
        activity = findViewById(R.id.actUserSignUp);
        db = FirebaseFirestore.getInstance();
        employmentCardIv = findViewById(R.id.employmentCard);
        employmentCardIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage(3);
            }
        });

    }

    public void signUpUser(View view) {
        if (validate()) {


            ModelUserTypeUser user = new ModelUserTypeUser();
            user.setName(etName.getText().toString().trim());
            user.setContact(etContact.getText().toString().trim());
            user.setEmail(etMail.getText().toString().trim());
            user.setLocation(etLocation.getText().toString().trim());
            user.setPassword(etPassword.getText().toString().trim());
            user.setType(AppConstant.UserTypeUser);
            user.setApproved(false);
            helpers.showLoader(this);
            DocumentReference docRef = db.collection(user.getType()).document(user.getEmail());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    helpers.hideLoader();
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        helpers.print(document.toString());
                        if (document.exists()) {
                            helpers.hideLoader();
                            Snackbar.make(activity, "User with email already exists", Snackbar.LENGTH_LONG).show();
                        } else {
                            uploadFile(user, employmentCard, user.getName(), user.getType());

                        }
                    } else {
                        helpers.hideLoader();
                        Snackbar.make(activity, task.getException().toString(), Snackbar.LENGTH_LONG).show();
                    }
                }
            });



        }

    }

    void uploadUserToFireStore(ModelUserTypeUser user) {
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

    private void uploadFile(ModelUserTypeUser user, Bitmap bitmap, String name, String ref) {
        helpers.showLoader(activity.getContext());
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(AppConstant.storageRefROot);
        StorageReference mountainImagesRef = storageRef.child(user.getEmail() + "/" + ref + name + ".jpg");
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

                            user.setImg(task.getResult().toString());
                            uploadUserToFireStore(user);
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


    private boolean validate() {
        boolean result = true;
        if (etName.getText().toString().trim().isEmpty()) {
            Snackbar.make(activity, "Enter name", Snackbar.LENGTH_LONG).show();
            result = false;
        } else if (etContact.getText().toString().trim().isEmpty()) {
            Snackbar.make(activity, "Enter contact", Snackbar.LENGTH_LONG).show();
            result = false;
        } else if (etMail.getText().toString().trim().isEmpty()) {
            Snackbar.make(activity, "Enter mail", Snackbar.LENGTH_LONG).show();
            result = false;
        } else if (etLocation.getText().toString().trim().isEmpty()) {
            Snackbar.make(activity, "Enter location", Snackbar.LENGTH_LONG).show();
            result = false;
        } else if (etPassword.getText().toString().trim().isEmpty()) {
            Snackbar.make(activity, "Enter password", Snackbar.LENGTH_LONG).show();
            result = false;
        } else if (employmentCard == null) {
            Snackbar.make(activity, "Select Employment Card", Snackbar.LENGTH_LONG).show();
            result = false;
        }
        return result;
    }

    @SuppressLint("IntentReset")
    public void pickImage(int requestCode) {
        ImagePicker.with(this)
                .crop()                    //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)
                //Final image resolution will be less than 1080 x 1080(Optional)
                .start(requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        helpers.print("on result image " + requestCode + " :: " + resultCode);

        if (resultCode == RESULT_OK && requestCode == 3) {
            helpers.print("front success ");
            try {
                employmentCard = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                employmentCardIv.setImageBitmap(employmentCard);

            } catch (IOException e) {
                helpers.print(e.getLocalizedMessage());

            }

        }

    }

}