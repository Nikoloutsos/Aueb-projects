import java.util.Comparator;

/**
 * Class used to compare two integers (used in the MaxPQ used in the Disk class)
 */
public class IntegerComparator implements Comparator<Integer> {

    /**
     *
     * @param t1 any integer
     * @param t2 any integer
     * @return the outcome of the comparison between the two integers
     */
    @Override
    public int compare(Integer t1, Integer t2) {
        return t1 - t2;
    }
}
