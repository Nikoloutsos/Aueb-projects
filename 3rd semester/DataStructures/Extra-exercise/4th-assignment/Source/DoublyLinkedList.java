/**
 * Useful for LRU
 * @param <D> Data
 */
public class DoublyLinkedList<D> {
    private final int SIZE;
    private Node<D> head;
    private Node<D> tail;


    public DoublyLinkedList(int SIZE) {
        this.SIZE = SIZE;
        if(SIZE <= 0 ) throw new IllegalArgumentException("Only positive size number is allowed!");

        if (SIZE == 1){
            head = new Node<D>(null,null,null);
            tail = head;
        }

        if(SIZE >= 2){
            Node<D> exCurrent = new Node<>(null,null,null);
            head =  exCurrent;
            Node<D> current;
            for (int i=1; i<SIZE; ++i){
                current = new Node<>(exCurrent,null,null);
                exCurrent.next = current;
                exCurrent = current;
            }
            tail = exCurrent;
        }
    }

    /**
     * Does the same thing with moveNodeAtHead() but it also updates the Data inside node.
     * @return The node.
     */
    public Node<D> putNewNode(D newData){
        tail.data = newData;
        moveNodeAtHead(tail);
        return head;
    }

    /**
     * Useful for getting the key from least recently used item.
     * @return The whole tail node.
     */
    public Node<D> getTail(){
        return tail;
    }

    /**
     * Update the doublyLinkedList so that <parameter>node</parameter> is the head.
     * @return The Node.
     */
    public Node<D> moveNodeAtHead(Node<D> node){
        if(node == head) return node;
        if (node == tail ){
            tail = node.previous;
            node.previous.next = null;
            node.previous = null;
            node.next = head;
            head.previous = node;

            head = node;
            return head;
        }

        node.previous.next = node.next;
        node.next.previous = node.previous;
        head.previous = node;
        node.next = head;
        node.previous = null;
        head = node;
        return head;


    }

}
