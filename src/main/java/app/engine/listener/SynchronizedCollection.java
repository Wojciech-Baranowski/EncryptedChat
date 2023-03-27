package app.engine.listener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SynchronizedCollection<T> {

    private final List<T> list;
    private final CountLatch countLatch;

    public SynchronizedCollection() {
        this.list = Collections.synchronizedList(new ArrayList<>());
        this.countLatch = ListenerBean.getListener().getCountLatch();
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
        this.countLatch.countDown();
    }

    public synchronized boolean isEmpty() {
        return this.list.isEmpty();
    }

}
