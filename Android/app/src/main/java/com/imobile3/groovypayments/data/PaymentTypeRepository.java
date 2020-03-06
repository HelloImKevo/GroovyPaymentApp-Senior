package com.imobile3.groovypayments.data;

import android.content.Context;

import com.imobile3.groovypayments.R;
import com.imobile3.groovypayments.data.enums.GroovyColor;
import com.imobile3.groovypayments.data.enums.GroovyIcon;
import com.imobile3.groovypayments.data.enums.GroovyPaymentType;
import com.imobile3.groovypayments.data.model.PaymentType;

import java.util.ArrayList;
import java.util.List;

/**
 * A repository holds a reference to different kinds of data sources
 * (web services, local database or cached data).
 */
public class PaymentTypeRepository {

    private static volatile PaymentTypeRepository sInstance;

    private PaymentTypeRepository() {
    }

    public static synchronized PaymentTypeRepository getInstance() {
        if (sInstance == null) {
            sInstance = new PaymentTypeRepository();
        }
        return sInstance;
    }

    public List<PaymentType> getPaymentTypes(Context context) {
        PaymentType result;
        List<PaymentType> results = new ArrayList<>();

        result = new PaymentType();
        result.setName(context.getString(R.string.cash));
        result.setType(GroovyPaymentType.Cash);
        result.setIcon(GroovyIcon.MoneyStack);
        result.setColor(GroovyColor.Green);
        results.add(result);

        result = new PaymentType();
        result.setName(context.getString(R.string.credit));
        result.setType(GroovyPaymentType.Credit);
        result.setIcon(GroovyIcon.BagCoins);
        result.setColor(GroovyColor.Blue);
        results.add(result);

        return results;
    }
}
