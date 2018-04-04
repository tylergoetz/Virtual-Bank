package com.example.tgoetz.database_test;

import android.annotation.TargetApi;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Created by TGoetz on 3/7/2018.
 */
@Entity(tableName = "Paychecks")
public class Paycheck {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int id;


    @ColumnInfo(name = "Amount")
    public double amt;

    @ColumnInfo(name = "Date")
    public Date date;

    @ColumnInfo(name = "Desc")
    public String desc;
    @Ignore
    public Paycheck(double amt, Date date, String desc){
        this.amt = amt ;
        this.date = date;
        this.desc = desc;
    }

    public Paycheck(){this.amt = 0; this.desc = "none provided";}

    //g&s below self explanatory...
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public double getAmt (){
        return amt;
    }
    public void setAmt(int amt ){
        this.amt = amt;
    }

    public Date getDate ( ){
        return date;
    }
    public void setDate(Date date ){
        this.date = date;
    }

    public  String getDesc( ){
        return desc;
    }
    public void setDesc(String desc ){
        this.desc = desc;
    }


    //helpers override to  string to print all info we need
    @TargetApi(19)
    public String toString(){
        return "TransactionId: " + getId() + ", Amount: " + getAmt() + ", Date: " + getDate() + ", Desc: " + getDesc() + System.lineSeparator();
    }
}

