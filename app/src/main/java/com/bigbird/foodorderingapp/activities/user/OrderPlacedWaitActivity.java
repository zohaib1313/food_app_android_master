package com.bigbird.foodorderingapp.activities.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.bigbird.foodorderingapp.R;

import java.util.concurrent.TimeUnit;

public class OrderPlacedWaitActivity extends AppCompatActivity {
    TextView tvRemainingTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_placed_wait);
        tvRemainingTime = findViewById(R.id.textView9);
        new CountDownTimer(30*60000, 1000) {

            public void onTick(long millisUntilFinished) {
                long secondsInMilli = 1000;
                long minutesInMilli = secondsInMilli * 60;
                long hoursInMilli = minutesInMilli * 60;

                long elapsedHours = millisUntilFinished / hoursInMilli;
                millisUntilFinished = millisUntilFinished % hoursInMilli;

                long elapsedMinutes = millisUntilFinished / minutesInMilli;
                millisUntilFinished = millisUntilFinished % minutesInMilli;

                long elapsedSeconds = millisUntilFinished / secondsInMilli;

                String yy = String.format("%02d Minutes: %02d Seconds", elapsedMinutes,elapsedSeconds);
                tvRemainingTime.setText(yy);
            }

            public void onFinish() {

                tvRemainingTime.setText("00:00");
            }
        }.start();

    }
}