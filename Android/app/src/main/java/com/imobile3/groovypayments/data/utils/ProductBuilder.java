package com.imobile3.groovypayments.data.utils;

import com.imobile3.groovypayments.data.entities.ProductEntity;
import com.imobile3.groovypayments.data.enums.GroovyColor;
import com.imobile3.groovypayments.data.enums.GroovyIcon;

import androidx.annotation.NonNull;

public final class ProductBuilder {

    private ProductBuilder() {
    }

    @NonNull
    public static ProductEntity build(
            long id,
            String name,
            String note,
            long unitPrice,
            long cost,
            @NonNull GroovyIcon icon,
            @NonNull GroovyColor color) {
        ProductEntity result = new ProductEntity();
        result.setId(id);
        result.setName(name);
        result.setNote(note);
        result.setUnitPrice(unitPrice);
        result.setCost(cost);
        result.setIconId(icon.id);
        result.setColorId(color.id);
        return result;
    }
}
