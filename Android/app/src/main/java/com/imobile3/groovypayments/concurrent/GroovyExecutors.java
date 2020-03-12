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

package com.imobile3.groovypayments.concurrent;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author Kevin Schanz
 */
public class GroovyExecutors {

    //region Singleton Implementation

    private static GroovyExecutors sInstance;

    public static synchronized GroovyExecutors getInstance() {
        if (sInstance == null) {
            sInstance = new GroovyExecutors();
        }
        return sInstance;
    }

    private GroovyExecutors() {
        mDiskIo = Executors.newFixedThreadPool(4);
        mNetwork = Executors.newFixedThreadPool(1);
        mMainThread = new MainThreadExecutor();
    }

    //endregion

    private Executor mDiskIo;
    private Executor mNetwork;
    private Executor mMainThread;

    private class MainThreadExecutor implements Executor {
        private Handler mMainThread = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mMainThread.post(command);
        }
    }

    @NonNull
    public Executor getDiskIo() {
        return mDiskIo;
    }

    @NonNull
    public Executor getNetwork() {
        return mNetwork;
    }

    @NonNull
    public Executor getMainThread() {
        return mMainThread;
    }
}
