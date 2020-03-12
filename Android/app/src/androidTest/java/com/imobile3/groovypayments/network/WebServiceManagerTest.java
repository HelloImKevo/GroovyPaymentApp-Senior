package com.imobile3.groovypayments.network;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.imobile3.groovypayments.BuildConfig;
import com.imobile3.groovypayments.logging.LogHelper;
import com.stripe.Stripe;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import androidx.test.runner.AndroidJUnitRunner;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Level;

public class WebServiceManagerTest extends AndroidJUnitRunner {

    private final String TAG = getClass().getSimpleName();

    private String publishableApiKey = BuildConfig.STRIPE_API_CLIENT_KEY;
    private String secretApiKey = BuildConfig.STRIPE_API_SERVER_KEY;

    @Before
    public void setUp() {
        LogHelper.write(Level.CONFIG, TAG, "Setting up android instrumentation test...");
    }

    @After
    public void tearDown() {
        LogHelper.write(Level.CONFIG, TAG, "Tearing down android instrumentation test...");
    }

    // https://github.com/stripe/stripe-java/releases/latest

    /*
    Example:
    {
        "amount": 1099,
        "amount_capturable": 0,
        "amount_received": 0,
        "capture_method": "automatic",
        "charges": {
            "data": [],
            "hasMore": false,
            "object": "list",
            "url": "/v1/charges?payment_intent\u003dpi_1GLdT7K4qYdXjx43qv4bXtnO"
        },
        "client_secret": "pi_1GLdT7K4qYdXjx43qv4bXtnO_secret_zeJpiWMR0BezkhfZoZLKIsbKX",
        "confirmation_method": "automatic",
        "created": 1583967365,
        "currency": "usd",
        "id": "pi_1GLdT7K4qYdXjx43qv4bXtnO",
        "livemode": false,
        "metadata": {},
        "object": "payment_intent",
        "payment_method_options": {
            "card": {
                "request_three_d_secure": "automatic"
            }
        },
        "payment_method_types": [
            "card"
        ],
        "status": "requires_payment_method"
    }
    ClientSecret = pi_1GLdT7K4qYdXjx43qv4bXtnO_secret_zeJpiWMR0BezkhfZoZLKIKXY8
     */
    @Test
    public void testJavaPaymentIntent() throws Exception {
        Stripe.apiKey = secretApiKey;
        Stripe.setAppInfo("Groovy Java Test");

        PaymentIntentCreateParams createParams = new PaymentIntentCreateParams.Builder()
                .setCurrency("usd")
                .setAmount(1099L)
                .build();

        PaymentIntent intent = PaymentIntent.create(createParams);
        LogHelper.write(TAG, "JSON = " + toJson(intent));

        String clientSecret = intent.getClientSecret();
        LogHelper.write(TAG, "ClientSecret = " + clientSecret);
    }

    // https://stripe.com/docs/payments/accept-a-payment#android

    /**
     * Stripe uses a PaymentIntent object to represent your intent to collect payment from
     * a customer, tracking charge attempts and payment state changes throughout the process.
     */
    @Test
    public void testAndroidPaymentIntent() {
        Map<String, Object> params = new HashMap<>();
        params.put("amount", 1000);
        params.put("currency", "usd");
        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");
        params.put("payment_method_types", paymentMethodTypes);
        params.put("receipt_email", "jenny.rosen@example.com");

        String cardNumber = "4111111111111111";
        int expMonth = Calendar.getInstance().get(Calendar.MONTH);
        int expYear = Calendar.getInstance().get(Calendar.YEAR);
        String cvv = "555";

        PaymentMethodCreateParams.Card card = new PaymentMethodCreateParams.Card.Builder()
                .setNumber(cardNumber)
                .setExpiryMonth(expMonth)
                .setExpiryYear(expYear)
                .setCvc(cvv)
                .build();

        PaymentMethodCreateParams createParams = PaymentMethodCreateParams.create(card);

        // Super secret client key
        // Note: This requires Stripe-specific key syntax. We cannot use a Millis timestamp.
        String paymentIntentClientSecret = "abcdefg";
        ConfirmPaymentIntentParams confirmParams = ConfirmPaymentIntentParams
                .createWithPaymentMethodCreateParams(createParams, paymentIntentClientSecret);

        // The Stripe Android SDK requires an Activity / Fragment input argument unfortunately.
    }

    private String toJson(Object o) {
        return new GsonBuilder()
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
                .create()
                .toJson(o);
    }
}
