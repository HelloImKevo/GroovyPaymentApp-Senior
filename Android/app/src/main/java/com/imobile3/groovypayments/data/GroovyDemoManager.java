package com.imobile3.groovypayments.data;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.imobile3.groovypayments.MainApplication;
import com.imobile3.groovypayments.calculation.CartCalculator;
import com.imobile3.groovypayments.data.entities.CartEntity;
import com.imobile3.groovypayments.data.entities.CartProductEntity;
import com.imobile3.groovypayments.data.entities.CartTaxEntity;
import com.imobile3.groovypayments.data.entities.ProductEntity;
import com.imobile3.groovypayments.data.entities.ProductTaxJunctionEntity;
import com.imobile3.groovypayments.data.entities.TaxEntity;
import com.imobile3.groovypayments.data.model.Cart;
import com.imobile3.groovypayments.data.model.Product;
import com.imobile3.groovypayments.logging.LogHelper;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import static com.imobile3.groovypayments.data.TestDataRepository.Environment;

public final class GroovyDemoManager {

    private static final String TAG = GroovyDemoManager.class.getSimpleName();

    //region Singleton Implementation

    private static GroovyDemoManager sInstance;

    private GroovyDemoManager() {
    }

    @NonNull
    public static synchronized GroovyDemoManager getInstance() {
        if (sInstance == null) {
            sInstance = new GroovyDemoManager();
        }
        return sInstance;
    }

    //endregion

    public interface ResetDatabaseCallback {

        void onDatabaseReset();
    }

