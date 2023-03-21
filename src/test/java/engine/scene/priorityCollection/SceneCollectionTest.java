package engine.scene.priorityCollection;

import app.engine.assets.Assets;
import app.engine.assets.AssetsBean;
import app.engine.display.Display;
import app.engine.display.DisplayBean;
import app.engine.display.DrawableFactory;
import app.engine.display.rectangle.Rectangle;
import app.engine.scene.priorityCollection.PriorityList;
import app.engine.scene.priorityCollection.SceneCollection;
import org.junit.Test;

import static org.junit.Assert.assertSame;

public class SceneCollectionTest {

    @Test
    public void get_top_object_on_position_test() {
        //given
        Assets assets = AssetsBean.getAssets();
        assets.addColor("red", 0xFFFF0000);
        assets.addColor("magenta", 0xFFFF00FF);
        Display display = DisplayBean.getDisplay();
        DrawableFactory drawableFactory = display.getDrawableFactory();
        Rectangle rectangle1 = drawableFactory.makeRectangle(0, 0, 100, 100, "red");
        Rectangle rectangle2 = drawableFactory.makeRectangle(50, 0, 100, 100, "red");
        Rectangle rectangle3 = drawableFactory.makeRectangle(20, 0, 20, 20, "magenta");
        SceneCollection priorityCollection = new SceneCollection(new PriorityList());
        priorityCollection.setOnHighest(rectangle1);
        priorityCollection.setOnHighest(rectangle2);
        priorityCollection.setOnHighest(rectangle3);
        int[] inputX = {10, 140, 90, 300, 30};
        int[] inputY = {50, 50, 50, 50, 30};
        Rectangle[] outputCheck = new Rectangle[]{rectangle1, rectangle2, rectangle2, null, rectangle1};
        Rectangle[] output = new Rectangle[inputX.length];
        //when
        for (int i = 0; i < inputX.length; i++) {
            output[i] = (Rectangle) priorityCollection.getTopObjectOnPosition(inputX[i], inputY[i]);
        }
        //then
        for (int i = 0; i < inputX.length; i++) {
            assertSame(outputCheck[i], output[i]);
        }
    }

}
