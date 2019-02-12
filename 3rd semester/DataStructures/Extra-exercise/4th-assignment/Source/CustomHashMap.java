/**
 * @param <K> Key
 * @param <D> Value
 */

public class CustomHashMap<K,D> {
    private final int BUFFER_SIZE;
    private SinglyLinkedList<K,D>[] buffer;


    public CustomHashMap(int BUFFER_SIZE) {
        this.BUFFER_SIZE = BUFFER_SIZE;
        this.buffer = new SinglyLinkedList[BUFFER_SIZE];

        for(int i=0; i<BUFFER_SIZE; ++i){
            buffer[i] = new SinglyLinkedList<K,D>();
        }
    }

    /**
     * Deletes a doubly-Linked-List Node if exists.
     * @param key
     * @return deleted doubly-Linked-List Node or null if it is not exist.
     */
    public Node<Model<K,D>> delete(K key){
        if(key == null) return null;
        return buffer[hashing(key)].delete(key);
    }

    /**
     * Add a new doubly-Linked-List Node to a certain hashmap's singlyLinkedList.
     * @param node
     * @return
     */
    public Node<Model<K,D>> put(Node<Model<K,D>> node){
        return buffer[hashing(node.data.key)].put(node);
    }

    /**
     *
     * @param key
     * @return a Doubly-Linked-List Node(Node<Model<K,D>>) if exists with certain key.
     */
    public Node<Model<K,D>> find(K key){
       return buffer[hashing(key)].find(key);
    }

    /**
     * A hashing method.
     * @param key
     * @return the offset.
     */
    private int hashing(K key){
        int hashingKey = key.hashCode();
        return   hashingKey > 0 ? hashingKey%BUFFER_SIZE : -hashingKey%BUFFER_SIZE;
    }

}
