package com.example.tgoetz.database_test;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import com.example.tgoetz.database_test.Transaction;
import com.example.tgoetz.database_test.Bill;
import com.example.tgoetz.database_test.DateTypeConverter;

/**
 * Created by TGoetz on 1/10/2018.
 */

@Database(entities = {User.class, Transaction.class, Bill.class, Deposit.class, Paycheck.class}, version = 12, exportSchema = false)
@TypeConverters({DateTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;
    public abstract TransactionDao transactionDao();
    public abstract UserDao userDao();
    public abstract BillDao billDao();
    public abstract DepositDao DepositDao();
    public abstract PaycheckDao PaycheckDao();
    public static void destroyInstance() {
        INSTANCE = null;
    }
}

