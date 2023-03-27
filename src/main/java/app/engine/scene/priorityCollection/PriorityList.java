package app.engine.scene.priorityCollection;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class PriorityList implements PriorityCollection {

    private final List<Object> objects;

    public PriorityList() {
        this.objects = new LinkedList<>();
    }

    @Override
    public void setLowerThan(Object inserted, Object contained) {
        if (this.objects.contains(inserted)) {
            return;
        }
        for (int i = 0; i < this.objects.size(); i++) {
            if (this.objects.get(i).equals(contained)) {
                this.objects.add(i, inserted);
                return;
            }
        }
    }

    @Override
    public void setHigherThan(Object inserted, Object contained) {
        if (this.objects.contains(inserted)) {
            return;
        }
        for (int i = 0; i < this.objects.size(); i++) {
            if (this.objects.get(i).equals(contained)) {
                this.objects.add(i + 1, inserted);
                return;
            }
        }
    }

    @Override
    public void setOnLowest(Object inserted) {
        if (this.objects.contains(inserted)) {
            return;
        }
        this.objects.add(0, inserted);
    }

    @Override
    public void setOnHighest(Object inserted) {
        if (this.objects.contains(inserted)) {
            return;
        }
        this.objects.add(this.objects.size(), inserted);
    }

    @Override
    public void clear() {
        this.objects.clear();
    }

    @Override
    public void remove(Object removed) {
        this.objects.remove(removed);
    }

    @Override
    public Collection<Object> getObjectCollection() {
        return this.objects;
    }

    @Override
    public Object getLowest() {
        return this.objects.get(0);
    }

    @Override
    public Object getHighest() {
        return this.objects.get(this.objects.size() - 1);
    }
}
