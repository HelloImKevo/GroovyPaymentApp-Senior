package com.imobile3.groovypayments.data;

import com.imobile3.groovypayments.data.model.Product;

import androidx.annotation.WorkerThread;

import java.util.List;

public class ProductDataSource {

    public ProductDataSource() {
    }

    @WorkerThread
    public Result<List<Product>> loadProducts() {
        List<Product> results =
                DatabaseHelper.getInstance().getDatabase().getProductDao().getProducts();
        return new Result.Success<>(results);
    }
}
