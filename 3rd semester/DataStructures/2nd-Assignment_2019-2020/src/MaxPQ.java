
import java.util.Comparator;

/**
 * Heap based implementation of PriorityQueue
 * To implement it you need to implement the following helper functions as well
 * swim, sing, swap, grow
 */
public class MaxPQ<T> implements MaxPQInterface<T> {
    private T[] heap; // the heap to store data in
    private int size; // current size of the queue
    private Comparator<T> comparator; // the comparator to use between the objects

    private static final int DEFAULT_CAPACITY = 4; // default capacity
    private static final int AUTOGROW_SIZE = 4; // default auto grow

    /**
     * Queue constructor
     * @param comparator an element of comparator class
     */
    public MaxPQ(Comparator<T> comparator) {
        this.heap = (T[]) new Object[DEFAULT_CAPACITY + 1];
        this.size = 0;
        this.comparator = comparator;
    }


    /**
     * @return size of queue
     */
    public int getSize() {
        return size;
    }

    /**
     * Inserts the specified element into this priority queue.
     * @param item is an item to be inserted
     */
    @Override
    public void insert(T item) {
        if (size == heap.length - 1)
            grow();
        heap[++size] = item;
        swim(size);
    }

    /**
     * Retrieves, but does not remove, the head of this queue, or returns null if this queue is empty.
     * @return the head of the queue
     */
    @Override
    public T peek() {
        if (size == 0)
            return null;
        return heap[1];
    }

    /**
     * Retrieves and removes the head of this queue, or returns null if this queue is empty.
     * @return the head of the queue
     */
    @Override
    public T getMax() {
        if (size == 0)
            return null;
        T root = heap[1];
        heap[1] = heap[size];
        size--;
        sink(1);
        return root;
    }

    /**
     * Helper function to swim items to the top
     * @param i the index of the item to swim
     */
    private void swim(int i) {
        if (i == 1)
            return;
        int parent = i / 2;
        while (i != 1 && comparator.compare(heap[i], heap[parent]) > 0) {
            swap(i, parent);
            i = parent;
            parent = i / 2;
        }
    }

    /**
     * Helper function to swim items to the bottom
     * @param i the index of the item to sink
     */
    private void sink(int i) {
        int left = 2 * i;
        int right = left + 1;
        if (left > size)
            return;
        while (left <= size) {
            int max = left;
            if (right <= size) {
                if (comparator.compare(heap[left], heap[right]) < 0)
                    max = right;
            }
            if (comparator.compare(heap[i], heap[max]) >= 0)
                return;
            else {
                swap(i, max);
                i = max;
                left = i * 2;
                right = left + 1;
            }
        }
    }

    /**
     * Helper function to swap two elements in the heap
     * @param i the first element's index
     * @param j the second element's index
     */
    private void swap(int i, int j) {
        T tmp = heap[i];
        heap[i] = heap[j];
        heap[j] = tmp;
    }

    /**
     * Helper function to grow the size of the heap
     */
    private void grow() {
        T[] newHeap = (T[]) new Object[heap.length + AUTOGROW_SIZE];
        for (int i = 0; i <= size; i++) {
            newHeap[i] = heap[i];
        }
        heap = newHeap;
    }
}
