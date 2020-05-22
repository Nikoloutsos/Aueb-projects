import java.io.File;
import java.util.List;

class ExercisesSet2
{


	public static void main(String args[])
	{
		String ex1fileName = args[0];
		System.out.println("START OF EXERCISE 1");
		exercise1( ex1fileName );
		System.out.println("END OF EXERCISE 1\n");


		int ex2Calories = 0;
		String ex2fileName = args[1];
		System.out.println("START OF EXERCISE 2");
		try{
			ex2Calories = Integer.parseInt(args[2]);
		}catch (NumberFormatException nfe){
			System.err.println("Calories given are not a number");
			System.out.println("END OF EXERCISE 2");
			System.exit(-1);
		}
		if(ex2Calories < 0) {
			System.err.println("Cannot give you a menu with negative number of calories");
			System.out.println("END OF EXERCISE 2");
			System.exit(1);
		}
		System.out.println("Calories wanted are: " + ex2Calories);
		exercise2( ex2fileName, ex2Calories );
		System.out.println("END OF EXERCISE 2");


	}

	//------------------------------------------------------------------------------------------//
	public static void exercise1( String fileName )
	{
		int[] data = turnListIntoTable( fileName );
		Exercise1 exercise1 = new Exercise1( data );
		exercise1.solveExercise1();
		exercise1.printSolution();		
	}

	public static int[] turnListIntoTable( String fileName )
	{
		List<Integer> list = null;
		int[] data = null;

		try
		{
			File file = new File(fileName);
			list = (List)Utilities.convertFileSequenceToList(file); // retrieve data, create Lists.
			data = new int[list.size()]; // create 2-d table
			for( int i=0; i<data.length; i++ )
				data[i] = list.get(i);
		}
		catch( Exception e )
		{
			System.out.println("- Problem with file-reading.");
		}

		return data;
	}
	//------------------------------------------------------------------------------------------//


	//------------------------------------------------------------------------------------------//
	public static void exercise2( String fileName, int totalCalories )
	{
		int[][] data = turnListsInto2Dtable( fileName );
		Exercise2 exercise2 = new Exercise2( data, totalCalories );
		if(exercise2.solveExercise2()==-1)
		{
			System.out.println("All the foods that i have availbale are each more calories that the menu you want");
		}else{
			exercise2.printSolution();
		}

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
	//------------------------------------------------------------------------------------------//

}