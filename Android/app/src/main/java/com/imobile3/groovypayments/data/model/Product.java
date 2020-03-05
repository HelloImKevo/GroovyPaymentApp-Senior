package com.imobile3.groovypayments.data.model;

import com.imobile3.groovypayments.data.entities.ProductEntity;
import com.imobile3.groovypayments.data.entities.ProductTaxJunctionEntity;
import com.imobile3.groovypayments.data.entities.TaxEntity;

import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class Product extends ProductEntity {

    /**
     * List of the taxes associated with this product.
     */
    @Relation(
            parentColumn = "product_id",
            entityColumn = "tax_id",
            associateBy = @Junction(ProductTaxJunctionEntity.class))
    private List<TaxEntity> mTaxes;

    public Product() {
    }

    public List<TaxEntity> getTaxes() {
        return mTaxes;
    }

    public void setTaxes(List<TaxEntity> taxes) {
        mTaxes = taxes;
    }
}
