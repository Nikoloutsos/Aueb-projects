/**
 * ListNode represents a list node
 * Each node contains an int as data and a reference to the next node in the list.
 */
class Node {
    private String item;
    Node next = null;

    /**
     * Constructor. Sets data
     *
     * @param item the data stored
     * @return
     */
    Node(String item) {
        this.item = item;
    }

    /**
     * Returns this node's data
     *
     * @return the reference to node's data
     */
    String getItem() {
        // return data stored in this node
        return item;
    }

    /**
     * Get reference to next node
     *
     * @return the next node
     */
    Node getNext() {
        // get next node
        return next;
    }


    /**
     * Set reference to next node
     *
     * @param next reference
     */
    void setNext(Node next) {
        this.next = next;
    }
}
