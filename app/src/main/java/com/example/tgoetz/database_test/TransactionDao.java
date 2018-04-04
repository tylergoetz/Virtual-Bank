

/**
 * Created by TGoetz on 1/19/2018.
 */

package com.example.tgoetz.database_test;

        import android.arch.lifecycle.LiveData;
        import android.arch.persistence.room.Dao;
        import android.arch.persistence.room.Delete;
        import android.arch.persistence.room.OnConflictStrategy;
        import android.arch.persistence.room.Query;
        import android.arch.persistence.room.Insert;
        import java.util.List;
        import java.util.List;

/**
 * Created by TGoetz on 1/19/2018.
 */


@Dao
public interface TransactionDao {
    @Query("SELECT * FROM `Transaction`")
    List<Transaction> getAll();

    @Insert
    void insertAll(Transaction... trans);

    @Insert
    void insertAll(Transaction trans);

    @Delete
    void delete(Transaction trans);

    @Query("delete from `Transaction`")
    void nukeTable();

}

