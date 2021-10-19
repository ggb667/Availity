package com.avility.app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

class AvilityData {
    String userId;
    String firstName;
    String lastName;
    int    version;
    String insuranceCompany;
    
    /** 
     * @return String Utility method for exporting data.
     */
    public String toString() {
        return "\"" + userId           + "\"" + ","
             + "\"" + firstName        + "\"" + ","
             + "\"" + lastName         + "\"" + ","
             +        version          + ","
             + "\"" + insuranceCompany + "\"";
    }
}

/** 
 * This code assumes the data is already pretty sanitized, if not it would need a lot more checks etc.
 */
public class CSVParser {

    /** 
    * Utility method to remove leading and ending quotes from a string.
    * @param String The string to remove quotes from, assumes string is not null.
    * @return String with quote characters removed.
    */
    public static final String removeQuotes(String string) {
        if (string.length() >= 2 && string.charAt(0) == '"' && string.charAt(string.length() - 1) == '"') {
            string = string.substring(1, string.length() - 1);
        }
        return string;
    }

    /** 
     * Entry point for main application.  Feed it valid filepath strings.  
     * No checking of any type for good data, invalid filepaths, etc.
     * Emits "output.csv", which contains all of the unique data elements with
     * highest version only sorted by userId (by virtue of the fact is backed by
     * a tree map with userId as the key). 
     * @param args A list of filepaths. 
     */
    public static void main(String[] args) throws Exception {
        Map<String, AvilityData> m = new TreeMap<String, AvilityData>();
        for (String filepath : args) {
            String avilityCSVDataLine;
            try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
                while ((avilityCSVDataLine = br.readLine()) != null) {
                    String[] data = avilityCSVDataLine.split(",");
                    AvilityData a = new AvilityData();
                    if (data[3].indexOf("Version") != -1) { //Skip header line if present
                        continue;
                    }
                    a.userId = removeQuotes(data[0]);
                    a.firstName = removeQuotes(data[1]);
                    a.lastName = removeQuotes(data[2]);
                    a.version = Integer.parseInt(removeQuotes(data[3]));
                    a.insuranceCompany = removeQuotes(data[4]);
                    if (m.containsKey(a.userId)) {
                        if (m.get(a.userId).version < a.version) {
                            m.put(a.userId, a);
                        }
                    } else {
                        m.put(a.userId, a);
                    }
                }
            }
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("output.csv"))) {
                m.forEach((k, v) -> {
                    try {
                        bw.write(v.toString() + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }

}
