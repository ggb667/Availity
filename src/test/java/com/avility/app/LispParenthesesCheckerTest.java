package com.avility.app;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

/**
 * Unit test for LispParenthesessChecker.
 */
public class LispParenthesesCheckerTest {

    /**
     * Check to make sure the code does what we want for the trivial case.
     */
    @Test
    public void checkLispParensCheckerGoodTests() {
        checkLispParensCheckerGood(new String[] { "()", "(())", "(())()", "((()))" });
        assertTrue(true);
    }

    /**
     * Check to make sure the code does what we want for the trivial case.
     */
    @Test
    public void checkLispParensCheckerMoreGoodTests() {
        checkLispParensCheckerGood(new String[] { "((()((((()()()((()))))))))()", "((()((()))()))" });
        assertTrue(true);
    }

    public void checkLispParensCheckerGood(final String[] args) {
        for (final String str : args) {
            try {
                final PrintStream originalPrintStream = System.out;
                final PrintStream originalErrorStream = System.err;
                final ByteArrayOutputStream os = new ByteArrayOutputStream();
                final ByteArrayOutputStream es = new ByteArrayOutputStream();
                System.setOut(new PrintStream(os));
                System.setErr(new PrintStream(es));
                final ExecutorService executorService = Executors.newSingleThreadExecutor();
                final Future<Void> future = executorService.submit(new Callable<Void>() {
                    public Void call() throws Exception {
                        LispParenthesesChecker.main(new String[] { str });
                        return null;
                    }
                });
                future.get();
                System.setOut(originalPrintStream);
                System.setErr(originalErrorStream);
                assertTrue(es.toString(UTF_8).length() == 0);
                assertTrue(os.toString(UTF_8).contains(" is good"));
            } catch (final Exception e) {
                assertNull(e);
            }
        }
    }

    /**
     * Check to make sure the code does what we want when things are not ok.
     */
    @Test
    public void checkLispParensCheckerBadTests() {
        checkLispParensCheckerBad(new String[] { "(()", "(()))", "(", ")" });
        assertTrue(true);
    }

    /**
     * Check to make sure the code does what we want when things are not ok.
     */
    @Test
    public void checkLispParensCheckerMoreBadTests() {
        checkLispParensCheckerBad(new String[] { ")(", "))((" });
        assertTrue(true);
    }

    /** 
     * @param args Checks the lisp for matching braces, short circuits if ever right occurs before left.
     */
    public void checkLispParensCheckerBad(final String[] args) {
        for (final String str : args) {
            try {
                final PrintStream originalPrintStream = System.out;
                final PrintStream originalErrorStream = System.err;
                final ByteArrayOutputStream os = new ByteArrayOutputStream();
                final ByteArrayOutputStream es = new ByteArrayOutputStream();
                System.setOut(new PrintStream(os));
                System.setErr(new PrintStream(es));
                final ExecutorService executorService = Executors.newSingleThreadExecutor();
                final Future<Void> future = executorService.submit(new Callable<Void>() {
                    public Void call() throws Exception {
                        LispParenthesesChecker.main(new String[] { str });
                        return null;
                    }
                });
                future.get();
                System.setOut(originalPrintStream);
                System.setErr(originalErrorStream);
                assertTrue(es.toString(UTF_8).length() == 0);
                assertTrue(os.toString(UTF_8).contains(" is bad"));
            } catch (final Exception e) {
                assertNull(e);
            }
        }
    }

    /**
     * Check to make sure the code does what we want when things are not ok.
     */
    @Test
    public void checkLispParensCheckerGarbage() {
        try {
            final PrintStream originalPrintStream = System.out;
            final PrintStream originalErrorStream = System.err;
            final ByteArrayOutputStream os = new ByteArrayOutputStream();
            final ByteArrayOutputStream es = new ByteArrayOutputStream();
            System.setOut(new PrintStream(os));
            System.setErr(new PrintStream(es));
            final ExecutorService executorService = Executors.newSingleThreadExecutor();
            final Future<Void> future = executorService.submit(new Callable<Void>() {
                public Void call() throws Exception {
                    LispParenthesesChecker.main(new String[] {});
                    return null;
                }
            });
            future.get();
            System.setOut(originalPrintStream);
            System.setErr(originalErrorStream);
            assertTrue(es.toString(UTF_8).length() == 0);
            assertFalse(os.toString(UTF_8).contains(" is good"));
        } catch (final Exception e) {
            assertNotNull(e);
        }
    }
}
