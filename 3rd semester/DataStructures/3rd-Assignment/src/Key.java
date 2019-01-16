/**
 * Interface Key is implemented by classes whose objects has keys.
 * @param <T> is the type of key.(It can be int,long,String...)
 */
public interface Key<T> {
    T key();
}
