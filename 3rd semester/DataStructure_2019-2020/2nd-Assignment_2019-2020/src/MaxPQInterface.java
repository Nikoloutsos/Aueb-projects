/**
 * Priority Queue interface
 * @param <T>
 */
public interface MaxPQInterface<T> {

    /**
     * Inserts the specified element into this priority queue.
     * @param item is a item of a node
     */
    void insert(T item);


    /**
     * Retrieves, but does not remove, the head of this queue, or returns null if this queue is empty.
     * @return the head of the queue
     */
    T peek();


    /**
     * Retrieves and removes the head of this queue, or returns null if this queue is empty.
     * @return the head of the queue
     */
    T getMax();
}
