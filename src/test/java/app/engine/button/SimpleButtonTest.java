package app.engine.button;

import app.engine.assets.Assets;
import app.engine.assets.AssetsBean;
import app.engine.display.Display;
import app.engine.display.DisplayBean;
import app.engine.display.Drawable;
import app.engine.display.DrawableFactory;
import app.engine.display.rectangle.Rectangle;
import app.engine.input.Input;
import app.engine.input.InputBean;
import app.engine.input.inputCombination.ActionType;
import app.engine.input.inputCombination.InputCombinationFactory;
import app.engine.input.inputCombination.InputElement;
import app.engine.scene.Scene;
import app.engine.scene.SceneBean;
import org.junit.Test;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static app.engine.input.inputCombination.ActionType.DOWN;
import static org.junit.Assert.assertSame;

public class SimpleButtonTest {

    @Test
    public void get_drawable_test() {
        //given
        Assets assets = AssetsBean.getAssets();
        assets.addColor("n1", 0xFFFFFFFF);
        Display display = DisplayBean.getDisplay();
        DrawableFactory drawableFactory = display.getDrawableFactory();
        Rectangle inputRectangle = drawableFactory.makeRectangle(10, 10, 10, 10, "n1");
        SimpleButton simpleButton = new SimpleButton(inputRectangle, null, null);
        Drawable output;
        //when
        output = simpleButton.getDrawable();
        //then
        assertSame(inputRectangle, output);
    }

    public static void main(String[] args) {
        SimpleButtonTest simpleButtonTest = new SimpleButtonTest();
        simpleButtonTest.update_manual_test();
    }

    public void update_manual_test() {
        //given
        Assets assets = AssetsBean.getAssets();
        assets.addColor("n1", 0xFFFF0000);
        Display display = DisplayBean.getDisplay();
        Input input = InputBean.getInput();
        InputCombinationFactory inputCombinationFactory = input.getInputCombinationFactory();
        DrawableFactory drawableFactory = display.getDrawableFactory();
        Rectangle inputRectangle = drawableFactory.makeRectangle(10, 10, 100, 100, "n1");
        ActionType[] inputActions = new ActionType[]{DOWN, DOWN};
        InputEvent[] inputEvents = new InputEvent[]{InputElement.getKeyboardInputEventByKeycode(KeyEvent.VK_E), InputElement.getMouseInputEventByKeycode(MouseEvent.BUTTON1)};
        SimpleButton simpleButton = new SimpleButton(inputRectangle, inputCombinationFactory.makeComplexInputCombination(inputActions, inputEvents), () -> System.out.println("Hellothere!"));
        Scene scene = SceneBean.getScene();
        scene.addCollection("c1");
        scene.switchCollection("c1");
        scene.addOnHighest(simpleButton);
        //when
        scene.initializeListeners();
        input.initializeListeners();
        scene.update();
        //then
        //"Hellothere!" should appear in console logs after pressing left mouse button
    }

}
