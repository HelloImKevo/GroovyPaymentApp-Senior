/*
 *  Copyright (c) 2020 iMobile3, LLC. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, is permitted provided that adherence to the following
 *  conditions is maintained. If you do not agree with these terms,
 *  please do not use, install, modify or redistribute this software.
 *
 *  1. Redistributions of source code must retain the above copyright notice, this
 *  list of conditions and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 */

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

/**
 * @author Kevin Schanz
 */
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

    public void init(WebServiceConfig config) {
        if (mInitialized) {
            LogHelper.writeWithTrace(Level.FINE, TAG, "Web services already initialized");
            return;
        }

        if (config == null
                || config.getApplicationContext() == null
                || config.getBaseUrl() == null
                || config.getPublishableApiKey() == null
                || config.getSecretApiKey() == null) {
            throw new IllegalStateException("Application environment is not set up correctly" +
                    " - missing one or more required attributes for payment processing");
        }

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(config.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        // Initialize the Android Client SDK.
        PaymentConfiguration.init(
                config.getApplicationContext(), config.getPublishableApiKey());

        // Initialize the Java Server SDK.
        Stripe.apiKey = config.getSecretApiKey();
        Stripe.setAppInfo(config.getAppName(), config.getAppVersion());

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
            long amount,
            @NonNull final ClientSecretCallback callback) {
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
