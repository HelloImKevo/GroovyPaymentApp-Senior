package com.imobile3.groovypayments;

import android.app.Application;

import com.imobile3.groovypayments.data.DatabaseHelper;
import com.imobile3.groovypayments.logging.LogHelper;

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
}
