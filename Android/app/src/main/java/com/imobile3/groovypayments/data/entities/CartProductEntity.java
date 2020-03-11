package com.imobile3.groovypayments.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart_product",
        indices = {
                @Index("cart_product_id"),
                @Index("cart_id")})
public class CartProductEntity {

    @PrimaryKey
    @ColumnInfo(name = "cart_product_id")
    private long mId;

    /**
     * Foreign key to the cart table.
     */
    @ForeignKey(
            entity = CartEntity.class,
            parentColumns = "cart_id",
            childColumns = "cart_id")
    @ColumnInfo(name = "cart_id")
    private long mCartId;

    @ColumnInfo(name = "name")
    private String mName;

    /**
     * What is the price-per-unit (in pennies) that we charge the customer?
     */
    @ColumnInfo(name = "unit_price")
    private long mUnitPrice;

    /**
     * How many units of this product is the customer buying?
     */
    @ColumnInfo(name = "quantity")
    private int mQuantity;

    public CartProductEntity() {
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public long getCartId() {
        return mCartId;
    }

    public void setCartId(long cartId) {
        mCartId = cartId;
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

    public int getQuantity() {
        return mQuantity;
    }

    public void setQuantity(int quantity) {
        mQuantity = quantity;
    }
}
