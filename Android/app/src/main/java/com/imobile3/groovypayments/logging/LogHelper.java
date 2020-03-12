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

package com.imobile3.groovypayments.logging;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * @author Kevin Schanz
 */
@SuppressWarnings({"ConstantConditions", "WeakerAccess", "unused", "FieldCanBeLocal"})
public class LogHelper {

    private static boolean sEnabled;
    private static String sDefaultTag = "GroovyPayments";

    public static void setEnabled(boolean enabled) {
        sEnabled = enabled;
    }

    public static boolean isEnabled() {
        return sEnabled;
    }

    public static void setDefaultTag(String tag) {
        sDefaultTag = tag;
    }

    //region Logging Tools

    public static void write(CharSequence message) {
        write(sDefaultTag, message);
    }

    public static void write(String tag, CharSequence message) {
        write(Level.CONFIG, tag, message);
    }

    /**
     * Example:<br>{@code
     * FileNameCurrent.currentMethod()[250] :: Invoked
     * }
     */
    public static void invoked(@NonNull Level level, String tag) {
        // Because we are using stack trace elements to infer the caller on the method stack,
        // the code becomes less intuitive when method overloads invoke one another. The
        // "duplicate" logic results in code that is easier to interpret.

        if (!sEnabled) {
            // Skip the performance overhead of building local strings that won't be used.
            return;
        }

        String tracePlusMessage = "";

        // These are the details for the *caller* of this method, invoked().
        String callerDetails = LogUtils.getCallingMethodDetails(
                StackTraceIndex.FIRST_CALLER);
        if (callerDetails != null) {
            tracePlusMessage += callerDetails;
        }

        if (tracePlusMessage.length() > 0) {
            tracePlusMessage += " :: ";
        }
        tracePlusMessage += "Invoked";
        write(level, tag, tracePlusMessage);
    }

    /**
     * Example:<br>{@code
     * FileNameCurrent.currentMethod()[250] :: Invoked with (1, 2.0, "Three")
     * }
     */
    public static void invoked(@NonNull Level level, String tag, Object... methodArgs) {
        if (!sEnabled) {
            // Skip the performance overhead of building local strings that won't be used.
            return;
        }

        StringBuilder tracePlusMessage = new StringBuilder();

        // These are the details for the *caller* of this method, invoked().
        String callerDetails = LogUtils.getCallingMethodDetails(
                StackTraceIndex.FIRST_CALLER);
        if (callerDetails != null) {
            tracePlusMessage.append(callerDetails);
        }

        if (tracePlusMessage.length() > 0) {
            tracePlusMessage.append(" :: ");
        }
        tracePlusMessage.append("Invoked");
        if (methodArgs != null && methodArgs.length > 0) {
            tracePlusMessage.append(" with (");
            for (int index = 0; index < methodArgs.length; index++) {
                if (index != 0 && index < methodArgs.length) {
                    tracePlusMessage.append(", ");
                }
                Object arg = methodArgs[index];
                if (arg instanceof String) {
                    tracePlusMessage.append("\"").append(arg).append("\"");
                } else {
                    tracePlusMessage.append(arg);
                }
            }
            tracePlusMessage.append(")");
        }
        write(level, tag, tracePlusMessage.toString());
    }

    /**
     * Prepends the message with call hierarchy / stack trace details,
     * including the file name, source method name, and line number, if
     * they are available (anonymous inner classes and other situations
     * can hide this information).<br>
     * Example:<br>{@code
     * FileNamePrior.callingMethod()[125] --> FileNameCurrent.currentMethod()[250] :: Message
     * }
     */
    public static void writeWithTrace(@NonNull Level level, String tag, CharSequence message) {
        if (!sEnabled) {
            // Skip the performance overhead of building local strings that won't be used.
            return;
        }

        // Within this scope, this method writeWithTrace() is the StackTraceIndex.CURRENT
        // index and the caller of this method is StackTraceIndex.SECOND_CALLER. (Note that
        // the index is relative, and additional stack trace indexes are hidden by LogUtils).
        String tracePlusMessage = "";

        // The "prior" caller is the immediate ancestor to the caller of
        // this method, writeWithTrace().
        // We want the log message to look like: Oldest.Method() --> Newest.Method()
        String priorCallerDetails = LogUtils.getCallingMethodDetails(
                StackTraceIndex.SECOND_CALLER);
        if (priorCallerDetails != null) {
            tracePlusMessage += priorCallerDetails;
        }

        // These are the details for the *caller* of this method, writeWithTrace().
        String callerDetails = LogUtils.getCallingMethodDetails(
                StackTraceIndex.FIRST_CALLER);
        if (callerDetails != null) {
            if (tracePlusMessage.length() > 0) {
                tracePlusMessage += " -> ";
            }
            tracePlusMessage += callerDetails;
        }

        if (tracePlusMessage.length() > 0) {
            tracePlusMessage += " :: ";
        }
        tracePlusMessage += message;
        write(level, tag, tracePlusMessage);
    }

    public static void write(@NonNull Level level, String tag, CharSequence message) {
        if (sEnabled) {
            if (message != null) {
                publishToAndroidLog(tag, new LogRecord(level, message.toString()));
            } else {
                publishToAndroidLog(tag, new LogRecord(level, "null"));
            }
        }
    }

    private static void publishToAndroidLog(String tag, @NonNull LogRecord record) {
        String logMessage = record.getMessage();
        if (logMessage == null) {
            logMessage = "null";
        }
        int value = record.getLevel().intValue();
        if (value >= Level.SEVERE.intValue()) {
            Log.e(tag, logMessage);
        } else if (value >= Level.WARNING.intValue()) {
            Log.w(tag, logMessage);
        } else if (value >= Level.INFO.intValue()) {
            Log.i(tag, logMessage);
        } else if (value >= Level.CONFIG.intValue()) {
            Log.d(tag, logMessage);
        } else {
            Log.v(tag, logMessage);
        }
    }

    //endregion
}
