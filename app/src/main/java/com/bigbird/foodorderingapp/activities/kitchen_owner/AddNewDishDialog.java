package com.bigbird.foodorderingapp.activities.kitchen_owner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;

import com.bigbird.foodorderingapp.R;
import com.bigbird.foodorderingapp.models.ModelKitchenUser;
import com.bigbird.foodorderingapp.models.ProductItemModel;
import com.bigbird.foodorderingapp.utils.AppConstant;
import com.bigbird.foodorderingapp.utils.SessionManager;
import com.bigbird.foodorderingapp.utils.helpers;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddNewDishDialog extends AppCompatActivity {
    EditText etDishName, etDishPrice;
    View activity;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Add new Dish");
        setContentView(R.layout.activity_add_new_dish_dialog);
        activity = findViewById(R.id.add_new_dish_dialog);
        db = FirebaseFirestore.getInstance();
        etDishName = findViewById(R.id.etDishName);
        etDishPrice = findViewById(R.id.etDishPrice);

    }


    public void addDish(View view) {

        if (validate()) {
            helpers.showLoader(AddNewDishDialog.this);
            ModelKitchenUser kitchenUser = SessionManager.getInstance(AddNewDishDialog.this).getKitchenUser();
            String id = db.collection(AppConstant.Dishes).document(kitchenUser.getEmail()).collection(kitchenUser.getEmail()).document().getId();
            ProductItemModel itemModel = new ProductItemModel();
            itemModel.setName(etDishName.getText().toString());
            itemModel.setPrice(Double.parseDouble(etDishPrice.getText().toString()));
            itemModel.setCount(1);
            itemModel.setId(id);

            helpers.print(id);
            db.collection(AppConstant.Dishes).document(kitchenUser.getEmail()).collection(kitchenUser.getEmail()).document(id).set(itemModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    helpers.hideLoader();
                    if (task.isSuccessful()) {
                        helpers.print(itemModel.getName() + " Dish Add Success");
                        helpers.showSnackBar(activity, "Dish add Success ");
                       helpers.waitAndFinish(AddNewDishDialog.this);
                    } else {
                        helpers.print(itemModel.getName() + " Dish Add Failed");
                        helpers.showSnackBar(activity, "Dish add failed " + task.getException().toString());
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


        }
        return result;
    }


    public void cancel(View view) {
        finish();
    }


}