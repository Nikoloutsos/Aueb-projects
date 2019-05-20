
import java.io.*;
import java.util.HashMap;


/**
 * Remove mistakes in files given for Amenity.csv files...
 * Also, create ZillowMerging ready to be published csv for our database!
 */
public class UtilAmenityAirbnb {
    static HashMap<Integer, String> hashMap = new HashMap<Integer, String>();
    static HashMap<String, Integer> hashMap2 = new HashMap<String, Integer>();

    public static void main(String[] args) {
        String basePath = AirbnbConstants.BASE_PATH;

        doEtlOnAmenities(basePath + AirbnbConstants.PATH_AMENITY_AUSTIN,
                basePath + AirbnbConstants.PATH_LISTING_AMENITY_CONNECTION_AUSTIN,
                AirbnbConstants.PATH_WRITE_AMENITY_AUSTIN);
        doEtlOnAmenities(basePath + AirbnbConstants.PATH_AMENITY_BOSTON,
                basePath + AirbnbConstants.PATH_LISTING_AMENITY_CONNECTION_BOSTON,
                AirbnbConstants.PATH_WRITE_AMENITY_BOSTON);
        doEtlOnAmenities(basePath + AirbnbConstants.PATH_AMENITY_DENVER,
                basePath + AirbnbConstants.PATH_LISTING_AMENITY_CONNECTION_DENVER,
                AirbnbConstants.PATH_WRITE_AMENITY_DENVER);
        doEtlOnAmenities(basePath + AirbnbConstants.PATH_AMENITY_PORTLAND,
                basePath + AirbnbConstants.PATH_LISTING_AMENITY_CONNECTION_PORTLAND,
                AirbnbConstants.PATH_WRITE_AMENITY_PORTLAND);


        //Make sure headers are removed!
        EtlMainMergeAirbnb.appendUsingPrintWriter(AirbnbConstants.PATH_WRITE_AMENITY_LAST_STEP,
                AirbnbConstants.PATH_WRITE_AMENITY_AUSTIN);
        EtlMainMergeAirbnb.appendUsingPrintWriter(AirbnbConstants.PATH_WRITE_AMENITY_LAST_STEP,
                AirbnbConstants.PATH_WRITE_AMENITY_BOSTON);
        EtlMainMergeAirbnb.appendUsingPrintWriter(AirbnbConstants.PATH_WRITE_AMENITY_LAST_STEP,
                AirbnbConstants.PATH_WRITE_AMENITY_DENVER);
        EtlMainMergeAirbnb.appendUsingPrintWriter(AirbnbConstants.PATH_WRITE_AMENITY_LAST_STEP,
                AirbnbConstants.PATH_WRITE_AMENITY_PORTLAND);

        doEtlAmenityGlobalId(AirbnbConstants.PATH_WRITE_AMENITY_LAST_STEP,
                AirbnbConstants.PATH_WRITE_LISTING_AMENITY_CONNECTION);






    }

    public static void loadHashMap(String filePath) {
        hashMap = new HashMap<Integer, String>();
        BufferedReader br = null;
        int index = 0;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
            String strLine;
            while ((strLine = br.readLine()) != null)   {
                if(index++ == 0) continue;

                String[] items = strLine.split(",");
                hashMap.put(Integer.parseInt(items[0]), items[1].toLowerCase());
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

    public static void loadHashMapGlobal(String filePath) {
        hashMap2 = new HashMap<String, Integer>();
        BufferedReader br = null;
        int index = 0;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
            String strLine;
            while ((strLine = br.readLine()) != null)   {
                String[] items = strLine.split(",");
                String amenity_type = "";
                try {
                    amenity_type = items[1].toLowerCase();
                } catch (ArrayIndexOutOfBoundsException e) {

                }

                hashMap2.put(amenity_type, Integer.parseInt(items[0]));
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

    private static void doEtlOnAmenities(String hashMapFile, String readFile, String writeFile) {
        loadHashMap(hashMapFile);
        BufferedReader br = null;
        PrintWriter pr = null;
        try {
            int index = 0;
            //Read
            br = new BufferedReader(new InputStreamReader(new FileInputStream(readFile)));
            //Write
            pr = new PrintWriter(new BufferedWriter(new FileWriter(new File(writeFile), false)));

            String strLine;
            while ((strLine = br.readLine()) != null){
                if(index++ == 0){ continue; }
                String[] items = strLine.split(",");
                items[1] = hashMap.get(Integer.parseInt(items[1]));
                pr.println(items[0] + "," + items[1].toLowerCase());
            }

        } catch (Exception e) {
            e.printStackTrace();


        }finally {
            try {
                br.close();
                pr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    private static void doEtlAmenityGlobalId(String readFile, String writeFile) {
        loadHashMapGlobal(AirbnbConstants.PATH_WRITE_AMENITY);
        BufferedReader br = null;
        PrintWriter pr = null;
        try {
            int index = 0;
            //Read
            br = new BufferedReader(new InputStreamReader(new FileInputStream(readFile)));
            //Write
            pr = new PrintWriter(new BufferedWriter(new FileWriter(new File(writeFile), false)));

            String strLine;
            while ((strLine = br.readLine()) != null){
                String[] items = strLine.split(",");
                String id = "35";

                try{
                    id = "" + hashMap2.get(items[1].toLowerCase());
                }catch (Exception e){

                }

                pr.println(items[0] + "," + id);
            }

        } catch (Exception e) {
            e.printStackTrace();


        }finally {
            try {
                br.close();
                pr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
