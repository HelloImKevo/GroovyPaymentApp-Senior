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

package com.imobile3.groovypayments.utils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import com.imobile3.groovypayments.logging.LogHelper;

import androidx.annotation.Nullable;

import java.util.logging.Level;

/**
 * @author Kevin Schanz
 */
public class SoftKeyboardHelper {

    private static final String TAG = SoftKeyboardHelper.class.getSimpleName();

    private SoftKeyboardHelper() {
    }

    /**
     * Requests to hide the soft input window from the context of the window
     * that is currently accepting input.
     */
    public static void hide(@Nullable Activity activity) {
        if (activity == null) {
            LogHelper.writeWithTrace(Level.WARNING, TAG, "Activity is null!");
            return;
        }

        InputMethodManager mgr = (InputMethodManager)activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (mgr != null) {
            mgr.hideSoftInputFromWindow(activity
                    .getWindow().getDecorView().getWindowToken(), 0);
        }
    }
}
