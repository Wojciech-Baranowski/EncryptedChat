package app.engine.scene.priorityCollection;

import app.engine.common.Interactive;
import app.engine.common.Visual;
import app.engine.input.inputCombination.InputCombination;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SceneCollection {

    private final PriorityCollection priorityCollection;

    private final Map<InputCombination, Interactive> globallyActivatedObjects;

    public SceneCollection(PriorityCollection priorityCollection) {
        this.priorityCollection = priorityCollection;
        this.globallyActivatedObjects = new HashMap<>();
    }

    public Visual getTopObjectOnPosition(int x, int y) {
        Collection<Object> objects = this.priorityCollection.getObjectCollection();
        Visual result = null;
        for (Object object : objects) {
            Visual visual = (Visual) object;
            if (visual.getDrawable().inBorders(x, y) && !visual.getDrawable().isPixelOnPositionTransparent(x, y)) {
                result = visual;
            }
        }
        return result;
    }

    public void setLowerThan(Visual inserted, Visual contained) {
        this.priorityCollection.setLowerThan(inserted, contained);
    }

    public void setHigherThan(Visual inserted, Visual contained) {
        this.priorityCollection.setHigherThan(inserted, contained);
    }

    public void setOnLowest(Visual inserted) {
        this.priorityCollection.setOnLowest(inserted);
    }

    public void setOnHighest(Visual inserted) {
        this.priorityCollection.setOnHighest(inserted);
    }

    public void clear() {
        this.priorityCollection.clear();
    }

    public void remove(Visual removed) {
        this.priorityCollection.remove(removed);
    }

    public Collection<Visual> getObjectCollection() {
        return (Collection) this.priorityCollection.getObjectCollection();
    }

    public Visual getLowest() {
        return (Visual) this.priorityCollection.getLowest();
    }

    public Visual getHighest() {
        return (Visual) this.priorityCollection.getHighest();
    }

    public void updateGloballyActivatedObjects() {
        for (InputCombination activationCombination : this.globallyActivatedObjects.keySet()) {
            if (activationCombination.isActive()) {
                this.globallyActivatedObjects.get(activationCombination).update();
            }
        }
    }

    public void addGloballyActivatedObject(InputCombination activationCombination, Interactive object) {
        this.globallyActivatedObjects.put(activationCombination, object);
    }

    public void removeGloballyActivatedObject(Interactive object) {
        this.globallyActivatedObjects.values().remove(object);
    }

}
