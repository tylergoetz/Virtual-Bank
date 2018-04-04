package com.example.tgoetz.database_test;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by TGoetz on 1/10/2018.
 */
@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userid")
    private int uid;

    @ColumnInfo(name = "first_name")
    private String firstName;

    @ColumnInfo(name = "last_name")
    private String lastName;


    public User(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }
    @Ignore
    public User(){}
    // Getters and setters are ignored for brevity,
    // but they're required for Room to work

    public int getUid(){
        return uid;
    }
    public void setUid(int uid){
        this.uid = uid;
    }

    public String getLastName(){
        return lastName;
    }
    public String setLastName(String lastName){
        this.lastName = lastName;
        return lastName;
    }
    public String getFirstName(){
        return firstName;
    }
    public String setfirstName(String firstname){
        return firstName = firstname;
    }

    //helpers
    public String toString(){
        return "First Name: " + getFirstName() + ", Last Name: " + getLastName() + System.getProperty("line.separator");
    }
}
