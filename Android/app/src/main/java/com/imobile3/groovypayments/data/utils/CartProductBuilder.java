package com.imobile3.groovypayments.data.utils;

import com.imobile3.groovypayments.data.entities.CartProductEntity;
import com.imobile3.groovypayments.data.model.Cart;
import com.imobile3.groovypayments.data.model.Product;

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

    @NonNull
    public static CartProductEntity from(@NonNull Cart cart, @NonNull Product product) {
        CartProductEntity result = new CartProductEntity();
        result.setId(product.getId());
        result.setCartId(cart.getId());
        result.setName(product.getName());
        result.setUnitPrice(product.getUnitPrice());
        result.setQuantity(1);
        return result;
    }
}
