package app.engine.scene;

import app.engine.common.Interactive;
import app.engine.common.Observer;
import app.engine.common.Visual;
import app.engine.input.inputCombination.InputCombination;

import java.util.Collection;

public interface Scene extends Observer {

    void update();

    void addObjectLowerThan(Visual inserted, Visual contained);

    void addObjectHigherThan(Visual inserted, Visual contained);

    void addOnHighest(Visual inserted);

    void addOnLowest(Visual inserted);

    void clear();

    void removeObject(Visual removed);

    Collection<Visual> getCurrentObjectCollection();

    Visual getTopObject();

    void switchCollection(String collectionName);

    void addCollection(String collectionName);

    void removeCollection(String collectionName);

    void initializeListeners();

    void addGloballyActivatedObject(InputCombination activationCombination, Interactive object);

    void removeGloballyActivatedObject(Interactive object);

}
