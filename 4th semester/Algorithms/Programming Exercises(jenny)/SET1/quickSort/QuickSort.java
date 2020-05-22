import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuickSort {
    private static Random rg = new Random();
    private static int[] index = new int[2];

    public static void quickSort(List<Integer> array, int left, int right) {

        if (left < right) {
            partition(array, left, right);

            quickSort(array, left, index[0]);
            quickSort(array, index[1], right);
        }


    }


    private static void partition(List<Integer> array, int left, int right) {
        int pivot = array.get(rg.nextInt(right - left) + left);
        int visitIndex = left;

        while (visitIndex <= right) {
            if (array.get(visitIndex) < pivot) {
                swap(array, visitIndex, left);
                ++left;
                ++visitIndex;
            } else if (array.get(visitIndex) == pivot) {
					++visitIndex;
            } else {
					swap(array, visitIndex, right);
					--right; 
            }

        }

        index[0] = --left;
        index[1] = visitIndex;


    }

    private static void swap(List<Integer> array, int x, int y) {
        int temp = array.get(x);
        array.set(x, array.get(y));
        array.set(y, temp);
    }
}
