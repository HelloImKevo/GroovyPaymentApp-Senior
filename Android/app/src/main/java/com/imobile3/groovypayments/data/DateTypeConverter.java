package com.imobile3.groovypayments.data;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateTypeConverter {

    /**
     * Converts a long millis value to a date.
     */
    @TypeConverter
    public static Date toDate(long value) {
        return new Date(value);
    }

    /**
     * Converts a date object to the long millis value.
     */
    @TypeConverter
    public static long toLong(Date value) {
        if (value != null) {
            return value.getTime();
        } else {
            return 0L;
        }
    }
}
