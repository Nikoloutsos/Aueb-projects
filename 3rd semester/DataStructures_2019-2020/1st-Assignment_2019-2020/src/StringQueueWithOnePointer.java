import java.io.PrintStream;
import java.util.NoSuchElementException;

public class StringQueueWithOnePointer implements StringQueue {
    private int size = 0;
    private Node rear = null;

    /**
     * Empty Constructor
     */
    public StringQueueWithOnePointer() {
    }

    /**
     * @return true if the queue is empty, false if the queue has elements
     */
    @Override
    public boolean isEmpty() {
        return (this.rear == null);
    }

    /**
     * insert a String item to the queue (end of the queue)
     */
    @Override
    public void put(String item) {
        Node node = new Node (item);
        if (this.rear == null){
            this.rear = node;
        }
        else{
            if (this.rear.getNext() == null){
                this.rear.setNext(node);
                node.setNext(this.rear);
            }
            else{
                node.setNext(this.rear.next);
                this.rear.setNext(node);
            }
            this.rear = node;
        }
        this.size++;
    }

    /**
     * remove and @return the oldest item of the queue
     * @return oldest item of the queue
     * @throws NoSuchElementException if the queue is empty
     */
    @Override
    public String get() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        String item = this.rear.getNext().getItem();
        this.rear.setNext(rear.getNext().getNext());
        if(rear.getNext()==null){
            this.rear = null;
        }
        this.size--;
        return item;
    }

    /**
     * return without removing the oldest item of the queue
     * @return oldest item of the queue
     * @throws NoSuchElementException if the queue is empty
     */
    @Override
    public String peek() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        else {

            if (this.rear.getNext()==null){
                return this.rear.getItem();
            }
            else{
                return this.rear.getNext().getItem();
            }
        }
    }

    /**
     * print the elements of the queue, starting from the oldest
     * item, to the print stream given as argument. For example, to
     * print the elements to the
     * standard output, pass System.out as parameter. E.g.,
     * printQueue(System.out);
     */
    @Override
    public void printQueue(PrintStream stream){
         StringBuilder stringBuilder = new StringBuilder();

        if(isEmpty()){
            stringBuilder.append("Queue is empty");
            stream.println(stringBuilder);
            stream.flush();
        }
        else{
            // while not at end of list, output current node's data
            stringBuilder.append(" FIRST -> ");
            if(rear.getNext()==null){
                stringBuilder.append(rear.getItem());
            }else {
                Node temp = rear.getNext();
                Node node = rear.getNext();
                do{
                    stringBuilder.append(node.getItem());
                    if (node.getNext() != temp)
                        stringBuilder.append(" -> ");
                    node = node.next;
                }while (temp!=node);
            }
            stringBuilder.append(" <- LAST");
            stream.println(stringBuilder);
            stream.flush();
        }
    }

    /**
     * return the size of the queue, 0 if it is empty
     * @return number of elements in the queue
     */
    @Override
    public int size() {
        return this.size;
    }
}
