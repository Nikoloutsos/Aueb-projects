/**
 * A singly-Linked-List that holds Doubly-Linked-List Nodes.
 * @param <K> Key
 * @param <D> Value
 */
public class SinglyLinkedList<K,D>{
    private Node<Node<Model<K,D>>> head;
    public SinglyLinkedList() {}


    public Node<Model<K,D>> find(K key){
        Node<Node<Model<K,D>>> currentNode = head;
        while(currentNode != null){
            if(currentNode.data.data.key.equals(key)){
                return currentNode.data;
            }
            currentNode = currentNode.next;
        }

        return null;
    }


    public Node<Model<K,D>> put(Node<Model<K,D>> data){
        Node<Node<Model<K,D>>> currentNode = head;
        if(head == null){
            head = new Node<>(null,null,data);
            return head.data;
        }
        while(currentNode.next!=null){
            currentNode = currentNode.next;
        }

        currentNode.next = new Node<>(null,null,data);
        return currentNode.next.data;
    }


    public Node<Model<K,D>> delete(K key){
        if(key == null) return null;
        if(head == null) return null;

        if(head.data.data.key.equals(key)){
            Node<Model<K,D>> temp = head.data;
            head = head.next;
            return temp;
        }

        Node<Node<Model<K,D>>> current = head;

        if(current.next != null){
            while(!current.next.data.data.key.equals(key)){
                current = current.next;
            }
        }


        Node<Node<Model<K,D>>> deletedNode = current.next;
        current.next = current.next.next;
        return deletedNode.data;

    }






}
