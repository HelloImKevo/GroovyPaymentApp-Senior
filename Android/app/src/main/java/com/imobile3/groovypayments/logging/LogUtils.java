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

import androidx.annotation.Nullable;

import java.util.logging.LogRecord;

/**
 * @author Kevin Schanz
 */
public class LogUtils {

    private LogUtils() {
    }

    /**
     * Returns the file name, source method name, and line number of the
     * executed code in the caller. Within this method scope, two additional
     * {@link StackTraceElement} entities are added to the stack (and compensated for).
     * <p>
     * Specifying {@link StackTraceIndex#CURRENT} will return the details of the
     * method calling this method.
     *
     * @return Example:
     * {@code "FileName.startTaskProcessing()[120]"}
     */
    @Nullable
    public static String getCallingMethodDetails(@StackTraceIndex final int index) {
        // Add +2 to the indexes to compensate for this current method added
        // to the stack trace, and the additional static utility method calls
        // below (which add another index to the current stack trace).
        String fileName = getFileName(index + 2);
        String methodName = getMethodName(index + 2);
        int lineNumber = getLineNumber(index + 2);

        String methodDetails = "";
        if (fileName != null) {
            // Remove file extensions (nearly all of them will be .java)
            if (fileName.contains(".")) {
                fileName = fileName.substring(0, fileName.lastIndexOf('.'));
            }
            methodDetails += fileName;
        }
        if (methodName != null) {
            if (methodDetails.length() > 0) {
                methodDetails += ".";
            }
            methodDetails += methodName + "()";
        }
        if (lineNumber > 0) {
            methodDetails += "[" + lineNumber + "]";
        }
        return methodDetails.isEmpty() ? null : methodDetails;
    }

    /**
     * Scans the current stack trace for the specified index, and returns
     * the name of the associated file. Note that this method will be the
     * first {@link StackTraceElement} at index 0, so passing a 0 argument
     * will yield the name of this class, {@link LogUtils}.
     * <p/>
     * An example of the approach used can be found in the
     * {@linkplain LogRecord LogRecord's} {@code inferCaller()} method!
     *
     * @return The name of the file (including the extension), like LogUtils.java,
     * at the specified index, or null if it is not available.
     */
    @Nullable
    private static String getFileName(final int stackTraceIndex) {
        StackTraceElement[] stackTraces = (new Throwable()).getStackTrace();
        if (stackTraces.length > stackTraceIndex) {
            String fileName = stackTraces[stackTraceIndex].getFileName();
            if (fileName != null) {
                return fileName;
            }
        }
        return null;
    }

    /**
     * Does the same thing as {@link #getFileName(int)}, but returns the
     * name of the source method (instead of the file name).
     *
     * @return The name of the source method, like processTransaction(),
     * at the specified index, or null if it is not available.
     */
    @Nullable
    private static String getMethodName(final int stackTraceIndex) {
        StackTraceElement[] stackTraces = (new Throwable()).getStackTrace();
        if (stackTraces.length > stackTraceIndex) {
            String methodName = stackTraces[stackTraceIndex].getMethodName();
            if (methodName != null) {
                return methodName;
            }
        }
        return null;
    }

    /**
     * Uses the same approach as {@link #getFileName(int)}, and returns
     * the line number of the executed code.
     *
     * @return The line number within the calling file, at the specified
     * index, or null if it is not available.
     */
    private static int getLineNumber(final int stackTraceIndex) {
        StackTraceElement[] stackTraces = (new Throwable()).getStackTrace();
        return stackTraces.length > stackTraceIndex
                ? stackTraces[stackTraceIndex].getLineNumber() : -1;
    }
}
