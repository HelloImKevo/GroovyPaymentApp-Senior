package com.imobile3.groovypayments.concurrent;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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
        mMainThread = new MainThreadExecutor();
    }

    //endregion

    private Executor mDiskIo;
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
    public Executor getMainThread() {
        return mMainThread;
    }
}
