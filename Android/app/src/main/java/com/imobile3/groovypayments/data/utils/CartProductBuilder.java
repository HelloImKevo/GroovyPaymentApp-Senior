package com.imobile3.groovypayments.data.utils;

import com.imobile3.groovypayments.data.entities.CartProductEntity;

import androidx.annotation.NonNull;

public final class CartProductBuilder {

    private CartProductBuilder() {
    }

    @NonNull
    public static CartProductEntity build(
            long id,
            long cartId,
            String name,
            long unitPrice,
            int quantity) {
        CartProductEntity result = new CartProductEntity();
        result.setId(id);
        result.setCartId(cartId);
        result.setName(name);
        result.setUnitPrice(unitPrice);
        result.setQuantity(quantity);
        return result;
    }
}
