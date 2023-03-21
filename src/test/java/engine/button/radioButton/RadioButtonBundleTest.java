package engine.button.radioButton;

import app.engine.assets.Assets;
import app.engine.assets.AssetsBean;
import app.engine.button.SimpleButton;
import app.engine.button.radioButton.RadioButton;
import app.engine.button.radioButton.RadioButtonBundle;
import app.engine.display.Display;
import app.engine.display.DisplayBean;
import app.engine.display.DrawableFactory;
import app.engine.display.rectangle.Rectangle;
import app.engine.input.Input;
import app.engine.input.InputBean;
import app.engine.input.inputCombination.InputCombinationFactory;
import app.engine.scene.Scene;
import app.engine.scene.SceneBean;

import java.util.LinkedList;
import java.util.List;

public class RadioButtonBundleTest {

    public static void main(String[] args) {
        new RadioButtonBundleTest().manual_get_bundle_state_test();
    }

    public void manual_get_bundle_state_test() {
        //given
        Assets assets = AssetsBean.getAssets();
        assets.addColor("n1", 0xFFFF0000);
        assets.addColor("n2", 0xFF0000FF);
        Display display = DisplayBean.getDisplay();
        Input input = InputBean.getInput();
        InputCombinationFactory inputCombinationFactory = input.getInputCombinationFactory();
        DrawableFactory drawableFactory = display.getDrawableFactory();
        Rectangle inputButtonRectangle = drawableFactory.makeRectangle(210, 10, 100, 100, "n1");
        Rectangle inputOffRectangle1 = drawableFactory.makeRectangle(10, 10, 100, 100, "n1");
        Rectangle inputOnRectangle1 = drawableFactory.makeRectangle(10, 10, 100, 100, "n2");
        Rectangle inputOffRectangle2 = drawableFactory.makeRectangle(10, 210, 100, 100, "n1");
        Rectangle inputOnRectangle2 = drawableFactory.makeRectangle(10, 210, 100, 100, "n2");
        Rectangle inputOffRectangle3 = drawableFactory.makeRectangle(10, 410, 100, 100, "n1");
        Rectangle inputOnRectangle3 = drawableFactory.makeRectangle(10, 410, 100, 100, "n2");
        RadioButton radioButton1 = new RadioButton(inputOffRectangle1, inputOnRectangle1, inputCombinationFactory.makeLmbCombination());
        RadioButton radioButton2 = new RadioButton(inputOffRectangle2, inputOnRectangle2, inputCombinationFactory.makeLmbCombination());
        List<RadioButton> inputRadioButtonList = new LinkedList<>();
        inputRadioButtonList.add(radioButton1);
        inputRadioButtonList.add(radioButton2);
        RadioButtonBundle radioButtonBundle = new RadioButtonBundle(inputRadioButtonList, false);
        RadioButton radioButton3 = new RadioButton(inputOffRectangle3, inputOnRectangle3, inputCombinationFactory.makeLmbCombination());
        Scene scene = SceneBean.getScene();
        scene.addCollection("c1");
        scene.switchCollection("c1");
        scene.addOnHighest(radioButton1);
        scene.addOnHighest(radioButton2);
        scene.addOnHighest(radioButton3);
        radioButtonBundle.addRadioButton(radioButton3);
        radioButtonBundle.removeRadioButton(radioButton3);
        radioButtonBundle.removeRadioButton(radioButton3);
        radioButtonBundle.addRadioButton(radioButton3);
        radioButtonBundle.addRadioButton(radioButton3);
        SimpleButton button = new SimpleButton(inputButtonRectangle, inputCombinationFactory.makeLmbCombination(), () -> {
            for (Boolean state : radioButtonBundle.getBundleState()) {
                System.out.print((state ? "T" : "F") + " ");
            }
            System.out.println();
        });
        scene.addOnHighest(button);
        //when
        InputBean.getInput().initializeListeners();
        SceneBean.getScene().initializeListeners();
        scene.update();
        //then
        //whenever any radio button is chosen, the other ones should deselect
        //moreover pressing right side button should print current bundle state in console logs (T - is selected, F - is not selected)
    }

}
