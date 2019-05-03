package hohserg.peice.of.pool;

import java.util.ArrayDeque;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Queue;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class Pool<A, Collection extends IPooledCollection<A>> {

    private LinkedHashMap<Collection, Void> inUsePool;
    private Queue<Collection> freePoll;
    private final int capacity;

    public Pool(Supplier<Collection> aNew, int capacity, float loadFactor) {

        inUsePool = new LinkedHashMap<Collection, Void>(capacity, 0.9f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Collection, Void> eldest) {
                if (size() > capacity) {

                    eldest.getKey().clear();
                    freePoll.add(eldest.getKey());

                    return true;
                } else
                    return false;
            }
        };

        freePoll = new ArrayDeque<>(capacity);
        this.capacity = capacity;
        for (int i = 0; i < capacity * (1 + loadFactor); i++) {
            Collection collection = aNew.get();
            collection.subscribe(this);
            freePoll.add(collection);
        }
    }

    public Collection newCollection() {
        Collection next = freePoll.poll();
        inUsePool.put(next, null);
        return next;
    }

    public Collector<A, Collection, Collection> collector() {
        return new PoolCollectorImpl<>(
                this::newCollection,
                Collection::add,
                (left, right) -> {
                    left.addAll(right);
                    return left;
                },
                Function.identity());
    }

    public void update(IAccessEventEmitter collection) {
        inUsePool.get(collection);
    }
}
