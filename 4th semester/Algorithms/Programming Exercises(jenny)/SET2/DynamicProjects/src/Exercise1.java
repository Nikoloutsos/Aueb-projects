class Exercise1
{

	private int[] leaves;
	private int minimumNumOfJumps[];
	private int jumpedFrom[];
	private int roadToTheEnd[];

	public Exercise1( int[] leaves )
	{
		this.leaves = leaves;
	}

	public void solveExercise1()
	{
		 minimumNumOfJumps = new int[leaves.length];
		 jumpedFrom = new int [leaves.length];

        /*initialize all minimumNUmberOfJumps to reach to a leaf to -1.
        It will be useful in the following steps*/
		for(int i = 1; i< minimumNumOfJumps.length; ++i)
		{
			minimumNumOfJumps[i] = -1;
		}

		minimumNumOfJumps[0] = 0;
		jumpedFrom[0]= 0;

		for(int i = 1; i<leaves.length; ++i)
		{
			for(int j =0; j<= i-1; ++j)
			{
				if(leaves[j] >= i - j)
				{
					//for the first time i can reach that specific leaf
					if(minimumNumOfJumps[i] == -1)
					{
						minimumNumOfJumps[i] = minimumNumOfJumps[j] +1;
						jumpedFrom[i] = j;
					}
					else if(minimumNumOfJumps[j] +1 <minimumNumOfJumps[i])
					{
						minimumNumOfJumps[i] = minimumNumOfJumps[j] +1;
						jumpedFrom[i] = j;
					}
				}
			}
		}

		roadToTheEnd= new int[minimumNumOfJumps[minimumNumOfJumps.length - 1] + 1];
		roadToTheEnd[0] = 0;
		roadToTheEnd[roadToTheEnd.length -1 ] = minimumNumOfJumps.length - 1;
		for(int i = roadToTheEnd.length -2; i>=1; --i)
		{
			roadToTheEnd[i] = jumpedFrom[roadToTheEnd[i+1]];
		}

	}

	public void printSolution()
	{
		/*no need to check whether there is a solution, because i asked the teacher and she
		assured me that all leaves have food*/
		System.out.println("-The minimum number of jumps required to jump from leaf 0 to the end is: " +
				minimumNumOfJumps[minimumNumOfJumps.length -1]);

		System.out.print("-The leaves you need to jump to  reach the end, starting from leaf 0 are: ");
		for(int i = 0; i< roadToTheEnd.length; ++i)
		{
			System.out.print(roadToTheEnd[i] + "   ");
		}
		System.out.println();

	}


}