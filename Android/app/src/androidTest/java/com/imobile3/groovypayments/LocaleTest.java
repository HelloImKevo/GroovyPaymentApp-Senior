package com.imobile3.groovypayments;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import com.imobile3.groovypayments.logging.LogHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.annotation.NonNull;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import java.util.Locale;
import java.util.logging.Level;

@RunWith(AndroidJUnit4.class)
public class LocaleTest {

    private static final String TAG = LocaleTest.class.getSimpleName();

    private Context mContext;

    @Before
    public void setUp() {
        mContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void spanishTest() {
        String english, spanish;
        Resources englishRes = getLocalizedResources(Locale.US);
        Resources spanishRes = getLocalizedResources(new Locale("es"));

        english = englishRes.getString(R.string.common_cancel);
        spanish = spanishRes.getString(R.string.common_cancel);
        logEnglishSpanish(1, english, spanish);

        english = englishRes.getString(R.string.order_entry_description);
        spanish = spanishRes.getString(R.string.order_entry_description);
        logEnglishSpanish(2, english, spanish);

        english = englishRes.getString(R.string.main_dashboard_subtitle);
        spanish = spanishRes.getString(R.string.main_dashboard_subtitle);
        logEnglishSpanish(3, english, spanish);

        english = englishRes.getString(R.string.checkout_subtitle);
        spanish = spanishRes.getString(R.string.checkout_subtitle);
        logEnglishSpanish(4, english, spanish);
    }

    @Test
    public void frenchTest() {
        String english, french;
        Resources englishRes = getLocalizedResources(Locale.US);
        Resources frenchRes = getLocalizedResources(new Locale("fr", "CA"));

        english = englishRes.getString(R.string.common_cancel);
        french = frenchRes.getString(R.string.common_cancel);
        logEnglishFrench(1, english, french);

        english = englishRes.getString(R.string.order_entry_description);
        french = frenchRes.getString(R.string.order_entry_description);
        logEnglishFrench(2, english, french);

        english = englishRes.getString(R.string.main_dashboard_subtitle);
        french = frenchRes.getString(R.string.main_dashboard_subtitle);
        logEnglishFrench(3, english, french);

        english = englishRes.getString(R.string.checkout_subtitle);
        french = frenchRes.getString(R.string.checkout_subtitle);
        logEnglishFrench(4, english, french);
    }

    private void logEnglishSpanish(int index, String english, String spanish) {
        LogHelper.write(Level.CONFIG, TAG, "[" + index +
                "] English = '" + english + "' | Spanish = '" + spanish + "'");
    }

    private void logEnglishFrench(int index, String english, String french) {
        LogHelper.write(Level.CONFIG, TAG, "[" + index +
                "] English = '" + english + "' | French = '" + french + "'");
    }

    @NonNull
    private Resources getLocalizedResources(Locale desiredLocale) {
        Configuration configuration = mContext.getResources().getConfiguration();
        configuration = new Configuration(configuration);
        configuration.setLocale(desiredLocale);
        Context localizedContext = mContext.createConfigurationContext(configuration);
        return localizedContext.getResources();
    }
}
