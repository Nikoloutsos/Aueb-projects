public class InsertionSort {


    public void insertion_sort(int food[][], int numOfFoods)
    {
        for(int i = 0; i<= numOfFoods; ++i){
            int j = i;

            while(j>0){
                if(food[0][j-1] > food[0][j]){
                    //swapping
                    int temp = food[0][j-1];
                    food[0][j-1] = food[0][j];
                    food[0][j] = temp;

                    int temp1 = food[1][j-1];
                    food[1][j-1] = food[1][j];
                    food[1][j] = temp1;
                }else if(food[0][j-1] == food[0][j]){
                    if(food[1][j-1] > food[1][j])
                    {
                        int temp = food[0][j-1];
                        food[0][j-1] = food[0][j];
                        food[0][j] = temp;

                        int temp1 = food[1][j-1];
                        food[1][j-1] = food[1][j];
                        food[1][j] = temp1;
                    }
                }else{
                    break;
                }
                j--;
            }
        }
    }
}



