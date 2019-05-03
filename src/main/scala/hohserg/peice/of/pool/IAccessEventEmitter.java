package hohserg.peice.of.pool;

import java.util.concurrent.atomic.AtomicReference;

public interface IAccessEventEmitter {

    AtomicReference<Pool> subscriber = new AtomicReference<>();

    default void subscribe(Pool subscriber) {
        this.subscriber.set(subscriber);
    }

    default void emit() {
        if (subscriber.get() != null)
            subscriber.get().update(this);
    }
}
