package com.imobile3.groovypayments;

import android.app.Application;

import com.imobile3.groovypayments.logging.LogHelper;

public class MainApplication extends Application {

    private static MainApplication sSingleton;

    @Override
    public void onCreate() {
        super.onCreate();

        sSingleton = this;

        LogHelper.setEnabled(isDebugMode());
        LogHelper.setDefaultTag("GroovyPayments");
    }

    public static MainApplication getInstance() {
        return sSingleton;
    }

    public boolean isDebugMode() {
        return BuildConfig.DEBUG;
    }
}
