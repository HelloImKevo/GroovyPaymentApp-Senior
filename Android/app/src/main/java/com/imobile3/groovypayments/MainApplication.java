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

package com.imobile3.groovypayments;

import android.app.Application;

import com.imobile3.groovypayments.data.DatabaseHelper;
import com.imobile3.groovypayments.logging.LogHelper;
import com.imobile3.groovypayments.manager.ApiKeyManager;
import com.imobile3.groovypayments.network.WebServiceConfig;

import androidx.annotation.NonNull;

/**
 * @author Kevin Schanz
 */
public class MainApplication extends Application {

    private static MainApplication sSingleton;

    @Override
    public void onCreate() {
        super.onCreate();

        sSingleton = this;

        LogHelper.setEnabled(isDebugMode());
        LogHelper.setDefaultTag("GroovyPayments");

        // Initialize the database.
        DatabaseHelper.getInstance().init(this);
    }

    public static MainApplication getInstance() {
        return sSingleton;
    }

    public boolean isDebugMode() {
        return BuildConfig.DEBUG;
    }

    @NonNull
    public WebServiceConfig getWebServiceConfig() {
        return new WebServiceConfig()
                .setApplicationContext(this)
                .setBaseUrl("https://api.stripe.com")
                .setPublishableApiKey(ApiKeyManager.getInstance().getStripeApiClientKey())
                .setSecretApiKey(ApiKeyManager.getInstance().getStripeApiServerKey())
                .setAppName(getString(R.string.app_name))
                .setAppVersion(BuildConfig.VERSION_NAME);
    }
}
