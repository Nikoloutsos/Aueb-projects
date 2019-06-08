public class Pair<T extends CharSequence> {
    public T x;
    public T y;

    public Pair(T x, T y) {
        this.x = x;
        this.y = y;
    }

    public static boolean equals(Pair<String> obj1, Pair<String> obj2) {
        boolean first = false;
        boolean second = false;
        if(obj1.x.equalsIgnoreCase(obj2.x )|| obj1.x.equalsIgnoreCase(obj2.y)) first = true;
        if(obj1.y.equalsIgnoreCase(obj2.x)|| obj1.y.equalsIgnoreCase(obj2.y)) second = true;
        return first && second;
    }
}

