package app.engine.listener;

import app.engine.input.InputBean;
import app.engine.scene.SceneBean;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ListenerBean implements Listener {

    private static Listener listener;

    private final List<ParallelThread<?>> parallelThreads;
    @Getter
    private final CountDownLatch countDownLatch;

    private ListenerBean() {
        this.parallelThreads = new ArrayList<>();
        this.countDownLatch = new CountDownLatch(1);
    }

    public static Listener getListener() {
        if (listener == null) {
            listener = new ListenerBean();
        }
        return listener;
    }

    public void start() {
        initializeListeners();
        listen();
    }

    @Override
    public void addParallelThread(ParallelThread<?> parallelThread) {
        this.parallelThreads.add(parallelThread);
    }

    @Override
    public void removeParallelThread(ParallelThread<?> parallelThread) {
        this.parallelThreads.remove(parallelThread);
    }

    private void initializeListeners() {
        InputBean.getInput().initializeListeners();
        SceneBean.getScene().initializeListeners();
    }

    private synchronized void listen() {
        while (true) {
            try {
                this.parallelThreads.forEach(ParallelThread::consume);
                this.countDownLatch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
