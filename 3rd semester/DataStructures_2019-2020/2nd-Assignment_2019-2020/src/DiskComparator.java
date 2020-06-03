import java.util.Comparator;

/**
 * Class used to compare two integers (used in the MaxPQ used in the MaxPQ<Disk> lists)
 */
public class DiskComparator implements Comparator<Disk> {
    /**
     *
     * @param t1 any given Disk object
     * @param t2 any give Disk object
     * @return 1 if Disk free Space > b free space, 0 if the Disk free space = b free space, -1 if the Disk free space < b free space
     */
    @Override
    public int compare(Disk t1, Disk t2) {
        return new IntegerComparator().compare(t1.getFreeSpace(), t2.getFreeSpace());

    }
}
