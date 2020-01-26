package com.imobile3.groovypayments;

import android.text.TextUtils;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;

import static org.mockito.ArgumentMatchers.any;

@SuppressWarnings("WeakerAccess")
public class MockHelper {

    public static void prepareTextUtils() {
        PowerMockito.mockStatic(TextUtils.class);
        PowerMockito.when(TextUtils.isEmpty(any(CharSequence.class)))
                .thenAnswer(new Answer<Boolean>() {
                    @Override
                    public Boolean answer(InvocationOnMock invocation) {
                        // Emulate the Android SDKs logic for this frequently used helper method
                        CharSequence str = (CharSequence)invocation.getArguments()[0];
                        return str == null || str.length() == 0;
                    }
                });
        PowerMockito.when(TextUtils.isEmpty(any(String.class)))
                .thenAnswer(new Answer<Boolean>() {
                    @Override
                    public Boolean answer(InvocationOnMock invocation) {
                        // Emulate the Android SDKs logic for this frequently used helper method
                        String str = (String)invocation.getArguments()[0];
                        return str == null || str.length() == 0;
                    }
                });
        // PowerMock requires handling for null parameters (because the dynamic data type
        // cannot be evaluated at runtime)
        PowerMockito.when(TextUtils.isEmpty(null))
                .thenAnswer(new Answer<Boolean>() {
                    @Override
                    public Boolean answer(InvocationOnMock invocation) {
                        return true;
                    }
                });
    }
}
