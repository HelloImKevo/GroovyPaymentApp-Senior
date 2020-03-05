package com.imobile3.groovypayments.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "product")
public class ProductEntity {

    @PrimaryKey
    @ColumnInfo(name = "product_id")
    private long mId;

    @ColumnInfo(name = "name")
    private String mName;

    /**
     * What is the price-per-unit (in pennies) that we charge the customer?
     */
    @ColumnInfo(name = "unit_price")
    private long mUnitPrice;

    /**
     * How much does this product cost (in pennies) the merchant?
     */
    @ColumnInfo(name = "cost")
    private long mCost;

    public ProductEntity() {
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

    public long getUnitPrice() {
        return mUnitPrice;
    }

    public void setUnitPrice(long unitPrice) {
        mUnitPrice = unitPrice;
    }

    public long getCost() {
        return mCost;
    }

    public void setCost(long cost) {
        mCost = cost;
    }
}
