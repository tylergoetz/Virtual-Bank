package com.example.tgoetz.database_test;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;


import java.io.Console;
import java.lang.reflect.Modifier;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;

import static java.lang.System.in;
import java.text.DecimalFormat;




public class MainActivity extends AppCompatActivity {
    public AppDatabase db;
    public double currentBank = 0;
    public double oldBank = currentBank;
    private static DecimalFormat df2 = new DecimalFormat(".##");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //testing Room and User interaction across database

        db = Room.databaseBuilder(this, AppDatabase.class, "Test.db").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        //create test users
        User ty = new User("Tyler", "Goetz");
        User tim = new User("Tim", "Jones");
        //new AddUsers().execute(ty, tim);
        String data = "test";
        //new nukeTransData().execute();
        //new nukeBillData().execute();
        System.out.println(data);
    }
    @Override
    protected  void onStart(){

        super.onStart();
        setContentView(R.layout.activity_main);
        Transaction[] e = db.transactionDao().getAll().toArray(new Transaction[db.transactionDao().getAll().size()]);
        Bill[] b = db.billDao().getAll().toArray(new Bill[db.billDao().getAll().size()]);
        Deposit[] d = db.DepositDao().getAll().toArray(new Deposit[db.DepositDao().getAll().size()]);
        Paycheck[] p = db.PaycheckDao().getAll().toArray(new Paycheck[db.PaycheckDao().getAll().size()]);
        TextView amtBank = (TextView) findViewById(R.id.currentBankAmt);

        //organize transaction by latest first (reverse order) for user
        for(int i = 0; i < e.length /2; i++){
            Transaction temp = e[i];
            e[i] = e[e.length - i -1];
            e[e.length - i -1] = temp;
        }

        //push withdrawals/bills table to screen via adapter
        ArrayAdapter<Transaction> adapter = new ArrayAdapter(MainActivity.this, R.layout.listview, e);
        ArrayAdapter<Bill> badapter = new ArrayAdapter(MainActivity.this, R.layout.listview, b);
        ListView tb = ((ListView)findViewById(R.id.tb));
        ListView billView = ((ListView)findViewById(R.id.billView));
        System.out.println("adapter:  " + adapter);
        tb.setAdapter(adapter);
        billView.setAdapter(badapter);

        //calculate total
        currentBank = calculateCurrentBank(e, b, amtBank); //calculate current amount in users bank and change the appropriate textview to reflect it on gui
        System.out.println("old bank: " + oldBank + " new bank: " + currentBank);
        try {
            startCountAnimation(currentBank, oldBank);
        }
        catch(Exception x){
            amtBank.setText("$ " + String.valueOf(df2.format(currentBank)));
            System.out.println(x);
        }


    }
    @Override
    protected void onRestart(){
        super.onRestart();
        setContentView(R.layout.activity_main);
        TextView amtBank = (TextView) findViewById(R.id.currentBankAmt);
        Transaction[] e = db.transactionDao().getAll().toArray(new Transaction[db.transactionDao().getAll().size()]);
        Bill[] b = db.billDao().getAll().toArray(new Bill[db.billDao().getAll().size()]);

        currentBank = calculateCurrentBank(e,b,amtBank);
        try {
            startCountAnimation(currentBank, oldBank);
        }
        catch(Exception x){

            amtBank.setText(String.valueOf(df2.format(currentBank)));
            System.out.println(x);
        }
    }
    //parappa the wrapper
    //need these to make background tasks and avoid main thread lock-down
    public class loadUserData extends AsyncTask<Void, Void, List<User>> {
        protected List<User> doInBackground(Void... params) {
            return db.userDao().getAll();
        }
        protected void onPostExecute(List<User> users) {
            System.out.println("Load data post execute:");
        }
    }
    public class AddUsers extends AsyncTask<User, Void, Void> {
        protected Void doInBackground(User... users) {
            System.out.println("AddUsers: " + users[0].toString());
            for (User user : users) {
                System.out.println("User: " + user.toString());
                db.userDao().insertAll(user);
            }
            return null;
        }
    }

    public class nukeUserData extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... params) {
            db.userDao().nukeTable();
            return null;
        }
    }
    public class nukeTransData extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... params) {
            db.transactionDao().nukeTable();
            return null;
        }
    }
    public class nukeBillData extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... params) {
            db.billDao().nukeTable();
            return null;
        }
    }

    //VIEWS
    public void enterWithdrawalView(View view){
        Intent intent = new Intent(this, enterWithdrawalActivity.class);
        startActivity(intent);
    }
    public void enterBillView(View view){
        Intent intent = new Intent(this, rebillsActivity.class);
        startActivity(intent);
    }
    public void enterDepositView(View view){
        Intent intent = new Intent(this, depositsActivity.class);
        startActivity(intent);
    }
    public void enterTransactionView(View view){
        //finish();
        Intent intent = new Intent(this, transactionActivity.class);
        startActivity(intent);

    }
    //BANK CAL
    public class curBank{
        public final double amt = 0;
    }

    private double calculateCurrentBank(Transaction[] t, Bill[] b, TextView amtBank){
        double curBank = 0;
        for(Transaction f : t){

            System.out.println(f.type);
            if(f.type.equals("Withdrawal")) {
                System.out.println(f.getAmt() + " being subtracted from " + curBank);
                curBank -= f.getAmt();
                //amtBank.setText(String.valueOf(currentBank));
            }
            else if(f.type.equals("Deposit")){
                System.out.println(f.getAmt() + " being added to " + curBank);
                curBank += f.getAmt();
            }
            else{
                System.out.println("\nNot a valid type for calculating transactions debug:\n" + t);
            }
            System.out.println(curBank);
        }
        //get current date(down to day) to calculate amount of bills to subtract from curBank
        Date currentDate = Calendar.getInstance().getTime();
        for(Bill f: b){
            if(applyBill(f)){
                curBank -= f.getAmt();
            }
            else{
                System.out.println("Bill span  too far bill skipped");
            }
        }
        return curBank;
    }
    //BILL CALC
    private boolean applyBill(Bill b){
        Calendar currentDate = Calendar.getInstance();
        Calendar dueDate = toCalendar(b.getDate());
        int billDueSpan = 14;       //allow user to change this value
        boolean canApplyBill = false;
        System.out.println("date difference "  + getDaysDifference(currentDate, dueDate));
        if(Math.abs(getDaysDifference(dueDate, currentDate))<= billDueSpan){
            canApplyBill = true;
        }
        return canApplyBill;
    }

    public static Calendar toCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
    //return number of days between two calenders
    public static int getDaysDifference(Calendar calendar1,Calendar calendar2)
    {
        if(calendar1==null||calendar2==null)
            return 0;

        return (int)( (calendar2.getTimeInMillis() - calendar1.getTimeInMillis()) / (1000 * 60 * 60 * 24));
    }

    //animation
    private void startCountAnimation(double amt, double oldamt) {
        TextView amtBank = (TextView) findViewById(R.id.currentBankAmt);
        ValueAnimator animator = ValueAnimator.ofFloat ((float)oldamt, (float)amt);
        animator.setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                TextView amtBank = (TextView) findViewById(R.id.currentBankAmt);
                if (amtBank != null){
                    amtBank.setText("$ " + df2.format(animation.getAnimatedValue()).toString());

                }
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                oldBank = currentBank;
            }
        });
        animator.start();
    }
}


