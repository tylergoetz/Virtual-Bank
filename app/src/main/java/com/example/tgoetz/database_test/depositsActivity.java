package com.example.tgoetz.database_test;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class depositsActivity extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void onStart(){
        super.onStart();
        setContentView(R.layout.activity_deposits);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitDeposit();
            }
        });

    }
    public class submit extends AsyncTask<Deposit, Void, Void> {
        protected Void doInBackground(Deposit... T) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //stuff that updates ui
                    View pgView = findViewById(R.id.progressbar);
                    pgView.setVisibility(View.VISIBLE);
                }
            });
            db.DepositDao().insertAll(T);              //CREATE DEPOSIT TABLE AND DAO
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
                    View pgView = findViewById(R.id.progressbar);
                    AlphaAnimation fadeOutAnimation = new AlphaAnimation(1.0f, 0.0f);//fade from 1 to 0 alpha
                    fadeOutAnimation.setDuration(1000);
                    fadeOutAnimation.setFillAfter(true);//to keep it at 0 when animation ends
                    pgView.startAnimation(fadeOutAnimation);
                }
            });
        }
    }
    protected int submitDeposit() {
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
            TextView amtView = findViewById(R.id.depAmt);
            amt = Double.parseDouble(amtView.getText().toString());
        }
        catch(NumberFormatException e){
            System.out.println(e);
        }
        try {
            TextView descView = findViewById(R.id.depDesc);
            desc = descView.getText().toString();
        }
        catch(Exception e){
            System.out.println(e);
        }
        System.out.println("amt from resource, " + amt + " desc " + desc + "date " + date);
        Deposit newDeposit = new Deposit(amt, date, desc);
        System.out.println("newTransaction Date: " + newDeposit.getDate());
        new submit().execute(newDeposit);
        return 0;
    }
    public void enterMainView(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
