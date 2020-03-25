package com.imobile3.groovypayments.data;

import android.content.Context;
import android.os.AsyncTask;

import com.imobile3.groovypayments.MainApplication;
import com.imobile3.groovypayments.data.entities.ProductEntity;
import com.imobile3.groovypayments.data.enums.GroovyColor;
import com.imobile3.groovypayments.data.enums.GroovyIcon;
import com.imobile3.groovypayments.data.utils.ProductBuilder;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

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
            // Blow away any existing database instance.
            DatabaseHelper.getInstance().eraseDatabase(mContext);

            // Initialize a new database instance.
            DatabaseHelper.getInstance().init(mContext);

            List<ProductEntity> productEntities = new ArrayList<>();

            // Add one product.
            productEntities.add(ProductBuilder.build(101L,
                    "Tasty Chicken Sandwich",
                    "Chicken, lettuce, tomato and mayo",
                    750L, 200L,
                    GroovyIcon.Sandwich, GroovyColor.Yellow));

            // Insert entities into database instance.
            DatabaseHelper.getInstance().getDatabase().getProductDao()
                    .insertProducts(
                            productEntities.toArray(new ProductEntity[0]));

            // All done!
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            mCallback.onDatabaseReset();
        }
    }
}
