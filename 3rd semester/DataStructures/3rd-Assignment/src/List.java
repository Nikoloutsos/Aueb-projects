
/*
Simple linked List that stores unique strings. ( where abC == abc is true )
Not to be confused with java's List interface.
 */
class List {
    class Node {
        //Declare class variables
        private Node next;
        private String data;

        public Node(String data) {
            this.data = data;
        }

        public String getData() {
            return data;
        }
    }

    private Node head;
    private int numNodes;

    public List(String string) {
        head = new Node(string);
        numNodes = 1;
    }

    public List() {
    }


    public void add(String string) {
        if (!isInList(string)) {
            Node temp = new Node(string);
            temp.next = head;
            head = temp;
            numNodes++;
        }
    }


    public void deleteString(String string) {
        if (isInList(string)) {
            Node currentNode = head;
            int position = 0;

            if (currentNode.getData().equalsIgnoreCase(string)) {
                head = head.next;
                numNodes--;
                return;
            }

            while (!currentNode.getData().equalsIgnoreCase(string)) {
                currentNode = currentNode.next;
                position++;
            }

            Node previousNode = head;
            for (int i = 0; i < position - 1; i++) {
                previousNode = previousNode.next;
            }
            previousNode.next = previousNode.next.next;
            numNodes--;


        }
    }

    public int getSize() {
        return numNodes;
    }

    public boolean isInList(String string) {
        Node currentNode = head;
        for (int i = 0; i < getSize(); i++) {
            if (currentNode.getData().equalsIgnoreCase(string)) {
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    public void printList() {
        Node currentNote = head;
        while (currentNote != null) {
            System.out.println(currentNote.data);
            currentNote = currentNote.next;
        }
    }
}