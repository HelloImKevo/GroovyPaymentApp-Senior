package com.imobile3.groovypayments.data.entities;

import com.imobile3.groovypayments.data.BigDecimalTypeConverter;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.math.BigDecimal;

@Entity(tableName = "cart_tax",
        indices = {
                @Index("cart_tax_id"),
                @Index("cart_id")})
@TypeConverters(BigDecimalTypeConverter.class)
public class CartTaxEntity {

    @PrimaryKey
    @ColumnInfo(name = "cart_tax_id")
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
     * Tax rate percentage in decimal format. Example: {@code 0.075 = 7.5%}
     */
    @ColumnInfo(name = "rate")
    private BigDecimal mRate;

    public CartTaxEntity() {
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

    public BigDecimal getRate() {
        return mRate;
    }

    public void setRate(BigDecimal rate) {
        mRate = rate;
    }
}
