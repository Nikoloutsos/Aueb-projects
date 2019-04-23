import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;

///////////////////////////////////////////////////////////////////////////
// Frog-Problem
///////////////////////////////////////////////////////////////////////////
public class Exercise1 {

    /*Global variables*/
    private ArrayList<Integer> dp;
    private ArrayList<Integer> locationAware;
    private ArrayList<Integer> foodAmmount ;

    //Data injection
    public Exercise1(ArrayList<Integer> foodAmmount) {
        this.foodAmmount = foodAmmount;
        this.dp = new ArrayList<>();
        locationAware = new ArrayList<>();
        dp.add(0);
        locationAware.add(0);
    }


    //Bottom-up approach
    public void solveExercise1() {
        //init
        int positionOfBestLeaf;

        int noLeafs = foodAmmount.size();
        for (int i = 1; i < noLeafs; i++) {
            positionOfBestLeaf = findIndexOfBestLeaf(i);
            updatedp(positionOfBestLeaf);
            updateLocationAware(positionOfBestLeaf);
        }
    }




    public void printSolution(PrintStream pStream) {
        pStream.println("Frog figured it out, result: ");
        pStream.print(dp.get(dp.size() - 1) + ", ");

        ArrayList<Integer> pathFrogFollowed = new ArrayList<>();
        int index = locationAware.size() -1;
        for (int i=0; i<dp.get(dp.size() - 1); i++){
            pathFrogFollowed.add(locationAware.get(index));
            index = locationAware.get(index);
        }

        Collections.reverse(pathFrogFollowed);
        pathFrogFollowed.add(dp.size() - 1);
        pStream.print(pathFrogFollowed);

    }


    /*Helping methods*/
    private boolean canItGoThere(int startPosition, int targetPositon){
        return startPosition + foodAmmount.get(startPosition) >= targetPositon;
    }


    //Finds the best leaf in left.
    private int findIndexOfBestLeaf(int currentPosition){
        int minIndex = -1;
        int minJumps = Integer.MAX_VALUE;

        for(int i=0; i<currentPosition;i++){
            if(canItGoThere(i, currentPosition) && dp.get(i) < minJumps){
                minIndex = i;
                minJumps = dp.get(minIndex);
            }
        }

        return  minIndex;
    }

    //Update dp list
    private void updatedp(int positionOfBestLeaf) {
        dp.add(dp.get(positionOfBestLeaf) + 1);
    }

    //Updates locationAware list
    private void updateLocationAware(int positionOfBestLeaf) {
        locationAware.add(positionOfBestLeaf);
    }
}
