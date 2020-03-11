package com.imobile3.groovypayments.data;

import com.imobile3.groovypayments.data.model.Cart;

import androidx.annotation.WorkerThread;

import java.util.List;

public class CartDataSource {

    public CartDataSource() {
    }

    @WorkerThread
    public Result<List<Cart>> loadCarts() {
        List<Cart> results =
                DatabaseHelper.getInstance().getDatabase().getCartDao().getCarts();
        return new Result.Success<>(results);
    }
}
