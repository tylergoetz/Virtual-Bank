package com.example.tgoetz.database_test;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by TGoetz on 2/20/2018.
 */

public class DateTypeConverter {
    @TypeConverter
    public static Date toDate(Long value) {
        System.out.println(value);
        return value == null ? null : new Date(value);
    }
    @TypeConverter
    public static Long toLong(Date value) {
        System.out.println("Converted datetime to value" + value.getTime());
        return value == null ? null : value.getTime();
    }
}
