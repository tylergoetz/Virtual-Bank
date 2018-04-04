package com.example.tgoetz.database_test;

import android.annotation.TargetApi;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Created by TGoetz on 1/19/2018.
 */
@Entity(tableName = "Transaction")
public class Transaction {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "TransactionId")
    private int id;


    @ColumnInfo(name = "Amount")
    public double amt;

    @ColumnInfo(name = "Date")
    public Date date;

    @ColumnInfo(name = "Desc")
    public String desc;

    @ColumnInfo(name = "Type")
    public String type;
    @Ignore
    public Transaction(double amt, Date date, String desc, String type){
        this.amt = amt ;
        this.date = date;
        this.desc = desc;
        this.type = type;
    }

    public Transaction(){this.amt = 0; this.desc = "none provided"; this.type = "W";}

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

    public  String getType( ){
        return type;
    }
    public void setType(String type ){this.type = type;}


    //helpers override to  string to print all info we need
    @TargetApi(19)
    public String toString(){
        return "TransactionId: " + getId() + ", Amount: " + getAmt() + ", Date: " + getDate() + ", Desc: " + getDesc() +  ", Type: " + getType() + System.lineSeparator();
    }
}
