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

package com.imobile3.groovypayments.data.enums;

import androidx.annotation.Nullable;

/**
 * @author Kevin Schanz
 */
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
