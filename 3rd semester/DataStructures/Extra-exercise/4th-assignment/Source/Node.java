/**
 * A simple node needed for building singly-Linked-List and Doubly-Linked-List.
 * @param <D> Data
 */
public class Node<D> {
    public Node<D> previous;
    public Node<D> next;
    public D data;

    public Node(Node<D> previous, Node<D> next, D data) {
        this.previous = previous;
        this.next = next;
        this.data = data;
    }
}