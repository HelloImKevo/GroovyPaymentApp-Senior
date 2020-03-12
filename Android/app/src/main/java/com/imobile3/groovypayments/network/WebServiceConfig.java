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

/**
 * @author Kevin Schanz
 */
public class WebServiceConfig {

    /**
     * Application context for payment SDK initialization.
     */
    private Context mApplicationContext;

    /**
     * Base URL for the payment SDK endpoint.
     * <p>
     * Example: {@code "https://api.stripe.com"}
     */
    private String mBaseUrl;

    /**
     * Publishable API Key for the Android Client.
     */
    private String mPublishableApiKey;

    /**
     * Secret API Key for the Java Server.
     */
    private String mSecretApiKey;

    /**
     * App name for payment SDK analytics.
     */
    private String mAppName;

    /**
     * App version for payment SDK analytics.
     */
    private String mAppVersion;

    public WebServiceConfig() {
    }

    public Context getApplicationContext() {
        return mApplicationContext;
    }

    public WebServiceConfig setApplicationContext(Context applicationContext) {
        mApplicationContext = applicationContext;
        return this;
    }

    public String getBaseUrl() {
        return mBaseUrl;
    }

    public WebServiceConfig setBaseUrl(String baseUrl) {
        mBaseUrl = baseUrl;
        return this;
    }

    public String getPublishableApiKey() {
        return mPublishableApiKey;
    }

    public WebServiceConfig setPublishableApiKey(String publishableApiKey) {
        mPublishableApiKey = publishableApiKey;
        return this;
    }

    public String getSecretApiKey() {
        return mSecretApiKey;
    }

    public WebServiceConfig setSecretApiKey(String secretApiKey) {
        mSecretApiKey = secretApiKey;
        return this;
    }

    public String getAppName() {
        return mAppName;
    }

    public WebServiceConfig setAppName(String appName) {
        mAppName = appName;
        return this;
    }

    public String getAppVersion() {
        return mAppVersion;
    }

    public WebServiceConfig setAppVersion(String appVersion) {
        mAppVersion = appVersion;
        return this;
    }
}
