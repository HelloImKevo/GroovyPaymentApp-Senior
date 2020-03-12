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

package com.imobile3.groovypayments.manager;

import android.os.Handler;
import android.os.Looper;

import com.imobile3.groovypayments.calculation.CartCalculator;
import com.imobile3.groovypayments.concurrent.GroovyExecutors;
import com.imobile3.groovypayments.data.DatabaseHelper;
import com.imobile3.groovypayments.data.entities.CartPaymentEntity;
import com.imobile3.groovypayments.data.entities.CartProductEntity;
import com.imobile3.groovypayments.data.entities.CartTaxEntity;
import com.imobile3.groovypayments.data.entities.TaxEntity;
import com.imobile3.groovypayments.data.enums.GroovyPaymentType;
import com.imobile3.groovypayments.data.model.Cart;
import com.imobile3.groovypayments.data.model.Product;
import com.imobile3.groovypayments.data.utils.CartPaymentBuilder;
import com.imobile3.groovypayments.data.utils.CartProductBuilder;
import com.imobile3.groovypayments.data.utils.CartTaxBuilder;
import com.imobile3.groovypayments.logging.LogHelper;
import com.imobile3.groovypayments.network.domainobjects.PaymentResponseDto;
import com.imobile3.groovypayments.rules.CartRules;
import com.imobile3.groovypayments.rules.CurrencyRules;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;

/**
 * @author Kevin Schanz
 */
public class CartManager {

    private static final String TAG = CartManager.class.getSimpleName();

    //region Singleton Implementation

    private static CartManager sInstance;

    private CartManager() {
    }

    @NonNull
    public static synchronized CartManager getInstance() {
        if (sInstance == null) {
            sInstance = new CartManager();
        }
        return sInstance;
    }

    //endregion

    @Nullable
    private Cart mCart;

    @NonNull
    public Cart getCart() {
        if (mCart == null) {
            LogHelper.writeWithTrace(Level.CONFIG, TAG, "Initializing new cart instance");
            mCart = initNewCart(System.currentTimeMillis());
        }
        return mCart;
    }

    private Cart initNewCart(long id) {
        Cart cart = new Cart();
        cart.setPayments(new ArrayList<>());
        cart.setProducts(new ArrayList<>());
        cart.setTaxes(new ArrayList<>());
        cart.setId(id);
        cart.setDateCreated(new Date());
        return cart;
    }

    public void addProduct(Product product) {
        // Initialize on-demand if needed.
        Cart cart = getCart();

        // Determine taxes.
        List<TaxEntity> taxes = product.getTaxes();
        if (taxes != null) {
            for (TaxEntity productTax : taxes) {
                CartRules cartRules = new CartRules(cart);

                if (!cartRules.hasTax(productTax)) {
                    addTax(productTax);
                }
            }
        }

        // Determine update existing or add new product.
        CartRules rules = new CartRules(cart);
        CartProductEntity existingProduct = rules.findProduct(product);
        if (existingProduct != null) {
            int quantity = existingProduct.getQuantity();
            existingProduct.setQuantity(quantity + 1);
            rules.updateProduct(existingProduct);
        } else {
            CartProductEntity newProduct = CartProductBuilder.from(cart, product);
            cart.getProducts().add(newProduct);
        }

        new CartCalculator(cart).calculate();

        saveCurrentCart();
    }

    private void addTax(TaxEntity tax) {
        if (mCart == null) {
            throw new IllegalStateException("Cart is null");
        }

        if (mCart.getTaxes() == null) {
            mCart.setTaxes(new ArrayList<>());
        }
        mCart.getTaxes().add(CartTaxBuilder.from(mCart, tax));
    }

    public void addCreditPayment(@NonNull PaymentResponseDto paymentResponse) {
        if (mCart == null) {
            throw new IllegalStateException("Cart is null");
        }

        if (mCart.getPayments() == null) {
            mCart.setPayments(new ArrayList<>());
        }

        long approvedAmount = paymentResponse.getApprovedAmount();
        LogHelper.writeWithTrace(Level.CONFIG, TAG,
                "Adding payment with approved amount: " + new CurrencyRules()
                        .getFormattedAmount(approvedAmount, Locale.US));

        mCart.getPayments().add(CartPaymentBuilder.from(
                mCart, GroovyPaymentType.Credit,
                approvedAmount,
                paymentResponse.getGatewayMessage()));

        new CartCalculator(mCart).calculate();

        saveCurrentCart();
    }

