package com.imobile3.groovypayments.data;

/**
 * A repository holds a reference to different kinds of data sources
 * (web services, local database or cached data).
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
