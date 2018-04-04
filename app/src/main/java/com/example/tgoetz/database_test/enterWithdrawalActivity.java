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
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Calendar;
import java.text.NumberFormat;
import java.util.Date;

import java.util.List;

public class enterWithdrawalActivity extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void onStart(){
        super.onStart();
        setContentView(R.layout.activity_enter_withdrawal);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitTransaction();
            }
        });
    }
    private class submit extends AsyncTask<Transaction, Void, Void> {
        protected Void doInBackground(Transaction... T) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                //stuff that updates ui
                View pgView = findViewById(R.id.progressbar);
                pgView.setVisibility(View.VISIBLE);
                }
            });
            db.transactionDao().insertAll(T);
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
    protected int submitTransaction() {
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
            TextView amtView = findViewById(R.id.amt);
            amt = Double.parseDouble(amtView.getText().toString());
        }
        catch(NumberFormatException e){
            System.out.println(e);
        }
        try {
            TextView descView = findViewById(R.id.desc);
            desc = descView.getText().toString();
        }
        catch(Exception e){
            System.out.println(e);
        }
        String type = "";
        System.out.println("amt from resource, " + amt + " desc " + desc + "date " + date);
        Transaction newTransaction = new Transaction(amt, date, desc, type);
        System.out.println("newTransaction Date: " + newTransaction.getDate());
        new submit().execute(newTransaction);
        return 0;
    }
    public void enterMainView(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
