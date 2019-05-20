import java.io.*;
import java.util.HashMap;

public class UtilNeiborhoodZipCode {
    static HashMap<String, String> hashMap = new HashMap<String, String>();

    public static void main(String[] args) {
        loadHashMap(AirbnbConstants.PATH_WRITE_NEIGHBOURHOOD);
    }


    public static void loadHashMap(String filePath) {
        hashMap = new HashMap<String, String>();
        BufferedReader br = null;
        int index = 0;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
            String strLine;
            while ((strLine = br.readLine()) != null)   {
                if(index++ == 0) continue; //skip header
                String[] items = strLine.split(",");
                String zipCode = "";
                try {
                    zipCode = items[1].toLowerCase();

                } catch (Exception e) {

                }
                hashMap.put(items[0].toLowerCase(), zipCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                br.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
