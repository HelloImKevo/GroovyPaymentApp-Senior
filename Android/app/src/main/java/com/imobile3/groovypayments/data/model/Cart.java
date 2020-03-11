package com.imobile3.groovypayments.data.model;

import com.imobile3.groovypayments.data.entities.CartEntity;
import com.imobile3.groovypayments.data.entities.CartProductEntity;
import com.imobile3.groovypayments.data.entities.CartTaxEntity;

import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class Cart extends CartEntity {

    /**
     * List of the products associated with this cart.
     */
    @Relation(
            parentColumn = "cart_id",
            entityColumn = "cart_product_id",
            associateBy = @Junction(CartProductEntity.class))
    private List<CartProductEntity> mProducts;

    /**
     * List of the taxes associated with this cart.
     */
    @Relation(
            parentColumn = "cart_id",
            entityColumn = "cart_tax_id",
            associateBy = @Junction(CartTaxEntity.class))
    private List<CartTaxEntity> mTaxes;

    public Cart() {
    }

    public Cart(CartEntity other) {
        super(other);
    }

    public List<CartProductEntity> getProducts() {
        return mProducts;
    }

    public void setProducts(List<CartProductEntity> products) {
        mProducts = products;
    }

    public List<CartTaxEntity> getTaxes() {
        return mTaxes;
    }

    public void setTaxes(List<CartTaxEntity> taxes) {
        mTaxes = taxes;
    }

    public boolean hasProducts() {
        return mProducts != null && !mProducts.isEmpty();
    }
}
