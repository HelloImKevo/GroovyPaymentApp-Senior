package com.imobile3.groovypayments.rules;

import android.text.TextUtils;

import com.imobile3.groovypayments.data.enums.GroovyColor;
import com.imobile3.groovypayments.data.enums.GroovyIcon;
import com.imobile3.groovypayments.data.model.Product;

import androidx.annotation.NonNull;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;
import java.util.Objects;

public final class ProductRules {

    @NonNull
    private Product mProduct;

    public ProductRules(@NonNull Product product) {
        mProduct = Objects.requireNonNull(product);
    }

    @NonNull
    public GroovyIcon getIcon() {
        return GroovyIcon.fromId(mProduct.getIconId());
    }

    @NonNull
    public GroovyColor getColor() {
        return GroovyColor.fromId(mProduct.getColorId());
    }

    @NonNull
    public String getUnitPriceDisplay(@NonNull Locale locale) {
        String symbol = Currency.getInstance(locale).getSymbol();
        BigDecimal unitPrice = new BigDecimal(mProduct.getUnitPrice()).movePointLeft(2);
        return symbol + unitPrice;
    }

    @NonNull
    public String getDescription(@NonNull Locale locale) {
        String unitPrice = getUnitPriceDisplay(locale);
        String note = mProduct.getNote();

        if (TextUtils.isEmpty(note)) {
            return unitPrice;
        }
        return unitPrice + " | " + note;
    }
}
