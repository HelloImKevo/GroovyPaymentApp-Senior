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

package com.imobile3.groovypayments.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import androidx.annotation.NonNull;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author Kevin Schanz
 */
@SuppressWarnings("Convert2Lambda") // Readability
public final class JsonHelper {

    @NonNull
    private static final Gson sGson;

    static {
        sGson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new JsonSerializer<Date>() {
                    @Override
                    public JsonElement serialize(Date src, Type typeOfSrc,
                            JsonSerializationContext context) {
                        SimpleDateFormat dateFormatter =
                                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
                        dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                        return new JsonPrimitive(dateFormatter.format(src));
                    }
                })
                .setPrettyPrinting()
                .create();
    }

    private JsonHelper() {
    }

    public static String toPrettyJson(Object o) {
        return sGson.toJson(o);
    }
}
