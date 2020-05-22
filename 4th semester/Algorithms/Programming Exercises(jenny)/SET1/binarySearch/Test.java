import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test {
    private static int position[] = {-1, -1};

    public static void main(String[] args) {
        int key = 0;
        try{
            key = Integer.parseInt(args[1]);
        }catch (NumberFormatException nfe){
            System.out.println("Key given is not a number");
            System.exit(-1);
        }

        List<Integer> array = new ArrayList<>();
        File f = new File(args[0]);
        try {
            array = Utilities.convertFileSequenceToList(f);
        } catch (IOException e) {
            System.out.println("Problem with opening file");
            System.exit(-1);
        }

        String sortedOrder;
        if(array.get(0)> array.get(array.size() -1)){
            sortedOrder = "descending";
        }
        else{
            sortedOrder = "ascending";
        }
        findFisrtOrLastPosition(array, 0, array.size() - 1, key, "first", sortedOrder);
        findFisrtOrLastPosition(array, 0, array.size()-1, key, "last", sortedOrder);
        System.out.println("First position is: " + position[0]);
        System.out.println("Last position is: " + position[1]);

    }


    private static void findFisrtOrLastPosition(List<Integer> array, int l, int r, int key, String positionToBeFound, String sortedOrder){
        int middle;
        while(l<=r){
            middle = (l+r)/2;
            if(array.get(middle) == key){
                if(positionToBeFound.equalsIgnoreCase("first")){
                        position[0] = middle;
                        r = middle -1;
                }
                else{
                    l = middle +1;
                    position[1] = middle;
                }
            }
            else if(array.get(middle) < key){
                if(sortedOrder.equalsIgnoreCase("ascending")){
                    l = middle + 1;
                }
                else{
                    r = middle -1;
                }
            }
            else{
                if(sortedOrder.equalsIgnoreCase("ascending")){
                    r = middle -1;
                }
                else{
                    l = middle + 1;

                }
            }
        }
    }
}


