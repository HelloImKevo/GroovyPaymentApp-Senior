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
     * Optional note about this product (a short description).
     */
    @ColumnInfo(name = "note")
    private String mNote;

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

    /**
     * See: GroovyIcon enum.
     */
    @ColumnInfo(name = "icon_id")
    private int mIconId;

    /**
     * See: GroovyColor enum.
     */
    @ColumnInfo(name = "color_id")
    private int mColorId;

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

    public String getNote() {
        return mNote;
    }

    public void setNote(String note) {
        mNote = note;
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

    public int getIconId() {
        return mIconId;
    }

    public void setIconId(int iconId) {
        mIconId = iconId;
    }

    public int getColorId() {
        return mColorId;
    }

    public void setColorId(int colorId) {
        mColorId = colorId;
    }
}
