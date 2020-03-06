package com.imobile3.groovypayments.data.model;

import com.imobile3.groovypayments.data.enums.GroovyColor;
import com.imobile3.groovypayments.data.enums.GroovyIcon;
import com.imobile3.groovypayments.data.enums.GroovyPaymentType;

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
