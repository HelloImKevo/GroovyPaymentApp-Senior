package com.imobile3.groovypayments.rules;

import android.content.Context;

import androidx.annotation.NonNull;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

public final class CurrencyRules {

    public CurrencyRules() {
    }

    public String getCartTotal(
            @NonNull Context context,
            @NonNull Locale locale) {
        String symbol = Currency.getInstance(locale).getSymbol();
        BigDecimal total = BigDecimal.ZERO.movePointLeft(2);
        return symbol + total;
    }
}
