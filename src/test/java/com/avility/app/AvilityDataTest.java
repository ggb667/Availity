package com.avility.app;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class AvilityDataTest {
    @Test
    public void testToString() {
        String[] data = {"\"UserId\"","\"First\"","\"Last\"","123","\"InsuranceCompany\""};
        AvilityData a = new AvilityData();
        a.userId=CSVParser.removeQuotes(data[0]);
        a.firstName=CSVParser.removeQuotes(data[1]);
        a.lastName=CSVParser.removeQuotes(data[2]);
        a.version=Integer.parseInt(CSVParser.removeQuotes(data[3]));
        a.insuranceCompany=CSVParser.removeQuotes(data[4]);
        assertTrue("UserId".equals(a.userId));
        assertTrue("First".equals(a.firstName));
        assertTrue("Last".equals(a.lastName));
        assertTrue(123 == a.version);
        assertTrue("InsuranceCompany".equals(a.insuranceCompany));
    }
}
