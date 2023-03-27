package app.engine.listener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class SynchronizedCollection<T> {

    private final List<T> list;
    private final CountDownLatch countDownLatch;

    public SynchronizedCollection() {
        this.list = Collections.synchronizedList(new ArrayList<>());
        this.countDownLatch = ListenerBean.getListener().getCountDownLatch();
    }

    public synchronized T takeElement() {
        if (!this.list.isEmpty()) {
            T element = this.list.get(0);
            this.list.remove(element);
            return element;
        } else {
            return null;
        }
    }

    public synchronized void put(T element) {
        this.list.add(element);
        this.countDownLatch.countDown();
    }

    public synchronized boolean isEmpty() {
        return this.list.isEmpty();
    }

}
