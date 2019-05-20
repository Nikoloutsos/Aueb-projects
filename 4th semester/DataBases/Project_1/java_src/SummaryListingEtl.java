import org.apache.beam.vendor.grpc.v1p13p1.io.grpc.internal.IoUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.HashMap;


/**
 * Updates summary_listing table with the right zipcode data fetched from listing table
 */
public class SummaryListingEtl {
    static HashMap<String, String> hashMap;
    static final String[] HEADERS = "id,listing_url,scrape_id,last_scraped,name,summary,space,description,experiences_offered,neighborhood_overview,notes,transit,access,interaction,house_rules,thumbnail_url,medium_url,picture_url,xl_picture_url,host_id,street,neighborhood,neighbourhood_cleansed,city,state,zipcode,market,smart_location,country_code,country,latitude,longitude,is_location_exact,property_type,room_type,accommodates,bathrooms,bedrooms,beds,bed_type,square_feet,price,weekly_price,monthly_price,security_deposit,cleaning_fee,guests_included,extra_people,minimum_nights,maximum_nights,has_availability,availability_365,number_of_reviews,first_review,last_review,review_scores_rating,review_scores_accuracy,review_scores_cleanliness,review_scores_checkin,review_scores_communication,review_scores_location,review_scores_value,requires_license,license,jurisdiction_names,instant_bookable,cancellation_policy,require_guest_profile_picture,require_guest_phone_verification,reviews_per_month".split(",");
    static final String[] HEADER_MERGED = "id,name,host_id,host_name,zip_code,latitude,longitude,room_type,price,minimum_nights,number_of_reviews,last_review,reviews_per_month,calculated_host_listings_count,availability_365".split(",");

    public static void main(String[] args) {
        try {
            loadHashMap(AirbnbConstants.PATH_WRITE_LISTING);
            updateSummaryListing(AirbnbConstants.PATH_WRITE_SUMMARY_LISTING,
                    AirbnbConstants.PATH_WRITE_SUMMARY_LISTING_RENEW_ZIPCODE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Finish");

    }





    /**
     * It loads the hashmap with listing_id --> zipcode
     */
    private static void loadHashMap(String readFile) throws IOException {
        hashMap = new HashMap<String, String>();
        BufferedReader bf;

        Reader in = new FileReader(readFile);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT
                .withHeader(HEADERS)
                .withFirstRecordAsHeader()
                .parse(in);

        for (CSVRecord record : records) {
            String listing_id = record.get("id");
            String zipcode = record.get("zipcode");
            hashMap.put(listing_id, zipcode);
        }
    }

    /**
     * Updates the summary_listing_merged with the correct zipcode if possible!
     */

    private static void updateSummaryListing(String readFile, String writePath){
        BufferedReader bf = null;
        PrintWriter pr = null;
        try {
            pr = new PrintWriter(new BufferedWriter(new FileWriter(new File(writePath), false)));

            Reader in = new FileReader(readFile);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withHeader(HEADERS)
                    .withFirstRecordAsHeader()
                    .parse(in);


            for (CSVRecord record : records) { //for every line
                StringBuilder sb = new StringBuilder();
                String id = record.get("id");
                if(id.equalsIgnoreCase("10337526")) System.out.println(record.get(0) + "  " + record.get(1) + "   " + record.get(2));

                for (String column : HEADER_MERGED) { //for every column
                    String data = record.get(column);
                    if(column.equalsIgnoreCase("zip_code")){
                        if(hashMap.containsKey(id)){
                            sb.append(hashMap.get(record.get("id")) + ",");
                        }else{
                            sb.append("" + ","); //When Sum_listing is not in Listing table
                        }
                    }else{
                        if(column.equalsIgnoreCase("name") || column.equalsIgnoreCase("host_name")
                        || column.equalsIgnoreCase("price")){
                            sb.append("\"" + data.replaceAll("\"", "") + "\"" + ","); //useful for names that contain commas
                        }
                        else{
                            sb.append(data + ",");
                        }


                    }
                }
                pr.println(sb.substring(0, sb.length() - 2));
            }


        } catch (IOException e) {
            if(pr != null) pr.close();
        }


    }

}
