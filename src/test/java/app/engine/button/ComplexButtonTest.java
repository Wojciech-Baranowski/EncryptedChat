package app.engine.button;

import app.engine.assets.Assets;
import app.engine.assets.AssetsBean;
import app.engine.common.Command;
import app.engine.display.Display;
import app.engine.display.DisplayBean;
import app.engine.display.Drawable;
import app.engine.display.DrawableFactory;
import app.engine.display.rectangle.Rectangle;
import app.engine.input.Input;
import app.engine.input.InputBean;
import app.engine.input.inputCombination.ActionType;
import app.engine.input.inputCombination.InputCombination;
import app.engine.input.inputCombination.InputCombinationFactory;
import app.engine.input.inputCombination.InputElement;
import app.engine.scene.Scene;
import app.engine.scene.SceneBean;
import org.junit.Test;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import static org.junit.Assert.assertSame;

public class ComplexButtonTest {

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
        new ComplexButtonTest().update_manual_test();
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
        InputEvent inputEvent  = InputElement.getKeyboardInputEventByKeycode(KeyEvent.VK_E);
        InputCombination inputCombination1 = inputCombinationFactory.makeSimpleInputCombination(ActionType.DOWN, inputEvent);
        InputCombination inputCombination2 = inputCombinationFactory.makeLmbCombination();
        InputCombination[] inputCombinations = new InputCombination[]{inputCombination1, inputCombination2};
        Command[] commands = new Command[]{() -> System.out.println("Hellothere!"), () -> System.out.println("General Kenobi!!!!")};
        ComplexButton complexButton = new ComplexButton(inputRectangle, inputCombinations, commands);
        Scene scene = SceneBean.getScene();
        scene.addCollection("c1");
        scene.switchCollection("c1");
        scene.addOnHighest(complexButton);
        //when
        scene.initializeListeners();
        input.initializeInputListener();
        scene.update();
        //then
        //"Hellothere!" should appear in console logs after pressing 'E' and
        //"General Kenobi!!!! should appear in console logs after pressing left mouse button"
    }

}
