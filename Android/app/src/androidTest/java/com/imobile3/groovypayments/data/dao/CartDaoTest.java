package com.imobile3.groovypayments.data.dao;

import android.content.Context;

import com.imobile3.groovypayments.data.GroovyDatabase;
import com.imobile3.groovypayments.data.TestDataRepository;
import com.imobile3.groovypayments.data.entities.CartEntity;
import com.imobile3.groovypayments.data.entities.CartProductEntity;
import com.imobile3.groovypayments.data.entities.CartTaxEntity;
import com.imobile3.groovypayments.data.model.Cart;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import java.util.ArrayList;
import java.util.List;

import static com.imobile3.groovypayments.data.TestDataRepository.Environment;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class CartDaoTest {

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
    public void insertCarts() {
        List<CartEntity> cartEntities = TestDataRepository.getInstance()
                .getCarts(Environment.InstrumentationTest);

        List<CartProductEntity> cartProductEntities = new ArrayList<>();
        List<CartTaxEntity> cartTaxEntities = new ArrayList<>();
        for (CartEntity cart : cartEntities) {
            cartProductEntities.addAll(TestDataRepository.getInstance()
                    .getCartProducts(Environment.InstrumentationTest, cart));

            cartTaxEntities.addAll(TestDataRepository.getInstance()
                    .getCartTaxes(Environment.InstrumentationTest, cart));
        }

        // Insert entities into database instance.
        mGroovyDatabase.getCartDao()
                .insertCarts(
                        cartEntities.toArray(new CartEntity[0]));
        mGroovyDatabase.getCartProductDao()
                .insertCartProducts(
                        cartProductEntities.toArray(new CartProductEntity[0]));
        mGroovyDatabase.getCartTaxDao()
                .insertCartTaxes(
                        cartTaxEntities.toArray(new CartTaxEntity[0]));

        List<Cart> cartResults =
                mGroovyDatabase.getCartDao().getCarts();
        List<CartProductEntity> cartProductResults =
                mGroovyDatabase.getCartProductDao().getCartProducts();
        List<CartTaxEntity> cartTaxResults =
                mGroovyDatabase.getCartTaxDao().getCartTaxes();

        // Verify cart products exist and the cart tax associations are populated.
        assertNotNull(cartResults);
        assertFalse(cartResults.get(0).getProducts().isEmpty());
        assertFalse(cartResults.get(0).getTaxes().isEmpty());

        assertNotNull(cartProductResults);
        assertFalse(cartProductResults.isEmpty());

        assertNotNull(cartTaxResults);
        assertFalse(cartTaxResults.isEmpty());
    }
}
