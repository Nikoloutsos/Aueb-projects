
import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * Keep in mind that this class is only for objects which has id in [0,10000]
 * If you try to add an object not within this range bad things will happen!
 * @param <T> is the type of objects which PQ saves.
 */
public class PQ<T extends Key> implements PriorityQueueInterface<T> {
    private int numberOfActiveElements = 0;
    private T[] heapArray;
    private final float PERCENTAGE = 0.75f;
    private int[] locationAwareArray = new int[10002];
    protected Comparator<T> comparator;

    public PQ(int sizeOfArray, Comparator<T> comparator) {
        heapArray = (T[]) new Key[sizeOfArray + 1];
        this.comparator = comparator;
    }
    @Override
    public void insert(T item) {
        heapArray[++numberOfActiveElements] = item;
        locationAwareArray[item.getKey()] = numberOfActiveElements;
        swim(numberOfActiveElements);
        if ((float) numberOfActiveElements  / (heapArray.length-1) > PERCENTAGE) {
            resize();
        }
    }
    @Override
    public T remove(int id) throws NoSuchElementException {
        int position = getItemsPositionFromId(id);
        if(heapArray[position] == null ) throw new NoSuchElementException();
        T returnValue = heapArray[position];
        heapArray[position] = heapArray[numberOfActiveElements];

        locationAwareArray[heapArray[position].getKey()] = position;

        heapArray[numberOfActiveElements] = null;
        locationAwareArray[returnValue.getKey()] = 0;

        sink(position, --numberOfActiveElements);
        return returnValue;
    }
    @Override
    public T getMax() throws NoSuchElementException {
        return  remove(heapArray[1].getKey());
    }

    @Override
    public T max() throws NoSuchElementException {
        if(isEmpty()) throw new NoSuchElementException();
        return heapArray[1];
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }
    @Override
    public int size() {
        return numberOfActiveElements;
    }

    private void resize() {
        heapArray = Arrays.copyOf(heapArray, heapArray.length * 2);
    }
    private void swap(int p1, int p2) {
        T temp = heapArray[p1];
        heapArray[p1] = heapArray[p2];
        heapArray[p2] = temp;

        //Update the locationAwareArray
        int temp2 = locationAwareArray[heapArray[p1].getKey()];
        locationAwareArray[heapArray[p1].getKey()] = locationAwareArray[heapArray[p2].getKey()];
        locationAwareArray[heapArray[p2].getKey()] = temp2;
    }
    private void swim(int position) {
        if (position == 1) return;
        int parent = position / 2;
        if (comparator.compare(heapArray[position], heapArray[parent]) > 0) {
            swap(position, parent);
            swim(parent);
        }
    }
    private void sink(int parentPos, int numberOfElements) {
        int childPosition;
        while (2 * parentPos <= numberOfElements) {
            childPosition = 2 * parentPos;
            if (childPosition < numberOfElements) {
                if (comparator.compare(heapArray[childPosition + 1], heapArray[childPosition]) > 0) {
                    childPosition++;
                }
            }
            if (comparator.compare(heapArray[childPosition], heapArray[parentPos]) > 0) {
                swap(childPosition, parentPos);
                parentPos = childPosition;
            } else {
                return;
            }
        }
    }
    protected int getItemsPositionFromId(int id){
        return locationAwareArray[id];
    }
}
