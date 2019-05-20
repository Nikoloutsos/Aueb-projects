import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.Flatten;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionList;

import java.io.*;


/**
 * Merge all csvS in one file.
 */
public class EtlMainMergeAirbnb {

    public static void main(String[] args) {

        String base_path = AirbnbConstants.BASE_PATH;
        //Merge host
//        appendUsingPrintWriter(AirbnbConstants.PATH_WRITE_HOST,
//                base_path + AirbnbConstants.PATH_HOST_AUSTIN);
//        appendUsingPrintWriter(AirbnbConstants.PATH_WRITE_HOST,
//                base_path + AirbnbConstants.PATH_HOST_DENVER);
//        appendUsingPrintWriter(AirbnbConstants.PATH_WRITE_HOST,
//                base_path + AirbnbConstants.PATH_HOST_PORTLAND);
//        appendUsingPrintWriter(AirbnbConstants.PATH_WRITE_HOST,
//                base_path + AirbnbConstants.PATH_HOST_BOSTON);

        //Merge Listing
        appendUsingPrintWriter(AirbnbConstants.PATH_WRITE_LISTING,
                base_path + AirbnbConstants.PATH_LISTING_AUSTIN);
        appendUsingPrintWriter(AirbnbConstants.PATH_WRITE_LISTING,
                base_path + AirbnbConstants.PATH_LISTING_DENVER);
        appendUsingPrintWriter(AirbnbConstants.PATH_WRITE_LISTING,
                base_path + AirbnbConstants.PATH_LISTING_PORTLAND);
        appendUsingPrintWriter(AirbnbConstants.PATH_WRITE_LISTING,
                base_path + AirbnbConstants.PATH_LISTING_BOSTON);

//         // Merge Review
//        appendUsingPrintWriter(AirbnbConstants.PATH_WRITE_REVIEW,
//                base_path + AirbnbConstants.PATH_REVIEW_AUSTIN);
//        appendUsingPrintWriter(AirbnbConstants.PATH_WRITE_REVIEW,
//                base_path + AirbnbConstants.PATH_REVIEW_DENVER);
//        appendUsingPrintWriter(AirbnbConstants.PATH_WRITE_REVIEW,
//                base_path + AirbnbConstants.PATH_REVIEW_PORTLAND);
//        appendUsingPrintWriter(AirbnbConstants.PATH_WRITE_REVIEW,
//                base_path + AirbnbConstants.PATH_REVIEW_BOSTON);

//        appendUsingPrintWriter(AirbnbConstants.PATH_WRITE_SUMMARY_LISTING,
//                base_path + AirbnbConstants.PATH_SUMMARY_LISTING_AUSTIN);
//        appendUsingPrintWriter(AirbnbConstants.PATH_WRITE_SUMMARY_LISTING,
//                base_path + AirbnbConstants.PATH_SUMMARY_LISTING_DENVER);
//        appendUsingPrintWriter(AirbnbConstants.PATH_WRITE_SUMMARY_LISTING,
//                base_path + AirbnbConstants.PATH_SUMMARY_LISTING_PORTLAND);
//        appendUsingPrintWriter(AirbnbConstants.PATH_WRITE_SUMMARY_LISTING,
//                base_path + AirbnbConstants.PATH_SUMMARY_LISTING_BOSTON);

//        appendUsingPrintWriter(AirbnbConstants.PATH_WRITE_SUMMARY_REVIEW,
//                base_path + AirbnbConstants.PATH_SUMMARY_REVIEW_AUSTIN);
//        appendUsingPrintWriter(AirbnbConstants.PATH_WRITE_SUMMARY_REVIEW,
//                base_path + AirbnbConstants.PATH_SUMMARY_REVIEW_DENVER);
//        appendUsingPrintWriter(AirbnbConstants.PATH_WRITE_SUMMARY_REVIEW,
//                base_path + AirbnbConstants.PATH_SUMMARY_REVIEW_PORTLAND);
//        appendUsingPrintWriter(AirbnbConstants.PATH_WRITE_SUMMARY_REVIEW,
//                base_path + AirbnbConstants.PATH_SUMMARY_REVIEW_BOSTON);
//
//
//                appendUsingPrintWriter(AirbnbConstants.PATH_WRITE_CALENDAR,
//                base_path + AirbnbConstants.PATH_CALENDAR_AUSTIN);
//        appendUsingPrintWriter(AirbnbConstants.PATH_WRITE_CALENDAR,
//                base_path + AirbnbConstants.PATH_CALENDAR_BOSTON);
//        appendUsingPrintWriter(AirbnbConstants.PATH_WRITE_CALENDAR,
//                base_path + AirbnbConstants.PATH_CALENDAR_DENVER);
//        appendUsingPrintWriter(AirbnbConstants.PATH_WRITE_CALENDAR,
//                base_path + AirbnbConstants.PATH_CALENDAR_PORTLAND);
//
//        appendUsingPrintWriter(AirbnbConstants.PATH_WRITE_SUMMARY_CALENDAR,
//                base_path + AirbnbConstants.PATH_SUMMARY_CALENDAR_AUSTIN);
//        appendUsingPrintWriter(AirbnbConstants.PATH_WRITE_SUMMARY_CALENDAR,
//                base_path + AirbnbConstants.PATH_SUMMARY_CALENDAR_BOSTON);
//        appendUsingPrintWriter(AirbnbConstants.PATH_WRITE_SUMMARY_CALENDAR,
//                base_path + AirbnbConstants.PATH_SUMMARY_CALENDAR_DENVER);
//        appendUsingPrintWriter(AirbnbConstants.PATH_WRITE_SUMMARY_CALENDAR,
//                base_path + AirbnbConstants.PATH_SUMMARY_CALENDAR_PORTLAND);
//
//        appendUsingPrintWriter(AirbnbConstants.PATH_WRITE_NEIGHBOURHOOD,
//                base_path + AirbnbConstants.PATH_NEIGHBOURHOOD_AUSTIN);
//        appendUsingPrintWriter(AirbnbConstants.PATH_WRITE_NEIGHBOURHOOD,
//                base_path + AirbnbConstants.PATH_NEIGHBOURHOOD_BOSTON);
//        appendUsingPrintWriter(AirbnbConstants.PATH_WRITE_NEIGHBOURHOOD,
//                base_path + AirbnbConstants.PATH_NEIGHBOURHOOD_DENVER);
//        appendUsingPrintWriter(AirbnbConstants.PATH_WRITE_NEIGHBOURHOOD,
//                base_path + AirbnbConstants.PATH_NEIGHBOURHOOD_PORTLAND);


    }


    public static void appendUsingPrintWriter(String filePath, String filePathToAppend) {
        //Write
        File file = new File(filePath);
        FileWriter fr = null;
        PrintWriter pr = null;

        // Read
        FileInputStream fstream = null;
        try {
            // Read
            fstream = new FileInputStream(filePathToAppend);
            //Write
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

            fr = new FileWriter(file, true);
            pr = new PrintWriter(new BufferedWriter(fr));

            String strLine;
            while ((strLine = br.readLine()) != null)   {
                pr.println(strLine);
            }


        } catch (Exception  e) {
            e.printStackTrace();
            System.exit(1);
        }finally {
            try {
                fstream.close();
                pr.close();
                fr.close();
            } catch (Exception e) {
                System.exit(1);
            }

        }
    }
}
