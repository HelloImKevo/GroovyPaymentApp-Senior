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
