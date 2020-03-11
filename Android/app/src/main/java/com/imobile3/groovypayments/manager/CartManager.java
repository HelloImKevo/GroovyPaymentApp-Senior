package com.imobile3.groovypayments.manager;

import com.imobile3.groovypayments.data.model.Cart;
import com.imobile3.groovypayments.logging.LogHelper;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;

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
        cart.setProducts(new ArrayList<>());
        cart.setTaxes(new ArrayList<>());
        cart.setId(id);
        cart.setDateCreated(new Date());
        return cart;
    }
}
