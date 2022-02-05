package com.bigbird.foodorderingapp.activities.kitchen_owner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;

import com.bigbird.foodorderingapp.R;
import com.bigbird.foodorderingapp.models.ModelKitchenUser;
import com.bigbird.foodorderingapp.models.ProductItemModel;
import com.bigbird.foodorderingapp.utils.AppConstant;
import com.bigbird.foodorderingapp.utils.SessionManager;
import com.bigbird.foodorderingapp.utils.helpers;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class AddNewDishDialog extends AppCompatActivity {
    EditText etDishName, etDishPrice;
    View activity;
    FirebaseFirestore db;
    Bitmap foodImage;
    de.hdodenhof.circleimageview.CircleImageView dishImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Add new Dish");
        setContentView(R.layout.activity_add_new_dish_dialog);
        activity = findViewById(R.id.add_new_dish_dialog);
        db = FirebaseFirestore.getInstance();
        etDishName = findViewById(R.id.etDishName);
        etDishPrice = findViewById(R.id.etDishPrice);
        dishImageView = findViewById(R.id.dishImage);

        dishImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage(1);
            }
        });
    }


    public void addDish(View view) {
        helpers.hideKeyboard(this);

        if (validate()) {
            helpers.showLoader(AddNewDishDialog.this);
            ModelKitchenUser kitchenUser = SessionManager.getInstance(AddNewDishDialog.this).getKitchenUser();
            String id = db.collection(AppConstant.Dishes).document(kitchenUser.getEmail()).collection(kitchenUser.getEmail()).document().getId();


            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReferenceFromUrl(AppConstant.storageRefROot);
            StorageReference mountainImagesRef = storageRef.child(AppConstant.Dishes + "/" + kitchenUser.getEmail() + "/" + id + ".jpg");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            foodImage.compress(Bitmap.CompressFormat.JPEG, 20, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = mountainImagesRef.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    helpers.hideLoader();
                    Snackbar.make(activity, "Failed to upload image", Snackbar.LENGTH_LONG).show();

                }
            }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        mountainImagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                helpers.print(uri.toString());
                                ProductItemModel itemModel = new ProductItemModel();
                                itemModel.setName(etDishName.getText().toString());
                                itemModel.setPrice(Double.parseDouble(etDishPrice.getText().toString()));
                                itemModel.setCount(0);
                                itemModel.setImage(uri.toString());
                                itemModel.setId(id);
                                itemModel.setOwnerId(kitchenUser.getEmail());
                                helpers.print(id);
                                db.collection(AppConstant.Dishes)
                                        .document(kitchenUser.getEmail())
                                        .set(itemModel)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                helpers.hideLoader();
                                                if (task.isSuccessful()) {
                                                    helpers.waitAndFinish(AddNewDishDialog.this);
                                                } else {
                                                    helpers.print(itemModel.getName() + " Dish Add Failed");
                                                    helpers.showSnackBar(activity, "Dish add failed " + task.getException().toString());
                                                }
                                            }
                                        });
                            }
                        });
                    }
                }
            });


        }


    }

    private boolean validate() {
        boolean result = true;
        if (etDishName.getText().toString().isEmpty()) {
            Snackbar.make(activity, "Enter name", Snackbar.LENGTH_LONG).show();
            result = false;
        } else if (etDishPrice.getText().toString().isEmpty()) {
            Snackbar.make(activity, "Enter contact", Snackbar.LENGTH_LONG).show();
            result = false;
        } else if (foodImage == null) {
            Snackbar.make(activity, "Select image", Snackbar.LENGTH_LONG).show();
            result = false;
        }
        return result;
    }


    public void cancel(View view) {
        finish();
    }

    @SuppressLint("IntentReset")
    public void pickImage(int requestCode) {


        ImagePicker.with(this)

                .crop()                    //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)
                //Final image resolution will be less than 1080 x 1080(Optional)
                .start();

  /*      Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("outputX", 256);
        intent.putExtra("outputY", 256);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, requestCode);*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        helpers.print("on result image " + requestCode + " :: " + resultCode);

        if (resultCode == Activity.RESULT_OK && requestCode == ImagePicker.REQUEST_CODE) {
            helpers.print("front success ");
            try {
                foodImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                dishImageView.setImageBitmap(foodImage);
            } catch (IOException e) {
                helpers.print(e.getLocalizedMessage());
            }
        }


    }
}