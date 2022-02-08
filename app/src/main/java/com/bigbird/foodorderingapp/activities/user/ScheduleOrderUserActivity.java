package com.bigbird.foodorderingapp.activities.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigbird.foodorderingapp.R;
import com.bigbird.foodorderingapp.models.ModelOrder;
import com.bigbird.foodorderingapp.models.ModelUserTypeUser;
import com.bigbird.foodorderingapp.models.ProductItemModel;
import com.bigbird.foodorderingapp.utils.AppConstant;
import com.bigbird.foodorderingapp.utils.SessionManager;
import com.bigbird.foodorderingapp.utils.helpers;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.type.DateTime;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ScheduleOrderUserActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    View activity;
    FirebaseFirestore db;
    private ModelUserTypeUser currentUser;
    TextView dishCountSchedule, pickDateSchedule, pickTimeSchedule;
    String pickedTime = "";
    String pickedDate = "";
    private ArrayList<ProductItemModel> productsToSchedule = new ArrayList<>();
    private EditText addressEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUser = SessionManager.getInstance(this).getUserTypeUser();
        getSupportActionBar().setTitle(currentUser.getName() + "'s Dashboard");
        setContentView(R.layout.activity_schedule_order_user_activity);
        activity = findViewById(R.id.schedule_order_activity);
        db = FirebaseFirestore.getInstance();
        dishCountSchedule = findViewById(R.id.dishCountSchedule);
        pickDateSchedule = findViewById(R.id.pickDateSchedule);
        pickTimeSchedule = findViewById(R.id.pickTimeSchedule);
        addressEt = findViewById(R.id.addressEt);
        productsToSchedule = (ArrayList<ProductItemModel>) getIntent().getSerializableExtra("list");
        helpers.print(productsToSchedule.size() + " is size");
        dishCountSchedule.setText(productsToSchedule.size() + "");
        pickDateSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickDate();
            }
        });


        pickTimeSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickTime();
            }


        });
        addressEt.setText(currentUser.getLocation().toString());
    }

    private void pickDate() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Inital day selection
        );
// If you're calling this from a support Fragment
        dpd.show(getSupportFragmentManager(), "Datepickerdialog");
    }

    private void pickTime() {

        TimePickerDialog tpd = TimePickerDialog.newInstance(this, true);
        tpd.show(getSupportFragmentManager(), "Datepickerdialog");
    }

    public void doneSchedule(View view) {
        if (pickedTime.equals("") || pickedDate.equals("")) {
            helpers.showDialog(activity.getContext(), "Select schedule");
        } else {
            helpers.showLoader(activity.getContext());
            List<Task> tasks = new ArrayList<>();
            for (ProductItemModel product : productsToSchedule) {
                ModelOrder modelOrder = new ModelOrder();
                String orderId = db.collection(AppConstant.Orders).document().getId();
                modelOrder.setId(orderId);
                modelOrder.setAddress(addressEt.getText().toString());
                modelOrder.setDateTime(pickedDate.toString() + "|" + pickedTime.toString());
                modelOrder.setDishItem(product);
                modelOrder.setScheduled(true);
                modelOrder.setOrderPlacer(currentUser);
                tasks.add(db.collection(AppConstant.Orders)
                        .document(orderId)
                        .set(modelOrder));
            }

            Tasks.whenAllSuccess(tasks).addOnCompleteListener(new OnCompleteListener<List<Object>>() {
                @Override
                public void onComplete(@NonNull Task<List<Object>> task) {
                    helpers.hideLoader();
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity.getContext());
                    alertDialog.setTitle("Success");
                    alertDialog.setMessage("Your order schedules successfully");
                    alertDialog.create();
                    alertDialog.setCancelable(false);
                    alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                            dialogInterface.dismiss();
                        }
                    });
                    alertDialog.show();
                }
            });



         /*   Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    helpers.hideLoader();
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity.getContext());
                    alertDialog.setTitle("Success");
                    alertDialog.setMessage("Your order schedules successfully");
                    alertDialog.create();
                    alertDialog.setCancelable(false);
                    alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                            dialogInterface.dismiss();
                        }
                    });
                    alertDialog.show();
                }
            }, 3000);*/
        }
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String time = "" + hourOfDay + ":" + minute;
        pickTimeSchedule.setText(time);
        pickedTime = time;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "" + dayOfMonth + ":" + (monthOfYear + 1) + ":" + year;
        pickDateSchedule.setText(date);
        pickedDate = date;
    }
}