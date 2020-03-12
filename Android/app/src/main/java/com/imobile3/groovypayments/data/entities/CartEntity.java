package com.imobile3.groovypayments.data.entities;

import com.imobile3.groovypayments.data.DateTypeConverter;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "cart")
public class CartEntity {

    @PrimaryKey
    @ColumnInfo(name = "cart_id")
    private long mId;

    @ColumnInfo(name = "date_created")
    @TypeConverters(DateTypeConverter.class)
    private Date mDateCreated;

    /**
     * Cart subtotal (in pennies).
     */
    @ColumnInfo(name = "subtotal")
    private long mSubtotal;

    /**
     * Cart tax total (in pennies).
     */
    @ColumnInfo(name = "tax_total")
    private long mTaxTotal;

    /**
     * Cart grand total (in pennies).
     */
    @ColumnInfo(name = "grand_total")
    private long mGrandTotal;

    /**
     * Cart total paid (in pennies).
     */
    @ColumnInfo(name = "total_paid")
    private long mTotalPaid;

    public CartEntity() {
    }

    public CartEntity(CartEntity other) {
        mId = other.mId;
        mDateCreated = other.mDateCreated;
        mSubtotal = other.mSubtotal;
        mTaxTotal = other.mTaxTotal;
        mGrandTotal = other.mGrandTotal;
        mTotalPaid = other.mTotalPaid;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public Date getDateCreated() {
        return mDateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        mDateCreated = dateCreated;
    }

    public long getSubtotal() {
        return mSubtotal;
    }

    public void setSubtotal(long subtotal) {
        mSubtotal = subtotal;
    }

    public long getTaxTotal() {
        return mTaxTotal;
    }

    public void setTaxTotal(long taxTotal) {
        mTaxTotal = taxTotal;
    }

    public long getGrandTotal() {
        return mGrandTotal;
    }

    public void setGrandTotal(long grandTotal) {
        mGrandTotal = grandTotal;
    }

    public long getTotalPaid() {
        return mTotalPaid;
    }

    public void setTotalPaid(long totalPaid) {
        mTotalPaid = totalPaid;
    }
}
