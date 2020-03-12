/*
 *  Copyright (c) 2020 iMobile3, LLC. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, is permitted provided that adherence to the following
 *  conditions is maintained. If you do not agree with these terms,
 *  please do not use, install, modify or redistribute this software.
 *
 *  1. Redistributions of source code must retain the above copyright notice, this
 *  list of conditions and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 */

package com.imobile3.groovypayments.data.model;

import com.imobile3.groovypayments.data.entities.CartEntity;
import com.imobile3.groovypayments.data.entities.CartPaymentEntity;
import com.imobile3.groovypayments.data.entities.CartProductEntity;
import com.imobile3.groovypayments.data.entities.CartTaxEntity;

import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

/**
 * @author Kevin Schanz
 */
public class Cart extends CartEntity {

    /**
     * List of the payments associated with this cart.
     */
    @Relation(
            parentColumn = "cart_id",
            entityColumn = "cart_payment_id",
            associateBy = @Junction(CartPaymentEntity.class))
    private List<CartPaymentEntity> mPayments;

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

    public List<CartPaymentEntity> getPayments() {
        return mPayments;
    }

    public void setPayments(List<CartPaymentEntity> payments) {
        mPayments = payments;
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
