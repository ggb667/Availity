package com.avility.app;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CSVParserTest {

    @Test
    public void testMainNoInputFiles() {
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
            assertTrue(es.toString(StandardCharsets.UTF_8).length() == 0);// There should be no output.
            assertTrue(os.toString(StandardCharsets.UTF_8).length() == 0);// There should be no output.
            assertFalse(new File("output.csv").exists());
        } catch (final Exception e) {// There should be no exceptions
            assertNull(e);
        }
    }

    @Test
    public void testMain3InputFiles() {
        final String testFilesPathRelativeToExecutationLocation = "./src/test/java/com/avility/app/";
//      System.out.println(new File("TestFile.txt").getAbsolutePath());
        assertTrue(new File("./src/test/java/com/avility/app/AvilityData.csv").exists());
        assertTrue(new File("./src/test/java/com/avility/app/Data.csv").exists());
        assertTrue(new File("./src/test/java/com/avility/app/MyData.csv").exists());

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
                    LispParenthesesChecker.main(new String[] {
                        testFilesPathRelativeToExecutationLocation + "AvilityData.csv",
                        testFilesPathRelativeToExecutationLocation + "Data.csv",
                        testFilesPathRelativeToExecutationLocation + "MyData.csv"});
                    return null;
                }
            });
            future.get();
            System.setOut(originalPrintStream);
            System.setErr(originalErrorStream);
//          assertTrue(os.toString(StandardCharsets.UTF_8).length() == 0);// There should be no output.
            System.out.println(os.toString(StandardCharsets.UTF_8));
            assertTrue(es.toString(StandardCharsets.UTF_8).length() == 0);// There should be no output.
//          assertTrue(new File(testFilesPathRelativeToExecutationLocation+"output.csv").exists());
        } catch (final Exception e) {// There should be no exceptions
            assertNull(e);
        }
    }

    @Test
    public void testRemoveQuotes() {
        assertTrue("SomeDataWithQuotes".equals(CSVParser.removeQuotes("\"SomeDataWithQuotes\"")));
        assertTrue("SomeDataWithQuotes".equals(CSVParser.removeQuotes("SomeDataWithQuotes")));
    }
}
