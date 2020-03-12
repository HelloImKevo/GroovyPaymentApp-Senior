/*
 *  Copyright (c) 2020 iMobile3, LLC. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, is permitted provided that adherence to the following
 *  conditions is maintained. If you do not agree with these terms,
 *  please do not use, install, modify or redistribute this software.
 *
 *  1. Redistributions of source code must retain the above copyright notice, this
 *  list of conditions and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 */

package com.imobile3.groovypayments.rules;

import com.imobile3.groovypayments.data.model.Cart;

import androidx.annotation.NonNull;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

/**
 * @author Kevin Schanz
 */
public final class CurrencyRules {

    public CurrencyRules() {
    }

    public String getCartTotal(@NonNull Cart cart, @NonNull Locale locale) {
        String symbol = Currency.getInstance(locale).getSymbol();
        BigDecimal total = new BigDecimal(cart.getGrandTotal()).movePointLeft(2);
        return symbol + total;
    }
}
