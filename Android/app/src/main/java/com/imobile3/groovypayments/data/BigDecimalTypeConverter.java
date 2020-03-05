package com.imobile3.groovypayments.data;

import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.room.TypeConverter;

import java.math.BigDecimal;

public class BigDecimalTypeConverter {

    @Nullable
    @TypeConverter
    public static BigDecimal toBigDecimal(String value) {
        if (!TextUtils.isEmpty(value)) {
            return new BigDecimal(value);
        } else {
            return null;
        }
    }

    @Nullable
    @TypeConverter
    public static String toString(BigDecimal value) {
        if (value != null) {
            return value.toString();
        } else {
            return null;
        }
    }
}
