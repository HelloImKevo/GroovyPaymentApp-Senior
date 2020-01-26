package com.imobile3.groovypayments;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import androidx.annotation.NonNull;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.LogRecord;

import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
        Application.class,
        Context.class,
        Log.class,
        TextUtils.class})
public abstract class BaseUnitTest {

    private MainApplication mApplication;

    @Before
    public void setUp() {
        mockAndroidLog();

        MockHelper.prepareTextUtils();
    }

    @After
    public void tearDown() {
    }

    @NonNull
    protected Application getApplication() {
        if (mApplication == null) {
            mApplication = PowerMockito.mock(MainApplication.class);

            PowerMockito.mockStatic(MainApplication.class);
            PowerMockito.when(MainApplication.getInstance()).thenReturn(mApplication);
            PowerMockito.when(mApplication.getBaseContext()).thenReturn(mApplication);
            PowerMockito.when(mApplication.getApplicationContext()).thenReturn(mApplication);
        }
        return mApplication;
    }

    @NonNull
    protected Context getTargetContext() {
        return getApplication().getApplicationContext();
    }

    private void mockAndroidLog() {
        PowerMockito.mockStatic(Log.class);
        PowerMockito.when(Log.v(anyString(), anyString()))
                .thenAnswer(new Answer<Integer>() {
                    @Override
                    public Integer answer(InvocationOnMock invocation) {
                        return MockLog.v(invocation);
                    }
                });
        PowerMockito.when(Log.d(anyString(), anyString()))
                .thenAnswer(new Answer<Integer>() {
                    @Override
                    public Integer answer(InvocationOnMock invocation) {
                        return MockLog.d(invocation);
                    }
                });
        PowerMockito.when(Log.i(anyString(), anyString()))
                .thenAnswer(new Answer<Integer>() {
                    @Override
                    public Integer answer(InvocationOnMock invocation) {
                        return MockLog.i(invocation);
                    }
                });
        PowerMockito.when(Log.w(anyString(), anyString()))
                .thenAnswer(new Answer<Integer>() {
                    @Override
                    public Integer answer(InvocationOnMock invocation) {
                        return MockLog.w(invocation);
                    }
                });
        PowerMockito.when(Log.e(anyString(), anyString()))
                .thenAnswer(new Answer<Integer>() {
                    @Override
                    public Integer answer(InvocationOnMock invocation) {
                        return MockLog.e(invocation);
                    }
                });
    }

    protected static void assertComparesEqualTo(String expected, BigDecimal actual) {
        assertComparesEqualTo(new BigDecimal(expected), actual);
    }

    /**
     * Convenience method that performs a {@link BigDecimal#compareTo(BigDecimal)} operation
     * in a more readable format than the JUnit methods provide.
     */
    protected static void assertComparesEqualTo(BigDecimal expected, BigDecimal actual) {
        assertThat(actual, Matchers.comparesEqualTo(expected));
    }

    /**
     * Mirrored from: {@code java.util.logging.LogRecord.inferCaller()}
     * <p>
     * Note: This method derives the method name using the context of the throwable's
     * stack. If called using a throwable instantiated from within an anonymous inner
     * class or method implementation, like {@code Thread->run() {}}, this method will
     * return {@code "run()"}.
     * <p>
     * Usage:
     * <pre>
     * void performMagic() {
     *     String methodName = getMethodName(new Throwable());
     *     assertEquals("performMagic()", methodName);
     * }
     * </pre>
     *
     * @return The name of the method inferred from the passed throwable.
     *
     * @see LogRecord
     */
    @NonNull
    protected String getMethodName(@NonNull Throwable throwable) {
        StackTraceElement[] stackTraces = throwable.getStackTrace();
        if (stackTraces.length > 0) {
            String methodName = stackTraces[0].getMethodName();
            if (methodName != null) {
                return methodName + "() ";
            }
        }
        return "METHOD() ";
    }

    /**
     * @param throwable Used only to infer caller.
     */
    protected void logTestStart(@NonNull Throwable throwable) {
        String timestamp = new SimpleDateFormat("MM/dd/YYYY '@' h:mm a").format(new Date());
        System.out.println("" +
                "\n/////////////////////////////////////////////////////////////////" +
                "\n//" +
                "\n// START: " + getMethodName(throwable) + ">> " + timestamp +
                "\n//" +
                "\n/////////////////////////////////////////////////////////////////\n");
    }

    /**
     * Disable all log output.
     */
    protected synchronized void suppressAllLogging() {
        MockLog.sEnabled = false;
    }

    /**
     * Enable all log output.
     */
    protected synchronized void enableAllLogging() {
        MockLog.sEnabled = true;
    }

    private static class MockLog {
        private static boolean sEnabled = true;

        static int v(InvocationOnMock invocation) {
            String tag = (String)invocation.getArguments()[0];
            String msg = (String)invocation.getArguments()[1];
            outputLog("V/", tag, msg);
            return 0;
        }

        static int d(InvocationOnMock invocation) {
            String tag = (String)invocation.getArguments()[0];
            String msg = (String)invocation.getArguments()[1];
            outputLog("D/", tag, msg);
            return 0;
        }

        static int i(InvocationOnMock invocation) {
            String tag = (String)invocation.getArguments()[0];
            String msg = (String)invocation.getArguments()[1];
            outputLog("I/", tag, msg);
            return 0;
        }

        static int w(InvocationOnMock invocation) {
            String tag = (String)invocation.getArguments()[0];
            String msg = (String)invocation.getArguments()[1];
            outputLog("W/", tag, msg);
            return 0;
        }

        static int e(InvocationOnMock invocation) {
            String tag = (String)invocation.getArguments()[0];
            String msg = (String)invocation.getArguments()[1];
            outputLog("E/", tag, msg);
            return 0;
        }

        static void outputLog(String level, String tag, String msg) {
            if (!sEnabled) {
                return;
            }
            System.out.println(level + tag + ": " + msg);
        }
    }
}
