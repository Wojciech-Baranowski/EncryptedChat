package app.engine.button.checkbox;

import app.engine.assets.Assets;
import app.engine.assets.AssetsBean;
import app.engine.display.Display;
import app.engine.display.DisplayBean;
import app.engine.display.Drawable;
import app.engine.display.DrawableFactory;
import app.engine.display.rectangle.Rectangle;
import app.engine.input.Input;
import app.engine.input.InputBean;
import app.engine.input.inputCombination.InputCombinationFactory;
import app.engine.scene.Scene;
import app.engine.scene.SceneBean;
import org.junit.Test;

import static org.junit.Assert.assertSame;

public class CheckboxTest {

    @Test
    public void get_drawable_test() {
        //given
        Assets assets = AssetsBean.getAssets();
        assets.addColor("n1", 0xFFFFFFFF);
        assets.addColor("n2", 0xFF000000);
        Display display = DisplayBean.getDisplay();
        DrawableFactory drawableFactory = display.getDrawableFactory();
        Rectangle inputOffRectangle = drawableFactory.makeRectangle(10, 10, 10, 10, "n1");
        Rectangle inputOnRectangle = drawableFactory.makeRectangle(10, 10, 10, 10, "n2");
        Checkbox checkbox = new Checkbox(inputOffRectangle, inputOnRectangle, null);
        Drawable output;
        //when
        output = checkbox.getDrawable();
        //then
        assertSame(inputOffRectangle, output);
    }

    public static void main(String[] args) {
        new CheckboxTest().manual_update_test();
    }

    public void manual_update_test() {
        //given
        Assets assets = AssetsBean.getAssets();
        assets.addColor("n1", 0xFFFF0000);
        assets.addColor("n2", 0xFF0000FF);
        Display display = DisplayBean.getDisplay();
        Input input = InputBean.getInput();
        InputCombinationFactory inputCombinationFactory = input.getInputCombinationFactory();
        DrawableFactory drawableFactory = display.getDrawableFactory();
        Rectangle inputOffRectangle = drawableFactory.makeRectangle(10, 10, 100, 100, "n1");
        Rectangle inputOnRectangle = drawableFactory.makeRectangle(10, 10, 100, 100, "n2");
        Checkbox checkbox = new Checkbox(inputOffRectangle, inputOnRectangle, inputCombinationFactory.makeLmbCombination());
        Scene scene = SceneBean.getScene();
        scene.addCollection("c1");
        scene.switchCollection("c1");
        scene.addOnHighest(checkbox);
        //when
        scene.initializeListeners();
        input.initializeInputListener();
        scene.update();
        //then
        //after each left click on square it should alternate between red and blue colors, starting from red
    }

}
