package app.engine.listener;

public interface Listener {

    void start();

    void addConsumer(ThreadedConsumer parallelThread);

    void removeThreadedConsumer(ThreadedConsumer threadedConsumer);

    CountLatch getCountLatch();

}
