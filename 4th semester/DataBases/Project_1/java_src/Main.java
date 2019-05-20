
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.values.PCollection;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


/**
 * (ELT) Transforms csv Zillow files into clean csv ready to be published in db!
 */
public class Main {


    private static List<Integer> maskedColumns_1 = new ArrayList<Integer>();
    private static List<Integer> maskedColumns_2 = new ArrayList<Integer>();
    private static List<Integer> maskedColumns_3 = new ArrayList<Integer>();
    private static List<Integer> maskedColumns_4 = new ArrayList<Integer>();
    private static List<Integer> maskedColumns_5plus = new ArrayList<Integer>();
    private static Pipeline pipeline;

    public static void main(String[] args) {
        PipelineOptions options = PipelineOptionsFactory.create();
        pipeline = Pipeline.create(options);

        maskedColumns_1 = findMaskedColumnIndexes(Constants.ZILLOW_HEADER_1);
        maskedColumns_2 = findMaskedColumnIndexes(Constants.ZILLOW_HEADER_2);
        maskedColumns_3 = findMaskedColumnIndexes(Constants.ZILLOW_HEADER_3);
        maskedColumns_4 = findMaskedColumnIndexes(Constants.ZILLOW_HEADER_4);
        maskedColumns_5plus = findMaskedColumnIndexes(Constants.ZILLOW_HEADER_5);

        final List<List<Integer>> list_Of_maskColumns = new ArrayList<List<Integer>>();
        list_Of_maskColumns.add(maskedColumns_1);
        list_Of_maskColumns.add(maskedColumns_2);
        list_Of_maskColumns.add(maskedColumns_3);
        list_Of_maskColumns.add(maskedColumns_4);
        list_Of_maskColumns.add(maskedColumns_5plus);
        System.out.println(maskedColumns_4);

        int zillowNumber = 0;
        for(String fullPath: Constants.PATHS_READ){
            zillowNumber++;
            String fullWritePath = Constants.BASE_PATH + "Upadated_Zillow" + zillowNumber;
            fullPath = Constants.BASE_PATH + fullPath;

            //Data injection
            final int finalZillowNumber = zillowNumber;
            PCollection<String> lines = pipeline
                    .apply(TextIO.read().from(fullPath))
                    .apply(new PTransform<PCollection<String>, PCollection<String>>() {

                        @Override
                        public PCollection<String> expand(PCollection<String> input) { //Batch processing
                            return input
                                    .apply(MapElements.via(new SimpleFunction<String, String>() {
                                        @Override
                                        public String apply(String input) {
                                            return transformLine(input, list_Of_maskColumns.get(finalZillowNumber - 1));
                                        }
                                    }));
                        }
                    });


            //Write-back
            lines.apply(TextIO
                    .write()
                    .to(fullWritePath)
                    .withoutSharding()
                    .withSuffix(".csv"));


            pipeline.run().waitUntilFinish();
        }


        System.out.println("end");
    }

    private static String transformLine(String input, List<Integer> masked_columns) {
        StringBuilder sb = new StringBuilder();
        String[] splitedLine = input.split(Constants.DELIMETER);
        int index = 0;
        for (String cString : splitedLine) {
            if (!masked_columns.contains(index++)) {
                sb.append(combineAllTransform(cString));
                sb.append(Constants.DELIMETER);
            }
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }


    private static String transormDate(String string) {
        return string.matches(Constants.REGEX_DATE) ? string + "-01" : string;
    }

    private static String transformField(String string) {
        return string.equalsIgnoreCase("RegionName") ? "zipcode" : string;
    }

    private static String combineAllTransform(String string) {
        string = transormDate(string);
        string = transformField(string);
        return string;
    }


    /**
     * finds unwanted columns
     */
    private static List<Integer> findMaskedColumnIndexes(String header) {
        List<Integer> result = new ArrayList<Integer>();
        String[] token_header = header.split(Constants.DELIMETER);
        for (int i = 0; i < token_header.length; i++) {
            if (!isWithinValidDate(token_header[i])) result.add(i);
        }
        return result;
    }

    /**
     * Returns true only if header title meets our conditions!
     */
    private static boolean isWithinValidDate(String date) {
        return date.matches(Constants.REGEX_VALID_DATE) || !date.matches(Constants.REGEX_DATE);
    }
    

}
