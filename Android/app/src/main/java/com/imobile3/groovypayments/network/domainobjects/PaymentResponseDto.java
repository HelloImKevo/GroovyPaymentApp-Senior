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

package com.imobile3.groovypayments.network.domainobjects;

/**
 * @author Kevin Schanz
 */
public class PaymentResponseDto {

    private long mApprovedAmount;
    private String mGatewayMessage;

    public PaymentResponseDto() {
    }

    public long getApprovedAmount() {
        return mApprovedAmount;
    }

    public void setApprovedAmount(long approvedAmount) {
        mApprovedAmount = approvedAmount;
    }

    public String getGatewayMessage() {
        return mGatewayMessage;
    }

    public void setGatewayMessage(String gatewayMessage) {
        mGatewayMessage = gatewayMessage;
    }
}
