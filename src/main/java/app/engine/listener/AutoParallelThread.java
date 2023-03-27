package app.engine.listener;

import java.util.function.Consumer;

import static app.engine.listener.ListenerBean.getListener;

public class AutoParallelThread<T> implements ThreadedConsumer {

    private final SynchronizedCollection<T> synchronizedCollection;
    private final Consumer<SynchronizedCollection<T>> parallelOperation;
    private final Consumer<T> consumer;

    public AutoParallelThread(Consumer<SynchronizedCollection<T>> parallelOperation, Consumer<T> consumer) {
        this.synchronizedCollection = new SynchronizedCollection<>();
        this.parallelOperation = parallelOperation;
        this.consumer = consumer;
        parallelOperation.accept(this.synchronizedCollection);
        getListener().addConsumer(this);
    }

    public void consume() {
        while (!this.synchronizedCollection.isEmpty()) {
            this.consumer.accept(this.synchronizedCollection.takeElement());
        }
    }

}
