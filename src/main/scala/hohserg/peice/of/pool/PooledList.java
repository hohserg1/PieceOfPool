package hohserg.peice.of.pool;

import java.util.ArrayList;

public class PooledList<A> extends ArrayList<A> implements IPooledCollection<A> {

    public static <A> Pool<A, PooledList<A>> newPool(int capacity, float loadFactor) {
        return new Pool<>(PooledList::new, capacity, loadFactor);
    }
}
