import java.io.*;

/**
 * Sort class, implements sorting in read IntegerQueue
 */
public class Sort {

    /**
     *
     * @param args for file input through cmd
     * @throws Exception in case that file read is unsuccessfull or data are wrong
     */
    public static void main(String[] args) throws Exception {
        IntegerQueueImpl folder_size = Greedy.read_Input(new File(args[0]));
        int folder_size_size = folder_size.size() ;
        int[] integers = integerQueueToTable(folder_size);
        quicksort(integers, 0, folder_size_size-1);
        folder_size = tableToIntegerQueueImpl(integers);
        MaxPQ<Disk> allocated_disks = Greedy.allocate_Disks(folder_size);
        Greedy.print_Output(allocated_disks);
    }

    /**
     * Quicksort method
     * @param ints is the int table
     * @param p is the min size
     * @param r is the max size
     */
    static void quicksort(int[] ints, int p, int r)
    { if (r <= p) return;
        int i = partition(ints, p, r);
        quicksort(ints, p, i-1);
        quicksort(ints, i+1, r);
    }

    /**
     * partitions table to subtables
     * @param ints is the int table
     * @param p is the min size
     * @param r is the max size
     * @return int which indicates the result of the partition
     */
    @SuppressWarnings("StatementWithEmptyBody")
    private static int partition(int[] ints, int p, int r)
    { int i = p-1, j = r; int v = ints[r];
        for (;;)
        { while (less(ints[++i], v)) ;
            while (less(v, ints[--j]))
                if (j == p) break;
            if (i >= j) break;
            exch(ints, i, j);
        }
        exch(ints, i, r);
        return i;
    }

    /**
     * Returns the result of w<v
     * @param v is any int
     * @param w is any int
     * @return the result of v<m
     */
    private static boolean less(int v, int w) {
        return w < v;
    }

    /**
     * Exchanges a[i] with a[j]
     * @param a is any int table
     * @param i is any int
     * @param j is any int
     */
    private static void exch(int[] a, int i, int j) {
        int t = a[i]; a[i] = a[j]; a[j] = t;
    }

    /**
     * Generates int table from IntegerQueueImpl
     * @param integerQueue is any queue of the class IntegerQueueImpl
     * @return an int table
     */
    static int[] integerQueueToTable (IntegerQueueImpl integerQueue){
        int[] integers = new int[integerQueue.size()];
        for (int i = 0; i < integers.length; i++){
            integers[i] = integerQueue.get();
        }
        return integers;
    }

    /**
     * Generates IntegerQueueImpl from int table
     * @param ints any int table
     * @return an IntegerQueueImpl
     */
    static IntegerQueueImpl tableToIntegerQueueImpl(int[] ints){
        IntegerQueueImpl integerQueue = new IntegerQueueImpl();
        for (int i : ints) {
            integerQueue.put(i);
        }
        return  integerQueue;
    }


}
