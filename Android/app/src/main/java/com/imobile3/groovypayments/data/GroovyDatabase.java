package com.imobile3.groovypayments.data;

import com.imobile3.groovypayments.data.dao.CartDao;
import com.imobile3.groovypayments.data.dao.CartPaymentDao;
import com.imobile3.groovypayments.data.dao.CartProductDao;
import com.imobile3.groovypayments.data.dao.CartTaxDao;
import com.imobile3.groovypayments.data.dao.ProductDao;
import com.imobile3.groovypayments.data.dao.ProductTaxJunctionDao;
import com.imobile3.groovypayments.data.dao.TaxDao;
import com.imobile3.groovypayments.data.dao.UserDao;
import com.imobile3.groovypayments.data.entities.CartEntity;
import com.imobile3.groovypayments.data.entities.CartPaymentEntity;
import com.imobile3.groovypayments.data.entities.CartProductEntity;
import com.imobile3.groovypayments.data.entities.CartTaxEntity;
import com.imobile3.groovypayments.data.entities.ProductEntity;
import com.imobile3.groovypayments.data.entities.ProductTaxJunctionEntity;
import com.imobile3.groovypayments.data.entities.TaxEntity;
import com.imobile3.groovypayments.data.entities.UserEntity;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import static com.imobile3.groovypayments.data.GroovyDatabase.VERSION;

@Database(version = VERSION,
        entities = {
                CartEntity.class,
                CartPaymentEntity.class,
                CartProductEntity.class,
                CartTaxEntity.class,
                ProductEntity.class,
                ProductTaxJunctionEntity.class,
                TaxEntity.class,
                UserEntity.class})
public abstract class GroovyDatabase extends RoomDatabase {

    public static final int VERSION = 1;
    public static final String NAME = "groovy.sqlite";

    public abstract CartDao getCartDao();

    public abstract CartPaymentDao getCartPaymentDao();

    public abstract CartProductDao getCartProductDao();

    public abstract CartTaxDao getCartTaxDao();

    public abstract ProductDao getProductDao();

    public abstract TaxDao getTaxDao();

    public abstract ProductTaxJunctionDao getProductTaxJunctionDao();

    public abstract UserDao getUserDao();
}
