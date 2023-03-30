package app.engine.listener;

import app.engine.input.InputBean;
import app.engine.scene.SceneBean;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static app.engine.display.DisplayBean.getDisplay;

public class ListenerBean implements Listener {

    private static Listener listener;

    private final List<ThreadedConsumer> threadedConsumers;
    @Getter
    private CountLatch countLatch;

    private ListenerBean() {
        this.threadedConsumers = new ArrayList<>();
        this.countLatch = new CountLatch();
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
    public void addConsumer(ThreadedConsumer threadedConsumer) {
        this.threadedConsumers.add(threadedConsumer);
    }

    @Override
    public void removeThreadedConsumer(ThreadedConsumer threadedConsumer) {
        this.threadedConsumers.remove(threadedConsumer);
    }

    private void initializeListeners() {
        InputBean.getInput().initializeInputListener();
        SceneBean.getScene().initializeListeners();
    }

    private synchronized void listen() {
        while (true) {
            this.threadedConsumers.forEach(ThreadedConsumer::consume);
            getDisplay().draw();
            this.countLatch.await();
            this.countLatch.countUp();
        }
    }

}
