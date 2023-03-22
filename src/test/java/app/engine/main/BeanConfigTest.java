package app.engine.main;

import app.engine.assets.Assets;
import app.engine.assets.AssetsBean;
import app.engine.display.Display;
import app.engine.display.DisplayBean;
import app.engine.input.Input;
import app.engine.input.InputBean;
import app.engine.scene.Scene;
import app.engine.scene.SceneBean;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

public class BeanConfigTest {

    @Test
    public void build_beans_test() {
        //given
        Assets assets;
        Display display;
        Input input;
        Scene scene;
        BeanConfig beanConfig = new BeanConfig();
        //when
        beanConfig.buildBeans();
        scene = SceneBean.getScene();
        display = DisplayBean.getDisplay();
        assets = AssetsBean.getAssets();
        input = InputBean.getInput();
        //then
        assertNotNull(assets);
        assertNotNull(display);
        assertNotNull(input);
        assertNotNull(scene);
        assertSame(assets, AssetsBean.getAssets());
        assertSame(display, DisplayBean.getDisplay());
        assertSame(input, InputBean.getInput());
        assertSame(scene, SceneBean.getScene());
    }

}
