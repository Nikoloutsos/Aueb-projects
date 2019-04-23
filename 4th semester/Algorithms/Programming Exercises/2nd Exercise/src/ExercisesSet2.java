import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

///////////////////////////////////////////////////////////////////////////
// According to given submission files
///////////////////////////////////////////////////////////////////////////
public class ExercisesSet2 {

    public static void main(String[] args){

        //data injection from cli
        String ex1fileName = args[0];
        String ex2fileName = args[1];
        int ex2Calories = Integer.parseInt(args[2]);


        try {
            exercise1( ex1fileName );
            System.out.println("\n \n");
            exercise2(ex2fileName, ex2Calories);
        } catch (IOException e) {
            System.err.println("File not found. Please use a valid path(try adding full path)");
        }


    }

    public static void exercise1( String fileName ) throws IOException
    {
        Exercise1 exercise1 = new Exercise1( (ArrayList)Utilities.convertFileSequenceToList(new File(fileName)) );
        // exercise1.printInputData();
        exercise1.solveExercise1();
        exercise1.printSolution(System.out);
    }

    public static void exercise2( String fileName, int totalCalories )
    {
        int[][] data = turnListsInto2Dtable( fileName );
        Exercise2 exercise2 = new Exercise2(totalCalories, data);
        exercise2.solveExercise2();
        exercise2.printSolution(System.out);
    }

    public static int[][] turnListsInto2Dtable( String fileName )
    {
        File file = new File(fileName);
        List<List<Integer>> list = null;
        int [][] data = null;

        try
        {
            list = (List)Utilities.convertFileMatrixToListOfLists(file); // retrieve data, create Lists.
            int rows = list.size(); // get the rows
            int columns = ((List<Integer>)list.get(0)).size(); // get the columns
            data = new int[rows][columns]; // create 2-d table

            for( int i=0; i<rows; i++ )
            {
                List<Integer> rowList =  (List<Integer>)list.get(i);
                for( int j=0; j<columns; j++ )
                    data[i][j] = rowList.get(j);
            }

        }
        catch( Exception e )
        {
            System.out.println("- Problem with file-reading.");
        }

        return data;
    }







}
