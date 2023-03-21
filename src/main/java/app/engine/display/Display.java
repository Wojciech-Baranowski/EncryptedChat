package app.engine.display;

import app.engine.common.Visual;

import java.util.Collection;
import java.util.EventListener;

public interface Display {

    void draw();

    void setObjectsToDraw(Collection<Visual> objects);

    void addWindowListener(EventListener listener);

    DrawableFactory getDrawableFactory();

}
