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

package com.imobile3.groovypayments.data.dao;

import com.imobile3.groovypayments.data.entities.CartPaymentEntity;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * @author Kevin Schanz
 */
@Dao
public interface CartPaymentDao {

    @Query("SELECT * FROM cart_payment")
    List<CartPaymentEntity> getCartPayments();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCartPayments(CartPaymentEntity... values);

    @Update
    void updateCartPayments(CartPaymentEntity... values);

    @Delete
    void deleteCartPayments(CartPaymentEntity... values);
}
