package com.imobile3.groovypayments.data.entities;

import com.imobile3.groovypayments.data.BigDecimalTypeConverter;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.math.BigDecimal;

@Entity(tableName = "tax")
@TypeConverters(BigDecimalTypeConverter.class)
public class TaxEntity {

    @PrimaryKey
    @ColumnInfo(name = "tax_id")
    private long mId;

    @ColumnInfo(name = "name")
    private String mName;

    /**
     * Tax rate percentage in decimal format. Example: {@code 0.075 = 7.5%}
     */
    @ColumnInfo(name = "rate")
    private BigDecimal mRate;

    public TaxEntity() {
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public BigDecimal getRate() {
        return mRate;
    }

    public void setRate(BigDecimal rate) {
        mRate = rate;
    }
}
