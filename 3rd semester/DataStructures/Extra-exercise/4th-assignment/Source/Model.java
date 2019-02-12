/**
 * This class encapsulates the key and data in one object.
 * @param <K> Key
 * @param <D> Data
 */

public class Model<K,D> {
    public K key;
    public D data;

    public Model(K key, D data) {
        this.key = key;
        this.data = data;
    }
}
