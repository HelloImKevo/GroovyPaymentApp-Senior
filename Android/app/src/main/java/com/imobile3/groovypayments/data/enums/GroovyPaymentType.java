package com.imobile3.groovypayments.data.enums;

import androidx.annotation.Nullable;

public enum GroovyPaymentType {

    Cash(1),
    Credit(2);

    public final int id;

    GroovyPaymentType(int id) {
        this.id = id;
    }

    @Nullable
    public static GroovyPaymentType fromId(int id) {
        for (GroovyPaymentType value : values()) {
            if (value.id == id) {
                return value;
            }
        }
        return null;
    }
}
