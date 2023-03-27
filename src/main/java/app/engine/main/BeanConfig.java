package app.engine.main;

import app.engine.assets.AssetsBean;
import app.engine.display.DisplayBean;
import app.engine.input.InputBean;
import app.engine.listener.ListenerBean;
import app.engine.scene.SceneBean;

public class BeanConfig {

    BeanConfig() {
    }

    void buildBeans() {
        AssetsBean.getAssets();
        DisplayBean.getDisplay();
        InputBean.getInput();
        SceneBean.getScene();
        ListenerBean.getListener();
    }

}
