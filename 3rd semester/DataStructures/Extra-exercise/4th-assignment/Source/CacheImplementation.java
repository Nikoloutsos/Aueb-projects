/**
 * This is a LRU cache built for DS AUEB university.
 * @param <K> Key
 * @param <V> Value
 */
public class CacheImplementation<K,V> implements Cache<K,V>{
    private long numberOfCacheMiss;
    private long numberOfTotalLookups;
    private int cacheSize;

    private CustomHashMap<K,V> hashMap;
    private DoublyLinkedList<Model<K,V>> linkedList;

    public CacheImplementation(int cacheSize) {
        this.cacheSize = cacheSize;
        hashMap = new CustomHashMap<>(131_071); //prime number
        linkedList = new DoublyLinkedList<>(cacheSize);
    }

    @Override
    public V lookUp(K key) {
        ++numberOfTotalLookups;
        Node<Model<K,V>> result = hashMap.find(key);
        if(result == null){
            return null;
        }else{
            linkedList.moveNodeAtHead(result);
            return result.data.data;
        }
    }

    @Override
    public void store(K key, V value) {
        ++numberOfCacheMiss;
        K keyItemDeleted;
        if(linkedList.getTail().data == null){
            keyItemDeleted = null;
        }else{
            keyItemDeleted = linkedList.getTail().data.key;
        }

        hashMap.delete(keyItemDeleted);
        Node<Model<K,V>> doublyLinkedListNode = linkedList.putNewNode(new Model<>(key, value));
        hashMap.put(doublyLinkedListNode);
    }

    @Override
    public double getHitRatio() {
        return (double)(numberOfTotalLookups - numberOfCacheMiss)/numberOfTotalLookups;
    }

    @Override
    public long getHits() {
        return numberOfTotalLookups - numberOfCacheMiss;
    }

    @Override
    public long getMisses() {
        return numberOfCacheMiss;
    }

    @Override
    public long getNumberOfLookUps() {
        return numberOfTotalLookups;
    }
}
