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

package com.imobile3.groovypayments.data.entities;

import com.imobile3.groovypayments.data.DateTypeConverter;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

/**
 * @author Kevin Schanz
 */
@Entity(tableName = "cart_payment",
        indices = {
                @Index("cart_payment_id"),
                @Index("cart_id")})
public class CartPaymentEntity {

    @PrimaryKey
    @ColumnInfo(name = "cart_payment_id")
    private long mId;

    /**
     * Foreign key to the cart table.
     */
    @ForeignKey(
            entity = CartEntity.class,
            parentColumns = "cart_id",
            childColumns = "cart_id")
    @ColumnInfo(name = "cart_id")
    private long mCartId;

    @ColumnInfo(name = "date_created")
    @TypeConverters(DateTypeConverter.class)
    private Date mDateCreated;

    /**
     * See: GroovyPaymentType enum.
     */
    @ColumnInfo(name = "payment_type_name")
    private String mPaymentTypeName;

    /**
     * The approved transaction amount (in pennies) that we charged the customer.
     */
    @ColumnInfo(name = "approved_amount")
    private long mApprovedAmount;

    /**
     * Response message from the gateway.
     * <p>
     * Stripe example: {@code "Status: Succeeded"}
     */
    @ColumnInfo(name = "gateway_message")
    private String mGatewayMessage;

    public CartPaymentEntity() {
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public long getCartId() {
        return mCartId;
    }

    public void setCartId(long cartId) {
        mCartId = cartId;
    }

    public Date getDateCreated() {
        return mDateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        mDateCreated = dateCreated;
    }

    public String getPaymentTypeName() {
        return mPaymentTypeName;
    }

    public void setPaymentTypeName(String paymentTypeName) {
        mPaymentTypeName = paymentTypeName;
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
