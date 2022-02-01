package com.bigbird.foodorderingapp.activities.kitchen_owner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.bigbird.foodorderingapp.R;
import com.bigbird.foodorderingapp.models.ModelKitchenUser;
import com.bigbird.foodorderingapp.models.ProductItemModel;
import com.bigbird.foodorderingapp.utils.AppConstant;
import com.bigbird.foodorderingapp.utils.SessionManager;
import com.bigbird.foodorderingapp.utils.helpers;
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
        ModelKitchenUser kitchenUser = SessionManager.getInstance(AddNewDishDialog.this).getKitchenUser();
        DocumentReference newCityRef = db.collection(AppConstant.Dishes).document();
        helpers.print(db.collection(AppConstant.Dishes).document(kitchenUser.getEmail()).collection(kitchenUser.getEmail()).document().getId());

        if (validate()) {
            ProductItemModel itemModel = new ProductItemModel();
            itemModel.setName(etDishName.getText().toString());
            itemModel.setPrice(Double.parseDouble(etDishPrice.getText().toString()));
            itemModel.setCount(1);..
//            ModelKitchenUser kitchenUser = SessionManager.getInstance(AddNewDishDialog.this).getKitchenUser();
//       db.collection(AppConstant.Dishes).document(kitchenUser.getEmail()).getId();


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