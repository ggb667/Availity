package com.avility.app;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    /**
     * Check to make sure the code does what we want for the trivial case.
     */
    @Test
    public void checkLispParensCheckerGoodTests()
    {
        checkLispParensCheckerGood(new String[]{"()","(())","(())()","((()))"});
        assertTrue(true);
    }

    public void checkLispParensCheckerGood(String[] args)
    {
        for(String str: args){
            try {
                PrintStream originalPrintStream = System.out;
                PrintStream originalErrorStream = System.err;
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ByteArrayOutputStream es = new ByteArrayOutputStream();
                System.setOut(new PrintStream(os));
                System.setErr(new PrintStream(es));
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                Future<Void> future = executorService.submit(new Callable<Void>() {
                    public Void call() throws Exception {
                            LispParenthesesChecker.main(new String[]{str});
                        return null;
                    }
                });
                future.get();
                System.setOut(originalPrintStream);
                System.setErr(originalErrorStream);
                assertTrue( es.toString(StandardCharsets.UTF_8).length()==0 );
                assertTrue( os.toString(StandardCharsets.UTF_8).contains(" is good"));
            } catch (Exception e) {
                assertNull( e );
            }
        }
    }
    
    /**
     * Check to make sure the code does what we want when things are not ok.
     */
    @Test
    public void checkLispParensCheckerBadTests()
    {
        checkLispParensCheckerBad(new String[]{")(","(()","(()))","(",")"});
        assertTrue(true);
    }

    public void checkLispParensCheckerBad(String[] args)
    {    
        for(String str: args){
            try {
                PrintStream originalPrintStream = System.out;
                PrintStream originalErrorStream = System.err;
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ByteArrayOutputStream es = new ByteArrayOutputStream();
                System.setOut(new PrintStream(os));
                System.setErr(new PrintStream(es));
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                Future<Void> future = executorService.submit(new Callable<Void>() {
                    public Void call() throws Exception {
                            LispParenthesesChecker.main(new String[]{str});
                        return null;
                    }
                });
                future.get();
                System.setOut(originalPrintStream);
                System.setErr(originalErrorStream);
                assertTrue( es.toString(StandardCharsets.UTF_8).length()==0 );
                assertTrue( os.toString(StandardCharsets.UTF_8).contains(" is bad"));
            } catch (Exception e) {
                assertNull( e );
            }
        }
    }

    /**
     * Check to make sure the code does what we want when things are not ok.
     */
    @Test
    public void checkLispParensCheckerGarbage()
    {
        try {
            PrintStream originalPrintStream = System.out;
            PrintStream originalErrorStream = System.err;
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ByteArrayOutputStream es = new ByteArrayOutputStream();
            System.setOut(new PrintStream(os));
            System.setErr(new PrintStream(es));
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            Future<Void> future = executorService.submit(new Callable<Void>() {
                public Void call() throws Exception {
                        LispParenthesesChecker.main(new String[]{});
                    return null;
                }
            });
            future.get();
            System.setOut(originalPrintStream);
            System.setErr(originalErrorStream);
            assertTrue( es.toString(StandardCharsets.UTF_8).length()==0 );
            assertFalse( os.toString(StandardCharsets.UTF_8).contains(" is good"));
        } catch (Exception e) {
            assertNotNull( e );
        }
    }
}
