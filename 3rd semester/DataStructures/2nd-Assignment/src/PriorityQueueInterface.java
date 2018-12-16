import java.util.NoSuchElementException;

/**
 *
 * @param <T> T is the type of objects that the priority queue holds
 */
public interface PriorityQueueInterface<T> {
    void insert(T item);
    T remove(int position) throws NoSuchElementException;

    T getMax() throws NoSuchElementException;
    T max() throws NoSuchElementException;

    boolean isEmpty();
    int size();
}
