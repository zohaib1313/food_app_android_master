package com.bigbird.foodorderingapp.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.bigbird.foodorderingapp.activities.ChooseUserLoginTypeActivity;
import com.google.android.material.snackbar.Snackbar;

public class helpers {
    static String TAG = "mytaaag";

    public static void print(String message) {

        Log.d(TAG, "\n***********" + message + "***********\n");
    }

    static ProgressDialog pd;

    public static void showLoader(Context context) {

        pd = new ProgressDialog(context);
        pd.setMessage("loading");
        pd.setCancelable(false);
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
                Intent intent = new Intent(context, ChooseUserLoginTypeActivity.class);
                context.startActivity(intent);
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }

  public   static void showSnackBar(View activity, String message) {
        Snackbar.make(activity, message, Snackbar.LENGTH_LONG).show();
    }

    public static void waitAndFinish(Context ctx) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                ((Activity) ctx).finish();
            }
        }, 2000);
    }
}
