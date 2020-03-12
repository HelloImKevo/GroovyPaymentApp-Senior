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

package com.imobile3.groovypayments.calculation;

import com.imobile3.groovypayments.data.entities.CartPaymentEntity;
import com.imobile3.groovypayments.data.entities.CartProductEntity;
import com.imobile3.groovypayments.data.entities.CartTaxEntity;
import com.imobile3.groovypayments.data.model.Cart;
import com.imobile3.groovypayments.logging.LogHelper;

import androidx.annotation.NonNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

/**
 * @author Kevin Schanz
 */
public class CartCalculator {

    private final String TAG = CartCalculator.class.getSimpleName();

    @NonNull
    private Cart mCart;

    public CartCalculator(@NonNull Cart cart) {
        mCart = Objects.requireNonNull(cart);
    }

    public void calculate() {
        long sumProductTotal = getSumProductTotal();
        LogHelper.write(Level.FINE, TAG, "Sum(ProductTotal) = " + sumProductTotal);

        long sumTaxTotal = getSumTaxTotal(sumProductTotal);
        LogHelper.write(Level.FINE, TAG, "Sum(TaxTotal) = " + sumTaxTotal);

        long totalPaid = getTotalPaid();
        LogHelper.write(Level.FINE, TAG, "TotalPaid = " + totalPaid);

        mCart.setSubtotal(sumProductTotal);
        mCart.setTaxTotal(sumTaxTotal);

        long grandTotal = sumProductTotal + sumTaxTotal;
        LogHelper.write(Level.FINE, TAG, "GrandTotal = " + grandTotal);

        mCart.setGrandTotal(grandTotal);
        mCart.setTotalPaid(totalPaid);
    }

    private long getSumProductTotal() {
        long sumProductTotal = 0L;

        List<CartProductEntity> products = mCart.getProducts();
        if (products != null) {
            for (CartProductEntity product : products) {
                // Price of a product is UnitPrice * Quantity
                long productTotal = product.getUnitPrice() * product.getQuantity();
                sumProductTotal += productTotal;
            }
        }

        return sumProductTotal;
    }

    // Note: For this current implementation, we are applying all taxes to all products
    // within the Cart. In a real-world implementation, taxes would only be applied
    // to the products which they are associate with. An example might be a Food Tax
    // that is only applied to Food items, or a Liquor Tax that is only applied to Liquor.
    private long getSumTaxTotal(long productTotal) {
        long sumTaxTotal = 0L;

        List<CartTaxEntity> taxes = mCart.getTaxes();
        if (taxes != null) {
            for (CartTaxEntity tax : taxes) {
                BigDecimal rate = tax.getRate();
                // This gives us how many tax pennies with decimal precision
                // Example: 8.94 is 8 pennies and 94 hundredths of a penny.
                BigDecimal taxPennies = new BigDecimal(productTotal).multiply(rate);
                // Round down or up to the nearest penny.
                taxPennies = taxPennies.setScale(0, BigDecimal.ROUND_HALF_UP);
                sumTaxTotal += taxPennies.longValue();
            }
        }

        return sumTaxTotal;
    }

    private long getTotalPaid() {
        long totalPaid = 0L;

        List<CartPaymentEntity> payments = mCart.getPayments();
        if (payments != null) {
            for (CartPaymentEntity payment : payments) {
                totalPaid += payment.getApprovedAmount();
            }
        }

        return totalPaid;
    }
}
