package hohserg.peice.of.pool;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class PoolCollectorImpl<A, B, C> implements Collector<A, B, C> {
    private final Supplier<B> create;
    private final BiConsumer<B, A> add;
    private final BinaryOperator<B> combiner;
    private final Function<B, C> finisher;

    public PoolCollectorImpl(Supplier<B> create, BiConsumer<B, A> add, BinaryOperator<B> combiner,Function<B, C> finisher) {
        this.create = create;
        this.add = add;
        this.combiner = combiner;
        this.finisher = finisher;
    }

    @Override
    public Supplier<B> supplier() {
        return create;
    }

    @Override
    public BiConsumer<B, A> accumulator() {
        return add;
    }

    @Override
    public BinaryOperator<B> combiner() {
        return combiner;
    }

    @Override
    public Function<B, C> finisher() {
        return finisher;
    }

    @Override
    public Set<Characteristics> characteristics() {
        Set<Characteristics> r = new HashSet<>();
        r.add(Characteristics.UNORDERED);
        return r;
    }
}
