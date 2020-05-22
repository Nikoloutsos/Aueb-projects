import java.io.File;
import java.util.ArrayList;

public class Askisi1_2Main {
	/**
	Set the path here.
	**/
    private static String PATH_FOR_READING_FILE_WITH_INTEGERS = "1.2-sm.txt";


    public static void main(String[] args) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        updateWithValuesFromCommandLine(args);

        try {
            arrayList = (ArrayList<Integer>) Utilities.convertFileSequenceToList(new File(PATH_FOR_READING_FILE_WITH_INTEGERS));
        } catch (Exception e) {
            System.err.println("Problem with finding the file you specified(please change it) : \n" + e.getMessage());
            System.exit(1);
        }

        Askisi1_2.quickSort(arrayList);
        System.out.println("QuickSort finished : ");
        System.out.println(arrayList);

    }

    private static void updateWithValuesFromCommandLine(String[] args) {
        try{
            PATH_FOR_READING_FILE_WITH_INTEGERS = args[0];
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Default path is running.(Read the report.pdf for changing it)");
        }catch (Exception e){
            System.err.println("Something is going wrong please read the report.pdf");
        }
    }

}
