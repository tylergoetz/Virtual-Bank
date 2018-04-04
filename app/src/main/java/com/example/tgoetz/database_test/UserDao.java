package com.example.tgoetz.database_test;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Insert;
import java.util.List;

/**
 * Created by TGoetz on 1/10/2018.
 */

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND "
            + "last_name LIKE :last LIMIT 1")
    User findByName(String first, String last);

    @Insert
    void insertAll(User... users);

    @Insert
    void insertAll(User user);

    @Delete
    void delete(User user);

    @Query("delete from user")
    void nukeTable();

    @Query("select name from sqlite_master where type ='table' order by name")
    String getall();



}
