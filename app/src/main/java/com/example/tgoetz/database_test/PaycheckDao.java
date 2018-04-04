package com.example.tgoetz.database_test;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by TGoetz on 3/7/2018.
 */

@Dao
public interface PaycheckDao {
    @Query("SELECT * FROM `Paychecks`")
    List<Deposit> getAll();

    @Insert
    void insertAll(Paycheck... trans);

    @Insert
    void insertAll(Paycheck trans);

    @Delete
    void delete(Paycheck trans);

    @Query("delete from `Paychecks`")
    void nukeTable();

}
