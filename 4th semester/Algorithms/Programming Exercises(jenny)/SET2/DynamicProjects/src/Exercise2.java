import java.util.ArrayList;

class Exercise2
{

	private int[][] caloriesAndFat; // 2D array. First row has the calories. Second row has the fat.
	private int wantedCalories; // Total calories.
	ArrayList<Pair>  menu = new ArrayList<Pair>();


	public Exercise2( int[][] caloriesAndFat, int wantedCalories )
	{
		this.caloriesAndFat = caloriesAndFat;
		this.wantedCalories = wantedCalories;	
	}


	//------------------------------------------------------------------------------------------------------
	public int solveExercise2()
	{
		/*sort by calories; i will use insertion sort because there are not many
		foods(at least in our data) so there is no need to use sort algorithms like mergesort or quicksort*/
		int numOfFoods = caloriesAndFat[0].length;
		/*By sorting the elements  in the end if i have more than one solution
		 to my problem(same calories and same fat) i want to choose the one with more foods, so that the menu will consist
		 of the largest number of foods possible. It is better to have a variety of foods*/
		InsertionSort sort = new InsertionSort();
		sort.insertion_sort(caloriesAndFat, numOfFoods-1);

		//table used to find the menu withmax calories i can suggest that do not exceed the number of wanted calories
		Pair solution[][] = new Pair[numOfFoods][wantedCalories+1];


		//when having one food you can either give a menu with the calories of that food or none
		for(int i = 0; i <=wantedCalories; ++i)
		{
			if( i < caloriesAndFat[0][0])
			{
				solution[0][i] = new Pair(0,0);
			}else {
				solution[0][i] = new Pair(caloriesAndFat[0][0],caloriesAndFat[1][0]);
			}
		}

		for(int i = 1; i < numOfFoods; ++i)
		{
			for(int j = 0; j <= wantedCalories; ++j)
			{
				if(j < caloriesAndFat[0][i])
				{
					solution[i][j] = solution[i-1][j];
				}else{
					int caloriesHelp = caloriesAndFat[0][i] + solution[i-1][j-caloriesAndFat[0][i]].calories ;
					int fatHelp = caloriesAndFat[1][i] + solution[i-1][j-caloriesAndFat[0][i]].fat;
					if(caloriesHelp == solution[i-1][j].calories)
					{
						if(fatHelp > solution[i-1][j].fat)
						{
							fatHelp = solution[i-1][j].fat;
						}

					}
					solution[i][j] = new Pair(caloriesHelp, fatHelp);
				}
			}
		}

		//show the maxCalories that are closest to the wanted calories, but not greater than them
		int maxCalories = solution[numOfFoods -1][wantedCalories].calories;
		int leastFat = solution[numOfFoods-1][wantedCalories].fat;
        System.out.println("Max calories menu i can give you is: " + maxCalories + ", fat: " + leastFat);

		int helpPos = 0;
        if(maxCalories == 0)
        {
        	return -1;
		}
        while(maxCalories > 0)
        {
        	//find in the row the combination with least fat and calories = solution[0][x].calories
			leastFat = -1;

			for(int i = 0; i<numOfFoods; ++i)
			{
				if(leastFat==-1 && solution[i][maxCalories].calories == maxCalories)
				{
					helpPos = i;
					leastFat = solution[i][maxCalories].fat;
				}else if(solution[i][maxCalories].calories == maxCalories && leastFat > solution[i][maxCalories].fat)
				{
					leastFat = solution[i][maxCalories].fat;
					helpPos = i;
				}
			}

			menu.add(0,new Pair(caloriesAndFat[0][helpPos], caloriesAndFat[1][helpPos]));
			maxCalories = maxCalories - caloriesAndFat[0][helpPos];
		}


        return 1;

	}
	//------------------------------------------------------------------------------------------------------



	public void printSolution()
	{
		System.out.println("The foods chosen are:");
		for (int i = 0; i<menu.size(); ++i)
		{
			System.out.println((i+1)+ ")" + "Calories:" + menu.get(i).calories + "\tFat:" + menu.get(i).fat );
		}
	}

}