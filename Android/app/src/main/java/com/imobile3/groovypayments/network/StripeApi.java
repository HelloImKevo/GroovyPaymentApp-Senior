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
public interface StripeApi {

    @FormUrlEncoded
    @POST("ephemeral_keys")
    Observable<ResponseBody> createEphemeralKey(@FieldMap HashMap<String, String> apiVersionMap);

    @FormUrlEncoded
    @POST("create_payment_intent")
    Observable<ResponseBody> createPaymentIntent(@FieldMap Map<String, Object> params);
}
