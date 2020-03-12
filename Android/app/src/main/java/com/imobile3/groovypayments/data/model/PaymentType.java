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

package com.imobile3.groovypayments.data.model;

import com.imobile3.groovypayments.data.enums.GroovyColor;
import com.imobile3.groovypayments.data.enums.GroovyIcon;
import com.imobile3.groovypayments.data.enums.GroovyPaymentType;

/**
 * @author Kevin Schanz
 */
public class PaymentType {

    /**
     * Example: Cash, Credit, Check.
     */
    private String mName;
    private GroovyPaymentType mType;
    private GroovyIcon mIcon;
    private GroovyColor mColor;

    public PaymentType() {
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public GroovyPaymentType getType() {
        return mType;
    }

    public void setType(GroovyPaymentType type) {
        mType = type;
    }

    public GroovyIcon getIcon() {
        return mIcon;
    }

    public void setIcon(GroovyIcon icon) {
        mIcon = icon;
    }

    public GroovyColor getColor() {
        return mColor;
    }

    public void setColor(GroovyColor color) {
        mColor = color;
    }
}
