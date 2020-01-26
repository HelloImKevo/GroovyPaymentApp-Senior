package com.imobile3.groovypayments;

import com.imobile3.groovypayments.logging.LogHelper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.logging.Level;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
@SuppressWarnings("FieldCanBeLocal")
public class LoggingTest extends BaseUnitTest {

    private static final String TAG = "LogHelperTest";

    static {
        LogHelper.setEnabled(true);
    }

    private ByteArrayOutputStream mBaos;
    private PrintStream mNewPrintStream;
    private PrintStream mOldPrintStream;

    @Test
    public void testWrite() {
        captureSystemOutput();

        // Test Basic Output Levels (must visually inspect results!)
        LogHelper.write(Level.FINE, TAG, "[VERBOSE] Test 1");
        assertEquals(getLastMessage(), "V/LogHelperTest: [VERBOSE] Test 1");

        LogHelper.write(Level.CONFIG, TAG, "[DEBUG] Test 2");
        assertStartsWith("D", getLastMessage());

        LogHelper.write(Level.INFO, TAG, "[INFO] Test 3");
        assertStartsWith("I", getLastMessage());

        LogHelper.write(Level.WARNING, TAG, "[WARN] Test 4");
        assertStartsWith("W", getLastMessage());

        LogHelper.write(Level.SEVERE, TAG, "[ERROR] Test 5");
        assertStartsWith("E", getLastMessage());

        LogHelper.writeWithTrace(Level.CONFIG, TAG, "Method Trace Test");
        apple();
        banana();
        cherry();
        appleBananaCherry();

        // Should output: LogHelperTest.testWrite() :: Invoked
        LogHelper.invoked(Level.FINE, TAG);
        assertContains(getLastMessage(), "Invoked");

        // Should output: LogHelperTest.testWrite() :: Invoked with (null)
        LogHelper.invoked(Level.FINE, TAG, (Object)null);
        assertContains(getLastMessage(), "Invoked with (null)");

        // Should output: LogHelperTest.cowGoesMoo() :: Invoked
        cowGoesMoo();

        sayWords("Hello", "How", "Are", "You?");
        assertContains(getLastMessage(), "sayWords()");

        LogHelper.invoked(Level.FINE, TAG, "One", "Two", "Three");
        assertContains(getLastMessage(), "One", "Two", "Three");

        LogHelper.invoked(Level.FINE, TAG, 5, "Apples", 10.25f, 3.14d, "Pi");
        LogHelper.invoked(Level.FINE, TAG,
                new Animal("Cat"),
                new Animal("Dog"),
                new BigDecimal("9.99"),
                null,
                "Orange",
                true,
                false,
                "Schfifty-Five",
                null);

        restoreAndDumpSystemOutput();
    }

    // Starts a "capture" session, where all System.out content is routed
    // to our local output stream.
    private void captureSystemOutput() {
        mBaos = new ByteArrayOutputStream();
        mNewPrintStream = new PrintStream(mBaos);
        mOldPrintStream = System.out;
        System.setOut(mNewPrintStream);
    }

    // Ends the "capture" session, and logs all content to the pre-existing
    // System.out print stream.
    private void restoreAndDumpSystemOutput() {
        System.out.flush();
        System.setOut(mOldPrintStream);
        System.out.println(mBaos.toString());
    }

    private String getLastMessage() {
        String[] logMessages = mBaos.toString().split("\n");
        return logMessages[logMessages.length - 1];
    }

    private void assertStartsWith(String expectedStart, String actualString) {
        assertTrue(actualString.startsWith(expectedStart));
    }

    private void assertContains(String actualString, String... elements) {
        for (String element : elements) {
            assertTrue(actualString.contains(element));
        }
    }

    private void appleBananaCherry() {
        apple();
        banana();
        cherry();
    }

    private void apple() {
        LogHelper.writeWithTrace(Level.INFO, "Fruit", "Invoked");
    }

    private void banana() {
        LogHelper.writeWithTrace(Level.INFO, "Fruit", "Invoked");
    }

    private void cherry() {
        LogHelper.writeWithTrace(Level.INFO, "Fruit", "Invoked");
    }

    private void cowGoesMoo() {
        LogHelper.invoked(Level.INFO, "Animal");
    }

    private void sayWords(String... words) {
        LogHelper.invoked(Level.INFO, TAG, (Object[])words);
    }

    private static class Animal {
        private String mName;

        Animal(String name) {
            mName = name;
        }

        @Override
        public String toString() {
            return mName;
        }
    }
}
