

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
public interface BillDao {
    @Query("SELECT * FROM `Bills`")
    List<Bill> getAll();

    @Insert
    void insertAll(Bill... trans);

    @Insert
    void insertAll(Bill trans);

    @Delete
    void delete(Bill trans);

    @Query("delete from `Bills`")
    void nukeTable();

}

