package com.imobile3.groovypayments.manager;

import com.imobile3.groovypayments.calculation.CartCalculator;
import com.imobile3.groovypayments.concurrent.GroovyExecutors;
import com.imobile3.groovypayments.data.DatabaseHelper;
import com.imobile3.groovypayments.data.entities.CartProductEntity;
import com.imobile3.groovypayments.data.entities.CartTaxEntity;
import com.imobile3.groovypayments.data.entities.TaxEntity;
import com.imobile3.groovypayments.data.model.Cart;
import com.imobile3.groovypayments.data.model.Product;
import com.imobile3.groovypayments.data.utils.CartProductBuilder;
import com.imobile3.groovypayments.data.utils.CartTaxBuilder;
import com.imobile3.groovypayments.logging.LogHelper;
import com.imobile3.groovypayments.rules.CartRules;
import com.imobile3.groovypayments.rules.CurrencyRules;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;

public class CartManager {

    private static final String TAG = CartManager.class.getSimpleName();

    //region Singleton Implementation

    private static CartManager sInstance;

    private CartManager() {
    }

    @NonNull
    public static synchronized CartManager getInstance() {
        if (sInstance == null) {
            sInstance = new CartManager();
        }
        return sInstance;
    }

    //endregion

    @Nullable
    private Cart mCart;

    @NonNull
    public Cart getCart() {
        if (mCart == null) {
            LogHelper.writeWithTrace(Level.CONFIG, TAG, "Initializing new cart instance");
            mCart = initNewCart(System.currentTimeMillis());
        }
        return mCart;
    }

    private Cart initNewCart(long id) {
        Cart cart = new Cart();
        cart.setProducts(new ArrayList<>());
        cart.setTaxes(new ArrayList<>());
        cart.setId(id);
        cart.setDateCreated(new Date());
        return cart;
    }

    public void addProduct(Product product) {
        // Initialize on-demand if needed.
        Cart cart = getCart();

        // Determine taxes.
        List<TaxEntity> taxes = product.getTaxes();
        if (taxes != null) {
            for (TaxEntity productTax : taxes) {
                CartRules cartRules = new CartRules(cart);

                if (!cartRules.hasTax(productTax)) {
                    addTax(productTax);
                }
            }
        }

        // Determine update existing or add new product.
        CartRules rules = new CartRules(cart);
        CartProductEntity existingProduct = rules.findProduct(product);
        if (existingProduct != null) {
            int quantity = existingProduct.getQuantity();
            existingProduct.setQuantity(quantity + 1);
            rules.updateProduct(existingProduct);
        } else {
            CartProductEntity newProduct = CartProductBuilder.from(cart, product);
            cart.getProducts().add(newProduct);
        }

        new CartCalculator(cart).calculate();
    }

    private void addTax(TaxEntity tax) {
        if (mCart == null) {
            throw new IllegalStateException("Cart is null");
        }

        if (mCart.getTaxes() == null) {
            mCart.setTaxes(new ArrayList<>());
        }
        mCart.getTaxes().add(CartTaxBuilder.from(mCart, tax));
    }

    public String getFormattedGrandTotal(@NonNull Locale locale) {
        return new CurrencyRules().getCartTotal(getCart(), locale);
    }

    //region Save the Cart

    public void saveCurrentCart() {
        if (mCart == null) {
            LogHelper.writeWithTrace(Level.WARNING, TAG,
                    "Current cart is null - nothing to save");
            return;
        }
        GroovyExecutors.getInstance().getDiskIo().execute(new SaveCartWorker(mCart));
    }

    private class SaveCartWorker implements Runnable {

        @NonNull
        private final Cart mCartToSave;

        SaveCartWorker(@NonNull Cart cart) {
            mCartToSave = cart;
        }

        @Override
        public void run() {
            // Note: We should probably know whether this record already exists in
            // the local database, so we can properly determine whether we need to
            // perform an INSERT or UPDATE (would require making the DAO conflict
            // strategy more strict).
            DatabaseHelper.getInstance().getDatabase().getCartDao()
                    .insertCarts(mCartToSave);

            // Save cart products.
            List<CartProductEntity> products = mCartToSave.getProducts();
            DatabaseHelper.getInstance().getDatabase().getCartProductDao()
                    .insertCartProducts(products.toArray(new CartProductEntity[0]));

            // Save cart taxes.
            List<CartTaxEntity> taxes = mCartToSave.getTaxes();
            DatabaseHelper.getInstance().getDatabase().getCartTaxDao()
                    .insertCartTaxes(taxes.toArray(new CartTaxEntity[0]));
        }
    }

    //endregion
}
