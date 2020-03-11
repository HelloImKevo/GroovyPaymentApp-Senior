package com.imobile3.groovypayments.rules;

import com.imobile3.groovypayments.data.model.Cart;

import androidx.annotation.NonNull;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

public final class CurrencyRules {

    public CurrencyRules() {
    }

    public String getCartTotal(@NonNull Cart cart, @NonNull Locale locale) {
        String symbol = Currency.getInstance(locale).getSymbol();
        BigDecimal total = new BigDecimal(cart.getGrandTotal()).movePointLeft(2);
        return symbol + total;
    }
}
