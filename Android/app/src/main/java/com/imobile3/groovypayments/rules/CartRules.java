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

package com.imobile3.groovypayments.rules;

import com.imobile3.groovypayments.data.entities.CartProductEntity;
import com.imobile3.groovypayments.data.entities.CartTaxEntity;
import com.imobile3.groovypayments.data.entities.TaxEntity;
import com.imobile3.groovypayments.data.model.Cart;
import com.imobile3.groovypayments.data.model.Product;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

/**
 * @author Kevin Schanz
 */
public final class CartRules {

    @NonNull
    private Cart mCart;

    public CartRules(@NonNull Cart cart) {
        mCart = Objects.requireNonNull(cart);
    }

    public boolean hasTax(TaxEntity tax) {
        if (mCart.getTaxes() != null) {
            for (CartTaxEntity cartTax : mCart.getTaxes()) {
                if (cartTax.getId() == tax.getId()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Nullable
    public CartProductEntity findProduct(Product product) {
        if (mCart.getProducts() != null) {
            for (CartProductEntity cartProduct : mCart.getProducts()) {
                if (cartProduct.getId() == product.getId()) {
                    return cartProduct;
                }
            }
        }
        return null;
    }

    public void updateProduct(CartProductEntity updated) {
        if (mCart.getProducts() != null) {
            for (CartProductEntity cartProduct : mCart.getProducts()) {
                if (cartProduct.getId() == updated.getId()) {
                    cartProduct.setQuantity(updated.getQuantity());
                }
            }
        }
    }

    public String getFormattedDate(@NonNull Locale locale) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy @ h:mm a", locale);
        return dateFormat.format(mCart.getDateCreated());
    }

    public String getOrderHistoryDescription() {
        StringBuilder builder = new StringBuilder();
        if (mCart.getProducts() != null) {
            for (CartProductEntity product : mCart.getProducts()) {
                if (builder.length() != 0) {
                    builder.append(", ");
                }
                builder.append(product.getName());
            }
        }
        return builder.toString();
    }
}
