package com.availity.app;

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
        String[] args = new String[] {};
        testMainUtil(args);
        assertFalse(new File("output.csv").exists());
    }

    final String testFilesPathRelativeToExecutationLocation = "./src/test/java/com/availity/app/";

    @Test
    public void testMain1InputFile() {
        String[] args = new String[] {
            testFilesPathRelativeToExecutationLocation + "AvailityData.csv"
        };
        testMainUtil(args);
        assertTrue(new File("output.csv").exists());
    }

    @Test
    public void testMain3InputFiles() {
        String[] args = new String[] {
            testFilesPathRelativeToExecutationLocation + "AvailityData.csv",
            testFilesPathRelativeToExecutationLocation + "Data.csv",
            testFilesPathRelativeToExecutationLocation + "MyData.csv"
        };
        testMainUtil(args);
        assertTrue(new File("output.csv").exists());
    }

    public void testMainUtil(String[] args) {            
        for (final String str : args) {
            assertTrue(new File(str).exists());
        }
        File f = new File("output.csv");
        if(f.exists()){
            f.delete();
        }
        assertFalse(new File("output.csv").exists());
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
                    CSVParser.main(args);
                    return null;
                }
            });
            future.get();
            System.setOut(originalPrintStream);
            System.setErr(originalErrorStream);
            assertTrue(os.toString(StandardCharsets.UTF_8).length() == 0);// There should be no output.
            assertTrue(es.toString(StandardCharsets.UTF_8).length() == 0);// There should be no output.
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
