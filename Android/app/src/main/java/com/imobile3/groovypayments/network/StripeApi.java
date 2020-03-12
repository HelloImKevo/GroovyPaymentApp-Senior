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

package com.imobile3.groovypayments.network;

import android.database.Observable;

import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import java.util.HashMap;
import java.util.Map;

// This currently only exists for reference purposes.
// Reference: https://stripe.com/docs/mobile/android/basic#set-up-ephemeral-key

/**
 * @author Kevin Schanz
 */
public interface StripeApi {

    @FormUrlEncoded
    @POST("ephemeral_keys")
    Observable<ResponseBody> createEphemeralKey(@FieldMap HashMap<String, String> apiVersionMap);

    @FormUrlEncoded
    @POST("create_payment_intent")
    Observable<ResponseBody> createPaymentIntent(@FieldMap Map<String, Object> params);
}
