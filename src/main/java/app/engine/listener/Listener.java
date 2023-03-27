package app.engine.listener;

import java.util.concurrent.CountDownLatch;

public interface Listener {

    void start();

    void addParallelThread(ParallelThread<?> parallelThread);

    void removeParallelThread(ParallelThread<?> parallelThread);

    CountDownLatch getCountDownLatch();

}
