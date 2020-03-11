package com.imobile3.groovypayments.rules;

import com.imobile3.groovypayments.data.entities.CartProductEntity;
import com.imobile3.groovypayments.data.entities.CartTaxEntity;
import com.imobile3.groovypayments.data.entities.TaxEntity;
import com.imobile3.groovypayments.data.model.Cart;
import com.imobile3.groovypayments.data.model.Product;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

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
}
