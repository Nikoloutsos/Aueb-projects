import java.util.ArrayList;

/*Solution: order by finish point. Each time put a position to where i have the first finish
* and then start from the next one that is not covered*/
public class TestFirstGreedy {
    private static Pair[] data= new Pair[5];
    private static ArrayList<Double> pointsToPut = new ArrayList<>();
    public static void main(String[] args) {
        /*let's suppose the data are ordered by finish point, otherwise i'd have to sort them and that's
        gonna take O(nlogn)
         */
       importData();

       double currentPos = data[0].finish;
       double pointToBeAdded = currentPos;
       for(int i = 1; i < data.length; ++i)
       {
           if(data[i].start <= currentPos)
           {
               if(data[i].finish < pointToBeAdded){
                   pointToBeAdded = data[i].finish;
                   continue;
               }
           }else{
               pointsToPut.add(pointToBeAdded);
               currentPos = data[i].finish;
               pointToBeAdded = currentPos;
           }
       }
        pointsToPut.add(pointToBeAdded);



       printPosForPoints();




    }

    private static void importData(){
        data[0] = new Pair(0.5,0.8);
        data[1] = new Pair(0,1);
        data[2] = new Pair(1,3);
        data[3] = new Pair(3.1,4);
        data[4] = new Pair(4.1, 5);
    }

    private static void printPosForPoints() {
         System.out.println("The number of points you will need to add on the line is:"
                + pointsToPut.size());
        System.out.println("The positions you need to put the points to minimize them are: ");
        for(int i = 0; i < pointsToPut.size(); ++i)
        {
            System.out.println("Position: " + pointsToPut.get(i));
        }
    }
}
