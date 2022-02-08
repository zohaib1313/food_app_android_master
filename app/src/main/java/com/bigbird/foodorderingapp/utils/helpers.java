package com.bigbird.foodorderingapp.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.airbnb.lottie.LottieDrawable;
import com.amrdeveloper.lottiedialog.LottieDialog;
import com.bigbird.foodorderingapp.R;
import com.bigbird.foodorderingapp.activities.ChooseUserLoginTypeActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.function.Function;

public class helpers {
    static String TAG = "mytaaag";

    public static void print(String message) {

        Log.d(TAG, "\n***********" + message + "***********\n");
    }

    static ProgressDialog pd;

    public static void showLoader(Context context) {

        pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setMessage("Loading...");
        pd.create();
        pd.show();

    }

    public static void hideLoader() {

        if (pd.isShowing()) {
            pd.dismiss();
        }
    }


    public static void showDialog(Context context, String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(message);
        alertDialog.create();
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }

    public static void gotoLogin(Context context, String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(message);
        alertDialog.create();
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
                Intent intent = new Intent(context, ChooseUserLoginTypeActivity.class);
                context.startActivity(intent);
                ((Activity) context).finishAffinity();
            }
        });
        alertDialog.show();
    }

    public static void showSnackBar(View activity, String message) {
        Snackbar.make(activity, message, Snackbar.LENGTH_LONG).show();
    }

    public static void waitAndFinish(Context ctx) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                ((Activity) ctx).finish();
            }
        }, 1000);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
