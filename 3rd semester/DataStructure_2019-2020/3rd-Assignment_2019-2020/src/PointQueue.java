import java.io.PrintStream;
import java.util.NoSuchElementException;

/**
 * IntegerQueueImpl class
 */
public class PointQueue{
    /**
     * ListNode represents a list node
     * Each node contains an int as data and a reference to the next node in the list.
     */
    @SuppressWarnings("InnerClassMayBeStatic")
    private class Node {
        private Point item;
        Node next = null;

        /**
         * Constructor. Sets data
         * @param item the data stored
         */
        Node(Point item) {
            this.item = item;
        }

        /**
         * Returns this node's data
         * @return the reference to node's data
         */
        Point getItem() {
            // return data stored in this node
            return item;
        }

        /**
         * Get reference to next node
         * @return the next node
         */
        Node getNext() {
            // get next node
            return next;
        }

        /**
         * Set reference to next node
         * @param next reference
         */
        void setNext(Node next) {
            this.next = next;
        }
    }

    private Node first = null;
    private Node last = null;

    /**
     * Empty Constructor
     */
    public PointQueue(){
    }

    /**
     * @return true if the queue is empty,false if the queue has elements
     */
    public boolean isEmpty() {
        return (first == null);
    }

    /**
     * insert a Integer item to the queue (end of the queue)
     */
    public void put(Point item){
        Node node = new Node(item);
        if (isEmpty()){
            first = node;
            last = node;
        }
        else{
            last.setNext(node);
            last = node;
        }
    }

    /**
     * print the elements of the queue, starting from the oldest
     * item, to the print stream given as argument. For example, to
     * print the elements to the
     * standard output, pass System.out as parameter. E.g.,
     * printQueue(System.out);
     */
    public void printQueue(PrintStream stream){
        Node node = first;
        StringBuilder stringBuilder = new StringBuilder();

        if(isEmpty()){
            stringBuilder.append("\nQueue is empty");
            stream.println(stringBuilder);
            stream.flush();
        }
        else{
            // while not at end of list, output current node's data
            stringBuilder.append(" FIRST -> ");

            while (node != null) {
                stringBuilder.append(node.getItem());

                if (node.getNext() != null)
                    stringBuilder.append(" -> ");

                node = node.next;
            }
            stringBuilder.append(" <- LAST");
            stream.println(stringBuilder);
            stream.flush();
        }
    }
}
