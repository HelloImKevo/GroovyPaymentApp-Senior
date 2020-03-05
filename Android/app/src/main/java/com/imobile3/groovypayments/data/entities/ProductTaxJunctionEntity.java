package com.imobile3.groovypayments.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

/**
 * Junction table for many-to-many relationship of Products and Taxes.
 * <p>
 * Additional reading:
 * https://developer.android.com/training/data-storage/room/relationships
 */
@Entity(tableName = "product_tax_junction",
        primaryKeys = {"product_id", "tax_id"},
        indices = {
                @Index("product_id"),
                @Index("tax_id")})
public class ProductTaxJunctionEntity {

    /**
     * Foreign key to the product table.
     */
    @ForeignKey(
            entity = ProductEntity.class,
            parentColumns = "product_id",
            childColumns = "product_id")
    @ColumnInfo(name = "product_id")
    private long mProductId;

    /**
     * Foreign key to the tax table.
     */
    @ForeignKey(
            entity = TaxEntity.class,
            parentColumns = "tax_id",
            childColumns = "tax_id")
    @ColumnInfo(name = "tax_id")
    private long mTaxId;

    public ProductTaxJunctionEntity() {
    }

    public long getProductId() {
        return mProductId;
    }

    public void setProductId(long productId) {
        mProductId = productId;
    }

    public long getTaxId() {
        return mTaxId;
    }

    public void setTaxId(long taxId) {
        mTaxId = taxId;
    }
}
