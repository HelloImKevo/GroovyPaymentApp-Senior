package com.imobile3.groovypayments.data.utils;

import com.imobile3.groovypayments.data.entities.CartTaxEntity;

import androidx.annotation.NonNull;

import java.math.BigDecimal;

public final class CartTaxBuilder {

    private CartTaxBuilder() {
    }

    @NonNull
    public static CartTaxEntity build(
            long id,
            long cartId,
            String name,
            String rate) {
        CartTaxEntity result = new CartTaxEntity();
        result.setId(id);
        result.setCartId(cartId);
        result.setName(name);
        result.setRate(new BigDecimal(rate));
        return result;
    }
}
