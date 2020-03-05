package com.imobile3.groovypayments.data.utils;

import com.imobile3.groovypayments.data.entities.ProductEntity;

import androidx.annotation.NonNull;

public final class ProductBuilder {

    private ProductBuilder() {
    }

    @NonNull
    public static ProductEntity build(long id, String name, long unitPrice, long cost) {
        ProductEntity result = new ProductEntity();
        result.setId(id);
        result.setName(name);
        result.setUnitPrice(unitPrice);
        result.setCost(cost);
        return result;
    }
}
