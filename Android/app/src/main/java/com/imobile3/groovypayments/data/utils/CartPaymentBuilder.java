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

package com.imobile3.groovypayments.data.utils;

import com.imobile3.groovypayments.data.entities.CartPaymentEntity;
import com.imobile3.groovypayments.data.enums.GroovyPaymentType;
import com.imobile3.groovypayments.data.model.Cart;

import androidx.annotation.NonNull;

import java.util.Date;

/**
 * @author Kevin Schanz
 */
public final class CartPaymentBuilder {

    private CartPaymentBuilder() {
    }

    @NonNull
    public static CartPaymentEntity from(
            @NonNull Cart cart,
            @NonNull GroovyPaymentType paymentType,
            long approvedAmount,
            @NonNull String gatewayMessage) {
        CartPaymentEntity result = new CartPaymentEntity();
        result.setId(System.currentTimeMillis());
        result.setCartId(cart.getId());
        result.setDateCreated(new Date());
        result.setPaymentTypeName(paymentType.name());
        result.setApprovedAmount(approvedAmount);
        result.setGatewayMessage(gatewayMessage);
        return result;
    }
}
