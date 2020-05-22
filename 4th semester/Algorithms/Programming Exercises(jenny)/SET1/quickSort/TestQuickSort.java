import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestQuickSort {

    public static void main(String[] args) {
        List<Integer> array = new ArrayList<>();
        File f = new File(args[0]);
        try {
            array = Utilities.convertFileSequenceToList(f);
        } catch (IOException e) {
            System.out.println("Problem with opening file");
            System.exit(-1);
        }

        QuickSort.quickSort(array, 0, array.size() -1);

        for(int i : array){
            System.out.print( i + " " );
        }
    }
}
