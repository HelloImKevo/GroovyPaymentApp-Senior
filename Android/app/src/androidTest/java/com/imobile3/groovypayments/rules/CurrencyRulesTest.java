package com.imobile3.groovypayments.rules;

import com.imobile3.groovypayments.logging.LogHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import java.util.Locale;
import java.util.logging.Level;

@RunWith(AndroidJUnit4.class)
public class CurrencyRulesTest {

    private static final String TAG = CurrencyRulesTest.class.getSimpleName();

    private final Locale us = Locale.US;
    private final Locale canada = Locale.CANADA_FRENCH;
    private final Locale spain = new Locale("es", "ES");

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /*
    Locale = en_US / Pennies = 50 / Result = $0.50
    Locale = en_US / Pennies = 750 / Result = $7.50
    Locale = en_US / Pennies = 120075 / Result = $1,200.75
    Locale = fr_CA / Pennies = 50 / Result = 0,50 $
    Locale = fr_CA / Pennies = 750 / Result = 7,50 $
    Locale = fr_CA / Pennies = 120075 / Result = 1 200,75 $
    Locale = es_ES / Pennies = 50 / Result = 0,50 €
    Locale = es_ES / Pennies = 750 / Result = 7,50 €
    Locale = es_ES / Pennies = 120075 / Result = 1.200,75 €
     */
    @Test
    public void getFormattedAmount() {
        Locale locale;
        long pennies;
        String result;

        CurrencyRules rules = new CurrencyRules();

        // United States
        locale = us;
        pennies = 50;
        result = rules.getFormattedAmount(pennies, locale);
        logResult(locale, pennies, result);

        pennies = 750;
        result = rules.getFormattedAmount(pennies, locale);
        logResult(locale, pennies, result);

        pennies = 120075;
        result = rules.getFormattedAmount(pennies, locale);
        logResult(locale, pennies, result);

        // Canada (French)
        locale = canada;
        pennies = 50;
        result = rules.getFormattedAmount(pennies, locale);
        logResult(locale, pennies, result);

        pennies = 750;
        result = rules.getFormattedAmount(pennies, locale);
        logResult(locale, pennies, result);

        pennies = 120075;
        result = rules.getFormattedAmount(pennies, locale);
        logResult(locale, pennies, result);

        // Spain
        locale = spain;
        pennies = 50;
        result = rules.getFormattedAmount(pennies, locale);
        logResult(locale, pennies, result);

        pennies = 750;
        result = rules.getFormattedAmount(pennies, locale);
        logResult(locale, pennies, result);

        pennies = 120075;
        result = rules.getFormattedAmount(pennies, locale);
        logResult(locale, pennies, result);
    }

    private void logResult(Locale locale, long pennies, String result) {
        LogHelper.write(Level.CONFIG, TAG,
                "Locale = " + locale +
                        " / Pennies = " + pennies +
                        " / Result = " + result);
    }
}
