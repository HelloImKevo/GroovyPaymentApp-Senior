package com.imobile3.groovypayments.data.dao;

import android.content.Context;

import com.imobile3.groovypayments.data.TestDataRepository;
import com.imobile3.groovypayments.data.GroovyDatabase;
import com.imobile3.groovypayments.data.entities.ProductEntity;
import com.imobile3.groovypayments.data.entities.ProductTaxJunctionEntity;
import com.imobile3.groovypayments.data.entities.TaxEntity;
import com.imobile3.groovypayments.data.model.Product;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ProductDaoTest {

    private GroovyDatabase mGroovyDatabase;

    @Before
    public void setUp() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        context.deleteDatabase(GroovyDatabase.NAME);
        mGroovyDatabase = Room.inMemoryDatabaseBuilder(context, GroovyDatabase.class)
                .build();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void insertProducts() {
        List<ProductEntity> productEntities = TestDataRepository.getInstance()
                .getProducts(TestDataRepository.Environment.InstrumentationTest);

        List<TaxEntity> taxEntities = TestDataRepository.getInstance()
                .getTaxes(TestDataRepository.Environment.InstrumentationTest);

        // Generate product-tax associations with junction entities.
        List<ProductTaxJunctionEntity> productTaxJunctionEntities = new ArrayList<>();
        for (ProductEntity product : productEntities) {
            // Associate all taxes with each product.
            productTaxJunctionEntities.addAll(TestDataRepository.getInstance()
                    .getProductTaxJunctions(product, taxEntities));
        }

        // Insert entities into database instance.
        mGroovyDatabase.getProductDao()
                .insertProducts(
                        productEntities.toArray(new ProductEntity[0]));
        mGroovyDatabase.getTaxDao()
                .insertTaxes(
                        taxEntities.toArray(new TaxEntity[0]));
        mGroovyDatabase.getProductTaxJunctionDao()
                .insertProductTaxJunctions(
                        productTaxJunctionEntities.toArray(new ProductTaxJunctionEntity[0]));

        List<Product> productResults =
                mGroovyDatabase.getProductDao().getProducts();
        List<TaxEntity> taxResults =
                mGroovyDatabase.getTaxDao().getTaxes();
        List<ProductTaxJunctionEntity> productTaxJunctionsResults =
                mGroovyDatabase.getProductTaxJunctionDao().getProductTaxJunctions();

        // Verify products exist and their tax associations are populated.
        assertNotNull(productResults);
        assertFalse(productResults.get(0).getTaxes().isEmpty());

        assertNotNull(taxResults);
        assertFalse(taxResults.isEmpty());

        assertNotNull(productTaxJunctionsResults);
        assertFalse(productTaxJunctionsResults.isEmpty());
    }

    @Test
    public void deleteProducts() {
        List<ProductEntity> productEntities = TestDataRepository.getInstance()
                .getProducts(TestDataRepository.Environment.InstrumentationTest);

        // Insert entities into database instance.
        mGroovyDatabase.getProductDao()
                .insertProducts(
                        productEntities.toArray(new ProductEntity[0]));

        // Delete all the product entities.
        mGroovyDatabase.getProductDao()
                .deleteProducts(
                        productEntities.toArray(new ProductEntity[0]));

        List<Product> productResults =
                mGroovyDatabase.getProductDao().getProducts();

        assertNotNull(productResults);
        assertTrue(productResults.isEmpty());
    }
}