    public String getFormattedGrandTotal(@NonNull Locale locale) {
        return new CurrencyRules().getCartTotal(getCart(), locale);
    }

    /**
     * Invoke when the order is complete.
     */
    public void clearCurrentCart() {
        mCart = null;
        LogHelper.writeWithTrace(Level.FINE, TAG, "Cart reference cleared");
    }

    //region Save the Cart

    public void saveCurrentCart() {
        if (mCart == null) {
            LogHelper.writeWithTrace(Level.WARNING, TAG,
                    "Current cart is null - nothing to save");
            return;
        }
        GroovyExecutors.getInstance().getDiskIo().execute(new SaveCartWorker(mCart));
    }

    private class SaveCartWorker implements Runnable {

        @NonNull
        private final Cart mCartToSave;

        SaveCartWorker(@NonNull Cart cart) {
            mCartToSave = cart;
        }

        @Override
        public void run() {
            // Note: We should probably know whether this record already exists in
            // the local database, so we can properly determine whether we need to
            // perform an INSERT or UPDATE (would require making the DAO conflict
            // strategy more strict).
            DatabaseHelper.getInstance().getDatabase().getCartDao()
                    .insertCarts(mCartToSave);

            // Save cart payments.
            List<CartPaymentEntity> payments = mCartToSave.getPayments();
            DatabaseHelper.getInstance().getDatabase().getCartPaymentDao()
                    .insertCartPayments(payments.toArray(new CartPaymentEntity[0]));

            // Save cart products.
            List<CartProductEntity> products = mCartToSave.getProducts();
            DatabaseHelper.getInstance().getDatabase().getCartProductDao()
                    .insertCartProducts(products.toArray(new CartProductEntity[0]));

            // Save cart taxes.
            List<CartTaxEntity> taxes = mCartToSave.getTaxes();
            DatabaseHelper.getInstance().getDatabase().getCartTaxDao()
                    .insertCartTaxes(taxes.toArray(new CartTaxEntity[0]));
        }
    }

    //endregion

    //region Erase the Cart

    public interface EraseCartCallback {

        void onCartErased();
    }

    /**
     * Delete the entire cart model from the local database.
     */
    public void eraseCurrentCart(@NonNull final EraseCartCallback callback) {
        if (mCart == null) {
            LogHelper.writeWithTrace(Level.WARNING, TAG,
                    "Current cart is null - nothing to erase");
            callback.onCartErased();
            return;
        }

        // Execute task on an available background thread.
        GroovyExecutors.getInstance().getDiskIo()
                .execute(new EraseCartWorker(mCart, new EraseCartCallback() {
                    @Override
                    public void onCartErased() {
                        // Nullify the last in-memory reference. Bon voyage!
                        mCart = null;

                        // Fire input callback on main thread.
                        final Handler mainThreadHandler = new Handler(Looper.getMainLooper());
                        mainThreadHandler.post(callback::onCartErased);
                    }
                }));
    }

    private class EraseCartWorker implements Runnable {

        @NonNull
        private final Cart mCartToDelete;
        @NonNull
        private final EraseCartCallback mCallback;

        EraseCartWorker(@NonNull Cart cart, @NonNull EraseCartCallback callback) {
            mCartToDelete = cart;
            mCallback = callback;
        }

        @Override
        public void run() {
            // Delete cart payments.
            List<CartPaymentEntity> payments = mCartToDelete.getPayments();
            DatabaseHelper.getInstance().getDatabase().getCartPaymentDao()
                    .deleteCartPayments(payments.toArray(new CartPaymentEntity[0]));

            // Delete cart taxes.
            List<CartTaxEntity> taxes = mCartToDelete.getTaxes();
            DatabaseHelper.getInstance().getDatabase().getCartTaxDao()
                    .deleteCartTaxes(taxes.toArray(new CartTaxEntity[0]));

            // Delete cart products.
            List<CartProductEntity> products = mCartToDelete.getProducts();
            DatabaseHelper.getInstance().getDatabase().getCartProductDao()
                    .deleteCartProducts(products.toArray(new CartProductEntity[0]));

            // Delete the cart.
            DatabaseHelper.getInstance().getDatabase().getCartDao()
                    .deleteCarts(mCartToDelete);

            mCallback.onCartErased();
        }
    }

    //endregion
}
