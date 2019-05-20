import org.apache.commons.csv.*;

import java.io.*;
import java.util.*;

public class ZillowMerging {


    public static void main(String[] args) {
        ArrayList<Integer> bedrooms = new ArrayList<Integer>();
        bedrooms.add(1);
        bedrooms.add(2);
        bedrooms.add(3);
        bedrooms.add(4);
        bedrooms.add(5);

        try {
            zillowMerger(fetchZillowFilePaths(), bedrooms);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void zillowMerger(ArrayList<String> fn, ArrayList<Integer> kl) throws Exception {
        HashMap<String, String> data2 = new HashMap<String, String>();
        Reader CustomReader = null;
        FileWriter CustomWriter = null;
        StringWriter CustomStringWritter = new StringWriter();
        CSVParser CustomCsvParser = null;
        CSVPrinter CustomPrinter = null;

        int file = 0;


        for (String filename : fn) {
            ArrayList<String> data = new ArrayList<String>();

            try {
                CustomReader = new FileReader(filename);
                CustomCsvParser = CSVFormat.EXCEL.withQuoteMode(QuoteMode.MINIMAL).withRecordSeparator('\n').withIgnoreEmptyLines(false).withQuote('"').withDelimiter(Constants.DELIMETER.charAt(0)).parse(CustomReader);
                CustomPrinter = new CSVPrinter(CustomStringWritter, CSVFormat.EXCEL.withQuoteMode(QuoteMode.MINIMAL).withRecordSeparator('\n').withIgnoreEmptyLines(false).withQuote('"').withDelimiter(','));
            } catch (IOException e) {
                e.printStackTrace();
            }

            int input = data.size();
            double output = 0.0;
            boolean positive = true;
            for (int i=0; i<input; i++) {
                double contribution = 1.0/(2.0*((double)i) + 1.0);
                if (positive)
                    output += contribution;
                else
                    output -= contribution;
                positive = !positive;
            }
            List<String> dates = new ArrayList<String>();
            List<Integer> skipList = new ArrayList<Integer>();
            for (CSVRecord csv_record_line : CustomCsvParser) {
                if (csv_record_line.getRecordNumber() == 1) {
                    int index = 0;

                    for (String recordblockXAxis : csv_record_line) {
                        if (recordblockXAxis.matches("[0123456789]+-[0123456789]+") && recordblockXAxis.compareTo("2016-01") >= 0) {
                            dates.add(recordblockXAxis.concat("-01"));
                        } else if (recordblockXAxis.matches("[0-9]+-[0-9]+")) {
                            skipList.add(index);
                        }


                        ++index;
                    }
                } else {
                    int index = 0;
                    int numberdate = 0;
                    Iterator<String> recordValues = csv_record_line.iterator();
                    ArrayList<String> line = new ArrayList<String>();
                    while (recordValues.hasNext()) {
                        if (index < 5) {
                            line.add(recordValues.next());
                        } else if (index == 5) {
                            line.add(recordValues.next());
                            line.add(kl.get(file).toString());

                            line.add("date");
                            line.add("price");
                        } else {
                            while (recordValues.hasNext()) {
                                if (!skipList.contains(index)) {
                                    line.set(7, dates.get(numberdate));
                                    line.set(8, recordValues.next());
                                    ++numberdate;

                                    try {
                                        CustomPrinter.printRecord(line);
                                        data.add(CustomStringWritter.toString());
                                        if (data2.get(line.get(0) + line.get(6) + line.get(7)) == null) {
                                            data2.put(line.get(0) + line.get(6) + line.get(7), CustomStringWritter.toString());
                                        }
                                        CustomStringWritter.getBuffer().setLength(0);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    recordValues.next();
                                    output++;
                                }

                                ++index;
                            }
                        }

                        ++index;
                    }
                }
            }

            try {
                CustomWriter = new FileWriter(filename.replace(".csv", "_out.csv"));

                for (String currentString : data) {
                    CustomWriter.write(currentString);
                }

                CustomWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

                CustomReader.close();
                CustomPrinter.close();
                CustomCsvParser.close();
        }

        try {
            CustomWriter = new FileWriter(AirbnbConstants.BASE_PATH  + "zillow_output.csv");

            for (Map.Entry<String, String> entry : data2.entrySet()) {
                CustomWriter.write(entry.getValue());
            }

            CustomWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static ArrayList<String> fetchZillowFilePaths() {
        ArrayList<String> arrayList =  new ArrayList<String>();
        arrayList.add(AirbnbConstants.BASE_PATH + "zillow_1");
        arrayList.add(AirbnbConstants.BASE_PATH + "zillow_2");
        arrayList.add(AirbnbConstants.BASE_PATH + "zillow_3");
        arrayList.add(AirbnbConstants.BASE_PATH + "zillow_4");
        arrayList.add(AirbnbConstants.BASE_PATH + "zillow_5");


        return arrayList;
    }
 }
