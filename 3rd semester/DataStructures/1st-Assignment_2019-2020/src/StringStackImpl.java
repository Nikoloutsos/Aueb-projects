import java.io.PrintStream;
import java.util.NoSuchElementException;

public class StringStackImpl implements StringStack {
    private Node top = null;
    private int size = 0;

    /**
     * Empty Constructor
     */
    public StringStackImpl(){
    }

    /**
     * @return true if the stack is empty, false if the stack has elements
     */
    @Override
    public boolean isEmpty(){
        return (top==null);
    }

    /**
     * insert a String item to the top of the stack
     */
    @Override
    public void push(String item){
        Node node = new Node(item);
        if(isEmpty()){
            this.top = node;
        }
        else{
            node.setNext(top);
            this.top = node;
        }
        this.size++;
    }

    /**
     * remove and return the item on the top of the stack
     * @return the item on the top of the stack
     * @throws NoSuchElementException if the stack is empty
     */
    @Override
    public String pop() throws NoSuchElementException{
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        String item = top.getItem();
        if (top.getNext()==null){
            top = null;
        }
        else{
            top = top.getNext();
        }
        this.size--;
        return item;
    }

    /**
     * return without removing the item on the top of the stack
     * @return the item on the top of the stack
     * @throws NoSuchElementException if the stack is empty
     */
    @Override
    public String peek() throws NoSuchElementException{
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return top.getItem();
    }

    /**
     * print the elements of the stack, starting from the item
     * on the top,
     * to the stream given as argument. For example,
     * to print to the standard output you need to pass System.out as
     * an argument. E.g.,
     * printStack(System.out);
     */
    @Override
    public void printStack(PrintStream stream){
        Node node = top;
        StringBuilder stringBuilder = new StringBuilder();

        if(isEmpty()){
            stringBuilder.append("Queue is empty");
        }
        else{
            // while not at end of list, output current node's data
            stringBuilder.append(" TOP -> ");
            while (node != null) {
                stringBuilder.append(node.getItem());

                if (node.getNext() != null)
                    stringBuilder.append(" -> ");
                node = node.next;
            }
            stringBuilder.append(" <- BOTTOM");
        }
        stream.println(stringBuilder);
        stream.flush();
    }

    /**
     * return the size of the stack, 0 if it is empty
     * @return the number of items currently in the stack
     */
    @Override
    public int size(){
        return this.size;
    }
}
