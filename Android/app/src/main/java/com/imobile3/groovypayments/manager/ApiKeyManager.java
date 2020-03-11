package com.imobile3.groovypayments.manager;

import com.imobile3.groovypayments.BuildConfig;
import com.imobile3.groovypayments.MainApplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ApiKeyManager {

    private static final String TAG = ApiKeyManager.class.getSimpleName();

    //region Singleton Implementation

    private static ApiKeyManager sInstance;

    private ApiKeyManager() {
    }

    @NonNull
    public static synchronized ApiKeyManager getInstance() {
        if (sInstance == null) {
            sInstance = new ApiKeyManager();
        }
        return sInstance;
    }

    //endregion

    /**
     * Note to developers: Create a new 'File' in the {@code app} directory called
     * {@code apikeys.gradle} (it is a git ignored file). Add one line to the file
     * like this, and specify your Stripe API key:<br>
     * {@code ext.STRIPE_API_KEY = '"your_api_key"'}
     */
    @Nullable
    public String getStripeApiKey() {
        if (MainApplication.getInstance().isDebugMode()) {
            return BuildConfig.STRIPE_API_KEY;
        } else {
            // TODO: Implement API Keys for release builds.
            return null;
        }
    }
}
