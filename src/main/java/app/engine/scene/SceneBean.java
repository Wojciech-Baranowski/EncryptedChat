package app.engine.scene;

import app.engine.common.Interactive;
import app.engine.common.Visual;
import app.engine.display.Display;
import app.engine.display.DisplayBean;
import app.engine.display.HoverMark;
import app.engine.input.Input;
import app.engine.input.InputBean;
import app.engine.input.inputCombination.InputCombination;
import app.engine.scene.priorityCollection.PriorityList;
import app.engine.scene.priorityCollection.SceneCollection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SceneBean implements Scene {

    private static SceneBean scene;
    private final Display display;
    private final Input input;
    private final Map<String, SceneCollection> objectCollections;
    private SceneCollection currentObjectCollection;

    private SceneBean() {
        this.display = DisplayBean.getDisplay();
        this.input = InputBean.getInput();
        this.objectCollections = new HashMap<>();
    }

    public static Scene getScene() {
        if (scene == null) {
            scene = new SceneBean();
        }
        return scene;
    }

    @Override
    public void update() {
        if (this.currentObjectCollection != null) {
            updateTopObject();
            this.currentObjectCollection.updateGloballyActivatedObjects();
            this.display.setObjectsToDraw(this.scene.getCurrentObjectCollection());
            this.display.draw();
        }
    }

    private void updateTopObject() {
        if (this.currentObjectCollection != null) {
            this.currentObjectCollection.remove(HoverMark.getHoverMark());
        }
        Visual topObject = getTopObject();
        if (topObject instanceof Interactive) {
            ((Interactive) topObject).update();
            HoverMark.getHoverMark().fitHoverMarkToDrawable(topObject.getDrawable());
            this.currentObjectCollection.setHigherThan(HoverMark.getHoverMark(), topObject);
        }
    }

    @Override
    public void initializeListeners() {
        this.input.addInputListener(this);
    }

    @Override
    public void addObjectLowerThan(Visual inserted, Visual contained) {
        this.currentObjectCollection.setLowerThan(inserted, contained);
    }

    @Override
    public void addObjectHigherThan(Visual inserted, Visual contained) {
        this.currentObjectCollection.setHigherThan(inserted, contained);
    }

    @Override
    public void addOnHighest(Visual inserted) {
        this.currentObjectCollection.setOnHighest(inserted);
    }

    @Override
    public void addOnLowest(Visual inserted) {
        this.currentObjectCollection.setOnLowest(inserted);
    }

    @Override
    public void clear() {
        this.currentObjectCollection.clear();
    }

    @Override
    public void removeObject(Visual removed) {
        this.currentObjectCollection.remove(removed);
    }

    @Override
    public Collection<Visual> getCurrentObjectCollection() {
        if (currentObjectCollection == null) {
            return null;
        }
        return currentObjectCollection.getObjectCollection();
    }

    @Override
    public Visual getTopObject() {
        int x = input.getMouseX();
        int y = input.getMouseY();
        if (this.currentObjectCollection != null) {
            return this.currentObjectCollection.getTopObjectOnPosition(x, y);
        }
        return null;
    }

    @Override
    public void switchCollection(String collectionName) {
        this.currentObjectCollection = this.objectCollections.get(collectionName);
    }

    @Override
    public void addCollection(String collectionName) {
        this.objectCollections.put(collectionName, new SceneCollection(new PriorityList()));
    }

    @Override
    public void removeCollection(String collectionName) {
        if (!this.objectCollections.containsKey(collectionName)) {
            return;
        }
        if (this.currentObjectCollection != null && currentObjectCollection.equals(this.objectCollections.get(collectionName))) {
            this.currentObjectCollection.clear();
            this.currentObjectCollection = null;
        }
        this.objectCollections.get(collectionName).clear();
        this.objectCollections.remove(collectionName);
    }

    @Override
    public void addGloballyActivatedObject(InputCombination activationCombination, Interactive object) {
        this.currentObjectCollection.addGloballyActivatedObject(activationCombination, object);
    }

    @Override
    public void removeGloballyActivatedObject(Interactive object) {
        this.currentObjectCollection.removeGloballyActivatedObject(object);
    }

}
