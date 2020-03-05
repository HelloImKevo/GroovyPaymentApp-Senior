package com.imobile3.groovypayments.data;

import com.imobile3.groovypayments.data.dao.ProductDao;
import com.imobile3.groovypayments.data.dao.ProductTaxJunctionDao;
import com.imobile3.groovypayments.data.dao.TaxDao;
import com.imobile3.groovypayments.data.entities.ProductEntity;
import com.imobile3.groovypayments.data.entities.ProductTaxJunctionEntity;
import com.imobile3.groovypayments.data.entities.TaxEntity;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import static com.imobile3.groovypayments.data.GroovyDatabase.VERSION;

@Database(version = VERSION,
        entities = {
                ProductEntity.class,
                ProductTaxJunctionEntity.class,
                TaxEntity.class})
public abstract class GroovyDatabase extends RoomDatabase {

    public static final int VERSION = 1;
    public static final String NAME = "groovy.sqlite";

    public abstract ProductDao getProductDao();

    public abstract TaxDao getTaxDao();

    public abstract ProductTaxJunctionDao getProductTaxJunctionDao();
}
