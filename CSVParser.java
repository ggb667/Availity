
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
    int version;
    String insuranceCompany;

    public String toString() {
        return "\"" + userId + "\",\""
                    + firstName + "\",\"" 
                    + lastName + "\"," 
                    + version + ",\""
                    + insuranceCompany +"\"";
    }
}

public class CSVParser {    

    public static final String removeQuotes(String string) {
        if (string.length() >= 2 && string.charAt(0) == '"' && string.charAt(string.length() - 1) == '"') {
           string = string.substring(1, string.length() - 1);
        }
        return string;
    }

    public static void main(String[] args) throws Exception {

        Map <String, AvilityData> m = new TreeMap<String, AvilityData>();
        for(String filepath: args) {
            String avilityCSVDataLine;
            try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
                while((avilityCSVDataLine = br.readLine()) !=null ) {
                    String[] data = avilityCSVDataLine.split(",");
                    AvilityData a = new AvilityData();
                    if(data[3].indexOf("Version")!=-1){
                        continue;
                    }
                    a.userId=removeQuotes(data[0]);
                    a.firstName=removeQuotes(data[1]);
                    a.lastName=removeQuotes(data[2]);
                    a.version=Integer.parseInt(removeQuotes(data[3]));
                    a.insuranceCompany=removeQuotes(data[4]);
                    if(m.containsKey(a.userId)) {
                        if(m.get(a.userId).version < a.version) {
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
                        bw.write(v.toString()+"\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }

}
