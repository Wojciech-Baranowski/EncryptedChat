package app.engine.listener;

import lombok.Getter;

import java.util.function.Consumer;

import static app.engine.listener.ListenerBean.getListener;

public class ParallelThread<T> {

    private final SynchronizedCollection<T> synchronizedCollection;
    private final Consumer<SynchronizedCollection<T>> parallelOperation;
    private final Consumer<T> consumer;
    @Getter
    private final Thread thread;

    public ParallelThread(Consumer<SynchronizedCollection<T>> parallelOperation, Consumer<T> consumer) {
        this.synchronizedCollection = new SynchronizedCollection<>();
        this.parallelOperation = parallelOperation;
        this.consumer = consumer;
        this.thread = new Thread(() -> this.parallelOperation.accept(this.synchronizedCollection));
        getListener().addParallelThread(this);
        this.thread.start();
    }

    public void consume() {
        while (!this.synchronizedCollection.isEmpty()) {
            this.consumer.accept(this.synchronizedCollection.takeElement());
        }
    }

}
