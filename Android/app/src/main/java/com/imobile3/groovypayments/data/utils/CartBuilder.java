package com.imobile3.groovypayments.data.utils;

import com.imobile3.groovypayments.data.entities.CartEntity;

import androidx.annotation.NonNull;

import java.util.Date;

public final class CartBuilder {

    private CartBuilder() {
    }

    @NonNull
    public static CartEntity build(long id, Date dateCreated) {
        CartEntity result = new CartEntity();
        result.setId(id);
        result.setDateCreated(dateCreated);
        result.setSubtotal(0L);
        result.setTaxTotal(0L);
        result.setGrandTotal(0L);
        return result;
    }

    @NonNull
    public static CartEntity build(
            long id,
            Date dateCreated,
            long subtotal,
            long taxTotal,
            long grandTotal) {
        CartEntity result = new CartEntity();
        result.setId(id);
        result.setDateCreated(dateCreated);
        result.setSubtotal(subtotal);
        result.setTaxTotal(taxTotal);
        result.setGrandTotal(grandTotal);
        return result;
    }
}
