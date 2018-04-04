

/**
 * Created by TGoetz on 1/19/2018.
 */

package com.example.tgoetz.database_test;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by TGoetz on 1/19/2018.
 */


@Dao
public interface DepositDao {
    @Query("SELECT * FROM `Deposits`")
    List<Deposit> getAll();

    @Insert
    void insertAll(Deposit... trans);

    @Insert
    void insertAll(Deposit trans);

    @Delete
    void delete(Deposit trans);

    @Query("delete from `Deposits`")
    void nukeTable();

}

