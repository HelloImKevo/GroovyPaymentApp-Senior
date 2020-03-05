package com.imobile3.groovypayments.data.utils;

import com.imobile3.groovypayments.data.entities.TaxEntity;

import androidx.annotation.NonNull;

import java.math.BigDecimal;

public final class TaxBuilder {

    private TaxBuilder() {
    }

    @NonNull
    public static TaxEntity build(long id, String name, String rate) {
        TaxEntity result = new TaxEntity();
        result.setId(id);
        result.setName(name);
        result.setRate(new BigDecimal(rate));
        return result;
    }
}