    /**
     * Delete the current database instance (potentially dangerous operation!)
     * and populate a new instance with fresh demo data.
     */
    public void resetDatabase(
            @NonNull final ResetDatabaseCallback callback) {
        new ResetDatabaseTask(MainApplication.getInstance(), callback)
                .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private class ResetDatabaseTask extends AsyncTask<Void, Void, Void> {

        @NonNull
        private final Context mContext;
        @NonNull
        private final ResetDatabaseCallback mCallback;

        private ResetDatabaseTask(
                @NonNull final Context context,
                @NonNull final ResetDatabaseCallback callback) {
            mContext = context;
            mCallback = callback;
        }

        @Override
        protected Void doInBackground(Void... params) {
            DatabaseHelper.getInstance().eraseDatabase(mContext);
            DatabaseHelper.getInstance().init(mContext);
            final ExecutorService executorService = Executors.newFixedThreadPool(4);

            int workerThreadCount = 2;
            final CountDownLatch countDownLatch = new CountDownLatch(workerThreadCount);

            try {
                executorService.execute(new InventoryWorker(countDownLatch));
                executorService.execute(new OrderHistoryWorker(countDownLatch));
            } catch (RejectedExecutionException e) {
                LogHelper.write(TAG, Log.getStackTraceString(e));
            }

            try {
                countDownLatch.await(2, TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                LogHelper.write(TAG, Log.getStackTraceString(e));
            }

            executorService.shutdown();

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            mCallback.onDatabaseReset();
        }
    }

    /**
     * Generate the application inventory (products, taxes, etc).
     */
    private class InventoryWorker implements Runnable {

        @NonNull
        private final CountDownLatch mSignal;

        private InventoryWorker(@NonNull CountDownLatch signal) {
            mSignal = signal;
        }

        @Override
        public void run() {
            List<ProductEntity> productEntities = TestDataRepository.getInstance()
                    .getProducts(TestDataRepository.Environment.GroovyDemo);

            List<TaxEntity> taxEntities = TestDataRepository.getInstance()
                    .getTaxes(TestDataRepository.Environment.GroovyDemo);
            List<ProductTaxJunctionEntity> productTaxJunctionEntities = new ArrayList<>();
            for (ProductEntity product : productEntities) {
                productTaxJunctionEntities.addAll(TestDataRepository.getInstance()
                        .getProductTaxJunctions(product, taxEntities));
            }
            DatabaseHelper.getInstance().getDatabase().getProductDao()
                    .insertProducts(
                            productEntities.toArray(new ProductEntity[0]));
            DatabaseHelper.getInstance().getDatabase().getTaxDao()
                    .insertTaxes(
                            taxEntities.toArray(new TaxEntity[0]));
            DatabaseHelper.getInstance().getDatabase().getProductTaxJunctionDao()
                    .insertProductTaxJunctions(
                            productTaxJunctionEntities.toArray(new ProductTaxJunctionEntity[0]));
            List<Product> productResults =
                    DatabaseHelper.getInstance().getDatabase()
                            .getProductDao().getProducts();
            List<TaxEntity> taxResults =
                    DatabaseHelper.getInstance().getDatabase()
                            .getTaxDao().getTaxes();
            List<ProductTaxJunctionEntity> productTaxJunctionsResults =
                    DatabaseHelper.getInstance().getDatabase()
                            .getProductTaxJunctionDao().getProductTaxJunctions();

            LogHelper.write(Level.CONFIG, TAG, "Successfully generated inventory data ..." +
                    "\n" + productResults.size() + " products" +
                    "\n" + taxResults.size() + " taxes" +
                    "\n" + productTaxJunctionsResults.size() + " product-tax-junctions");

            // The signal must always be notified once!
            mSignal.countDown();
        }
    }

    /**
     * Generate the application order history (carts).
     */
    private class OrderHistoryWorker implements Runnable {

        @NonNull
        private final CountDownLatch mSignal;

        private OrderHistoryWorker(@NonNull CountDownLatch signal) {
            mSignal = signal;
        }

        @Override
        public void run() {
            List<CartEntity> cartEntities = TestDataRepository.getInstance()
                    .getCarts(Environment.GroovyDemo);

            List<CartProductEntity> cartProductEntities = new ArrayList<>();
            List<CartTaxEntity> cartTaxEntities = new ArrayList<>();
            List<Cart> calculatedCarts = new ArrayList<>();
            for (CartEntity cartEntity : cartEntities) {
                Cart cart = new Cart(cartEntity);

                cart.setProducts(TestDataRepository.getInstance()
                        .getCartProducts(Environment.GroovyDemo, cartEntity));
                cart.setTaxes(TestDataRepository.getInstance()
                        .getCartTaxes(Environment.GroovyDemo, cartEntity));

                // Perform calculations on cart objects.
                new CartCalculator(cart).calculate();
                calculatedCarts.add(cart);

                cartProductEntities.addAll(cart.getProducts());
                cartTaxEntities.addAll(cart.getTaxes());
            }

            // Update our cart entities with the calculated results.
            cartEntities.clear();
            cartEntities.addAll(calculatedCarts);

            GroovyDatabase databaseInstance = DatabaseHelper.getInstance().getDatabase();

            // Insert entities into database instance.
            databaseInstance.getCartDao()
                    .insertCarts(
                            cartEntities.toArray(new CartEntity[0]));
            databaseInstance.getCartProductDao()
                    .insertCartProducts(
                            cartProductEntities.toArray(new CartProductEntity[0]));
            databaseInstance.getCartTaxDao()
                    .insertCartTaxes(
                            cartTaxEntities.toArray(new CartTaxEntity[0]));

            // Optional verification that data exists.
            List<Cart> cartResults =
                    databaseInstance.getCartDao().getCarts();
            List<CartProductEntity> cartProductResults =
                    databaseInstance.getCartProductDao().getCartProducts();
            List<CartTaxEntity> cartTaxResults =
                    databaseInstance.getCartTaxDao().getCartTaxes();

            LogHelper.write(Level.CONFIG, TAG, "Successfully generated order history data ..." +
                    "\n" + cartResults.size() + " carts" +
                    "\n" + cartProductResults.size() + " cart products" +
                    "\n" + cartTaxResults.size() + " cart taxes");

            // The signal must always be notified once!
            mSignal.countDown();
        }
    }
}
