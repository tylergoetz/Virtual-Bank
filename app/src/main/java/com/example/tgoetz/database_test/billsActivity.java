package com.example.tgoetz.database_test;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Calendar;
import java.text.NumberFormat;
import java.util.Date;

import java.util.List;

public class billsActivity extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void onStart(){
        super.onStart();
        setContentView(R.layout.activity_bills);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitBill();
            }
        });
    }
    public class submit extends AsyncTask<Bill, Void, Void> {
        protected Void doInBackground(Bill... T) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //stuff that updates ui
                    View pgView = findViewById(R.id.progressbar);
                    pgView.setVisibility(View.VISIBLE);
                }
            });
            db.billDao().insertAll(T);
            return null;
        }
        protected void onProgressUpdate(Void... values) {
        }
        @Override
        protected void onPostExecute(Void result){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //stuff that updates ui
                    final View pgView = findViewById(R.id.progressbar);
                    AlphaAnimation fadeOutAnimation = new AlphaAnimation(1.0f, 0.0f);//fade from 1 to 0 alpha
                    fadeOutAnimation.setDuration(1000);
                    fadeOutAnimation.setFillAfter(true);//to keep it at 0 when animation ends
                    pgView.startAnimation(fadeOutAnimation);

                    fadeOutAnimation.setAnimationListener(new Animation.AnimationListener(){
                        public void onAnimationRepeat(Animation fade){
                            fade.cancel();
                            fade.setFillAfter(true);//to keep it at 0 when animation ends

                        }
                        public void onAnimationStart(Animation fade){

                        }
                        public void onAnimationEnd(Animation fade){
                            fade.cancel();
                            fade.setFillAfter(true);//to keep it at 0 when animation ends
                            System.out.println("animation ended");
                            pgView.clearAnimation();
                            pgView.setVisibility(View.INVISIBLE);
                        }
                    });

                }
            });
        }
    }
    protected int submitBill() {
        double amt = 0;
        String desc = "";
        DatePicker datePicker = findViewById(R.id.datePicker);
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();
        System.out.println(day + " " + month + " " + year);
        Calendar cal = Calendar.getInstance();
        cal.set(year,month,day);
        Date date = cal.getTime();
        try {
            TextView amtView = findViewById(R.id.billAmt);
            amt = Double.parseDouble(amtView.getText().toString());
        }
        catch(NumberFormatException e){
            System.out.println(e);
        }
        try {
            TextView descView = findViewById(R.id.billDesc);
            desc = descView.getText().toString();
        }
        catch(Exception e){
            System.out.println(e);
        }
        System.out.println("amt from resource, " + amt + " desc " + desc + "date " + date);
        Bill newBill = new Bill(amt, date, desc);
        System.out.println("newTransaction Date: " + newBill.getDate());
        new submit().execute(newBill);
        return 0;
    }
    public void enterMainView(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
