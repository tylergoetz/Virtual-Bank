package com.example.tgoetz.database_test;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class transactionActivity extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void onStart(){
        super.onStart();
        setContentView(R.layout.activity_transaction);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitBill();
            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.type);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.transaction_choices, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    protected int submitBill() {
        double amt = 0;
        String desc = "";
        String type = "Withdrawal";
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
        try {
            Spinner spin  = findViewById(R.id.type);
            type = spin.getSelectedItem().toString();
        }
        catch(Exception e){
            System.out.println(e);
        }
        System.out.println("Submitted Transaction, " + amt + " desc " + desc + "date " + date);
        Transaction newBill = new Transaction(amt, date, desc, type);
        //System.out.println("newTransaction Date: " + newBill.getDate());
        new submit().execute(newBill);
        return 0;
    }
    public class submit extends AsyncTask<Transaction, Void, Void> {
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
    public void enterMainView(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
