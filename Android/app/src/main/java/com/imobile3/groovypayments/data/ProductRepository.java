package com.imobile3.groovypayments.data;

/**
 * A repository holds a reference to a data source.
 */
public class ProductRepository {

    private static volatile ProductRepository sInstance;

    private ProductDataSource mDataSource;

    private ProductRepository(ProductDataSource dataSource) {
        mDataSource = dataSource;
    }

    public static synchronized ProductRepository getInstance(ProductDataSource dataSource) {
        if (sInstance == null) {
            sInstance = new ProductRepository(dataSource);
        }
        return sInstance;
    }

    public ProductDataSource getDataSource() {
        return mDataSource;
    }
}
