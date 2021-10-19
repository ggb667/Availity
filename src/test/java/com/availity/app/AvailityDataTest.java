package com.availity.app;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

import com.availity.app.AvailityData;
import com.availity.app.CSVParser;

public class AvailityDataTest {
    @Test
    public void testToString() {
        String[] data = {"\"UserId\"","\"First\"","\"Last\"","123","\"InsuranceCompany\""};
        AvailityData a = new AvailityData();
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
