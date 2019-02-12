/**
 * Defines the interface for a Cache data structure
 * The cache stores item of type V with keys of type K.
 * Keys must be unique
 * */
public interface Cache<K, V> {

    /**
     * Look for data associated with key.
     * @param key the key to look for
     * @return The associated data or null if it is not found
     */
    public V lookUp(K key);

    /**
     * Stores data with associated with the given key. If required, it evicts a
     * data record to make room for the new one
     * @param key the key to associate with the data
     * @param value the actual data
     */
    public void store(K key, V value);

    /**
     * Returns the hit ratio, i.e. the number of times a lookup was successful divided by the number of lookup
     * @return the cache hit ratio
     */
    public double getHitRatio();

    /**
     * Returns the absolute number of cache hits, i.e. the number of times a lookup found data in the cache
     */
    public long getHits();

    /**
     * Returns the absolute number of cache misses, i.e. the number of times a lookup returned null
     */
    public long getMisses();

    /**
     * Returns the total number of lookups performed by this cache
     */
    public long getNumberOfLookUps();
}
