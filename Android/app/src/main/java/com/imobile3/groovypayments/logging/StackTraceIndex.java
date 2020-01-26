package com.imobile3.groovypayments.logging;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Index values are all <b>relative</b> to the scope from which they are
 * used, so this mechanism should be used in conjunction with a controlled
 * {@link StackTraceElement} handling utility, like {@link LogUtils}.
 * (Static utility methods add additional trace entities to the stack and
 * must be compensated for, to retrieve accurate stack details).
 */
@IntDef(flag = true, value = {
        StackTraceIndex.CURRENT,
        StackTraceIndex.FIRST_CALLER,
        StackTraceIndex.SECOND_CALLER,
})
@Retention(RetentionPolicy.SOURCE)
public @interface StackTraceIndex {
    /**
     * The first stack index; associated with the current method in scope.
     */
    int CURRENT = 0;

    /**
     * The second stack index; can represent the {@link StackTraceElement}
     * associated with the <b>caller</b> of the current method in scope.
     */
    int FIRST_CALLER = 1;

    /**
     * The third stack index; can represent the immediate ancestor (ie, parent)
     * to the <b>caller</b> of the current method in scope.
     */
    int SECOND_CALLER = 2;
}
