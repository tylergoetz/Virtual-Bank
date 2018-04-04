package com.example.tgoetz.database_test;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by TGoetz on 1/19/2018.
 */
@Entity
public class TransactionQueue {
    @PrimaryKey
    @ColumnInfo(name = "Transaction Id" )
    public int id;


}
