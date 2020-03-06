package com.imobile3.groovypayments.rules;

import com.imobile3.groovypayments.BaseUnitTest;
import com.imobile3.groovypayments.data.enums.GroovyColor;
import com.imobile3.groovypayments.data.enums.GroovyIcon;
import com.imobile3.groovypayments.data.model.Product;

import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.*;

public class ProductRulesTest extends BaseUnitTest {

    @Test
    public void getIcon() {
        logTestStart(new Throwable());

        Product product = new Product();
        product.setIconId(GroovyIcon.Sandwich.id);
        GroovyIcon result = new ProductRules(product).getIcon();
        assertEquals(GroovyIcon.Sandwich, result);
    }

    @Test
    public void getColor() {
        logTestStart(new Throwable());

        Product product = new Product();
        product.setColorId(GroovyColor.Yellow.id);
        GroovyColor result = new ProductRules(product).getColor();
        assertEquals(GroovyColor.Yellow, result);
    }

    @Test
    public void getUnitPriceDisplay() {
        logTestStart(new Throwable());

        Product product = new Product();
        product.setUnitPrice(1499L);
        String result = new ProductRules(product).getUnitPriceDisplay(Locale.US);
        assertEquals("$14.99", result);
    }

    @Test
    public void getDescription() {
        logTestStart(new Throwable());

        Product product = new Product();
        product.setUnitPrice(999L);
        product.setNote("Unit test note");
        String result = new ProductRules(product).getDescription(Locale.US);
        assertEquals("$9.99 | Unit test note", result);
    }
}
