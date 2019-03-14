import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Askisi1_1 {

    private final static int ITEM_NOT_FOUND = -1;


    /**
     * Complexity of this method is O(log(n)) where n is the array.length.
     */
    public static int findFirstOccurrence(List<Integer> array, int element, int lowIndex, int highindex) {
        int lastVisit = ITEM_NOT_FOUND;

        //Checks
        if (lowIndex < 0 || highindex >= array.size() ||
                array.size() == 0 || lowIndex > highindex) return ITEM_NOT_FOUND;

        //Algorithm
        do {
            int mid = lowIndex + (highindex - lowIndex) / 2;
            if (array.get(mid) == element) {
                highindex = mid - 1;
                lastVisit = mid;
            } else {
                lowIndex = mid + 1;
            }
        } while (lowIndex <= highindex);

        return lastVisit;
    }

    /**
     * Complexity of this method is O(log(n)) where n is the array.length.
     */
    public static int findLastOccurrence(List<Integer> array, int element, int lowIndex, int highindex) {
        int lastVisit = ITEM_NOT_FOUND;

        //Checks
        if (lowIndex < 0 || highindex >= array.size() ||
                array.size() == 0 || lowIndex > highindex) return ITEM_NOT_FOUND;

        //Algorithm
        do {
            int mid = lowIndex + (highindex - lowIndex) / 2;
            if (array.get(mid) == element) {
                lowIndex = mid + 1;
                lastVisit = mid;
            } else {
                highindex = mid - 1;
            }
        } while (lowIndex <= highindex);

        return lastVisit;
    }

    /**
     * Complexity of this method is O(log(n)) where n is the array.length.
     */
    public static int doNaiveBinarySearch(List<Integer> array, int element, int low, int high){
        int mid;
        while(low<=high){
            mid = low + (high - low)/2;

            if(array.get(mid) == element) return mid;
            else if (array.get(mid) > element) high = mid - 1;
            else if (array.get(mid) < element) low = mid + 1;
        }

        return ITEM_NOT_FOUND;
    }

  

}
