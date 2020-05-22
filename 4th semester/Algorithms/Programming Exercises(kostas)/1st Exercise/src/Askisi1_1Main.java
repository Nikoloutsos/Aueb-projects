import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Askisi1_1Main {


	/**
	Set the path  and integer here.
	**/
    private static String PATH_FOR_READING_FILE_WITH_INTEGERS = "1.1-sm.txt";
    private static int INT_YOU_WANT_TO_SEARCH = 3;

	
	
    public static void main(String[] args) {
        updateWithValuesFromCommandLine(args);
        List<Integer> array = new ArrayList<>();
        try {
            array = Utilities.convertFileSequenceToList(new File(PATH_FOR_READING_FILE_WITH_INTEGERS));
        } catch (Exception e) {
            System.err.println("Problem with finding the file you specified : \n" + e.getMessage());
            System.exit(1);
        }
        Integer wantedInteger = INT_YOU_WANT_TO_SEARCH;

        int oneOccurence = Askisi1_1.doNaiveBinarySearch(array, wantedInteger, 0, array.size() - 1);

        System.out.println("You searched for the number " + wantedInteger);
        System.out.println("First occurrence: " + Askisi1_1.findFirstOccurrence(array, wantedInteger, 0, oneOccurence));
        System.out.println("Last occurrence:  " + Askisi1_1.findLastOccurrence(array, wantedInteger, oneOccurence, array.size() - 1));
    }


    private static void updateWithValuesFromCommandLine(String[] args) {
        try{
            PATH_FOR_READING_FILE_WITH_INTEGERS = args[0];
            INT_YOU_WANT_TO_SEARCH = Integer.valueOf(args[1]);
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Default path and Integer are set.(Read the report.pdf for changing them)" );
        }catch (Exception e){
            System.err.println("Something is going wrong please read the report.pdf");
        }
    }
}
