package com.imobile3.groovypayments.ui.orderhistory;

import com.imobile3.groovypayments.data.CartRepository;

import androidx.lifecycle.ViewModel;

/**
 * The ViewModel serves as an async bridge between the View (Activity, Fragment)
 * and our backing data repository (Database).
 */
public class OrderHistoryViewModel extends ViewModel {

    private int mCartClicks;
    private CartRepository mRepository;

    OrderHistoryViewModel(CartRepository repository) {
        mCartClicks = 0;
        mRepository = repository;
    }

    public void addCartClick() {
        mCartClicks++;
    }

    public int getCartClicks() {
        return mCartClicks;
    }
}
