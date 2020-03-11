package com.imobile3.groovypayments.calculation;

import com.imobile3.groovypayments.BaseUnitTest;
import com.imobile3.groovypayments.data.entities.CartProductEntity;
import com.imobile3.groovypayments.data.entities.CartTaxEntity;
import com.imobile3.groovypayments.data.model.Cart;
import com.imobile3.groovypayments.data.utils.CartBuilder;
import com.imobile3.groovypayments.data.utils.CartProductBuilder;
import com.imobile3.groovypayments.data.utils.CartTaxBuilder;
import com.imobile3.groovypayments.logging.LogHelper;
import com.imobile3.groovypayments.rules.CurrencyRules;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
public class CartCalculatorTest extends BaseUnitTest {

    private static final String TAG = CartCalculatorTest.class.getSimpleName();

    static {
        LogHelper.setEnabled(true);
    }

    @Test
    public void calculate() {
        logTestStart(new Throwable());

        Cart cart = new Cart(CartBuilder.build(801L,
                new Date(1857000000L),
                0L, 0L, 0L));

        // Products
        List<CartProductEntity> products = new ArrayList<>();
        products.add(CartProductBuilder.build((cart.getId() * 2) + 1L,
                cart.getId(),
                "Calculated Fries",
                500L, 1));
        products.add(CartProductBuilder.build((cart.getId() * 2) + 2L,
                cart.getId(),
                "Calculated Apple",
                125L, 5));

        // Taxes
        List<CartTaxEntity> taxes = new ArrayList<>();
        taxes.add(CartTaxBuilder.build((cart.getId() * 2) + 301L,
                cart.getId(),
                "Test Tax 2%", "0.02"));

        taxes.add(CartTaxBuilder.build((cart.getId() * 2) + 302L,
                cart.getId(),
                "Test Tax 4%", "0.04"));

        cart.setProducts(products);
        cart.setTaxes(taxes);

        new CartCalculator(cart).calculate();

        LogHelper.write(Level.CONFIG, TAG, "Subtotal: " + cart.getSubtotal());
        LogHelper.write(Level.CONFIG, TAG, "TaxTotal: " + cart.getTaxTotal());
        LogHelper.write(Level.CONFIG, TAG, "GrandTotal: " + cart.getGrandTotal());

        assertEquals(1125L, cart.getSubtotal());
        assertEquals(68L, cart.getTaxTotal());
        assertEquals(1193L, cart.getGrandTotal());

        LogHelper.write(Level.CONFIG, TAG, "Formatted Total: " +
                new CurrencyRules().getCartTotal(cart, Locale.US));
    }
}
