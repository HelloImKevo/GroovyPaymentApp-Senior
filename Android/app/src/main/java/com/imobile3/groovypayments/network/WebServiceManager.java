package com.imobile3.groovypayments.network;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.imobile3.groovypayments.concurrent.GroovyExecutors;
import com.imobile3.groovypayments.logging.LogHelper;
import com.imobile3.groovypayments.utils.JsonHelper;
import com.stripe.Stripe;
import com.stripe.android.PaymentConfiguration;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.logging.Level;

public class WebServiceManager {

    private final String TAG = WebServiceManager.class.getSimpleName();

    private static WebServiceManager sInstance;

    private boolean mInitialized;
    private StripeApi mStripeApi;

    private WebServiceManager() {
        mInitialized = false;
    }

    public static synchronized WebServiceManager getInstance() {
        if (sInstance == null) {
            sInstance = new WebServiceManager();
        }
        return sInstance;
    }

    // TODO: Refactor all this to use an abstracted "InitPayload"

    /**
     * @param baseUrl Example: {@code "https://api.stripe.com"}.
     */
    public void init(Context applicationContext, String baseUrl, String publishableApiKey) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        // TODO: Need to init the Secret Key
        // Initialize the Android Client SDK.
        PaymentConfiguration.init(applicationContext, publishableApiKey);

        // TODO: Initialize the Java Server SDK (set Stripe.AppInfo)

        mStripeApi = retrofit.create(StripeApi.class);
        mInitialized = true;
    }

    @NonNull
    public StripeApi getStripeClient() {
        checkInitCalled();

        return mStripeApi;
    }

    //region Server-Side Java Mechanisms

    public interface ClientSecretCallback {

        @MainThread
        void onClientSecretError(@NonNull String message);

        @MainThread
        void onClientSecretGenerated(@NonNull String clientSecret);
    }

    public void generateClientSecret(
            Context applicationContext,
            String secretApiKey,
            long amount,
            @NonNull final ClientSecretCallback callback) {
        if (secretApiKey == null) {
            secretApiKey = "";
        }

        Stripe.apiKey = secretApiKey;

        ClientSecretTask clientSecretTask =
                new ClientSecretTask(applicationContext, "usd", amount, callback);

        GroovyExecutors.getInstance().getNetwork().execute(clientSecretTask);
    }

    private class ClientSecretTask implements Runnable {

        @NonNull
        private final Handler mMainThreadHandler;
        @NonNull
        private final String mCurrency;
        private final long mAmount;
        @NonNull
        private final ClientSecretCallback mCallback;

        private ClientSecretTask(
                @NonNull Context applicationContext,
                @NonNull String currency,
                long amount,
                @NonNull ClientSecretCallback callback) {
            mMainThreadHandler = new Handler(applicationContext.getMainLooper());
            mCurrency = currency;
            mAmount = amount;
            mCallback = callback;
        }

        @Override
        public void run() {
            PaymentIntentCreateParams createParams = new PaymentIntentCreateParams.Builder()
                    .setCurrency(mCurrency)
                    .setAmount(mAmount)
                    .build();

            PaymentIntent intent;
            try {
                intent = PaymentIntent.create(createParams);
                LogHelper.write(Level.FINE, TAG, "JSON = " + JsonHelper.toPrettyJson(intent));
            } catch (StripeException e) {
                LogHelper.write(Level.WARNING, TAG,
                        "ClientSecretTask Error: " + Log.getStackTraceString(e));

                // Abort! Post callback and return!
                String errorMessage = "Error Generating Client Secret: " + e.getMessage();
                mMainThreadHandler.post(() -> mCallback.onClientSecretError(errorMessage));
                return;
            }

            if (intent != null && intent.getClientSecret() != null) {
                String clientSecret = intent.getClientSecret();

                // Note: The Client Secret is sensitive data and should not be logged.
                // LogHelper.write(Level.CONFIG, TAG, "ClientSecret = " + clientSecret);

                mMainThreadHandler.post(() -> mCallback.onClientSecretGenerated(clientSecret));
            } else {
                String errorMessage = "Payment Intent or Client Secret is null";
                mMainThreadHandler.post(() -> mCallback.onClientSecretError(errorMessage));
            }
        }
    }

    //endregion

    private void checkInitCalled() {
        if (!mInitialized) throw new IllegalStateException("Manager not initialized!");
    }
}
