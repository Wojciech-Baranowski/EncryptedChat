package app;

import app.engine.assets.Assets;
import app.engine.assets.font.Font;
import app.engine.display.Display;
import app.engine.scene.Scene;
import app.files.FileController;
import app.gui.chat.backgrounds.BackgroundController;
import app.gui.chat.buttons.ButtonController;
import app.gui.chat.textFields.TextFieldController;
import app.gui.chat.texts.TextController;

import static app.engine.assets.AssetsBean.getAssets;
import static app.engine.display.DisplayBean.getDisplay;
import static app.engine.scene.SceneBean.getScene;

public class Initializer {

    private static Initializer initializer;
    private final Assets assets;
    private final Display display;
    private final Scene scene;

    private Initializer() {
        assets = getAssets();
        display = getDisplay();
        scene = getScene();
    }

    public static Initializer getInitializer() {
        if (initializer == null) {
            initializer = new Initializer();
        }
        return initializer;
    }

    public void initialize() {
        initializeColors();
        initializeFonts();
        initializeScenes();
        initializeLoginControllers();
        initializeGuiControllers();
        scene.switchCollection("login");
        scene.update();
    }

    private void initializeColors() {
        assets.addColor("black", 0xFF000000);
        assets.addColor("white", 0xFFFFFFFF);
        assets.addColor("green", 0xFF00FF00);
        assets.addColor("darkGreen", 0xFF0E4C05);
        assets.addColor("red", 0xFFAA0707);
        assets.addColor("lighterBlue", 0xFF01BAEF);
        assets.addColor("lightBlue", 0xFF559FFF);
        assets.addColor("lighterGray", 0xFF4F93FF);
        assets.addColor("yellow", 0xFFFFFF00);
        assets.addColor("darkYellow", 0xFFA08F2D);
        assets.addColor("lightGray", 0xFFA8A8A8);
        assets.addColor("gray", 0xFF4378FF);
        assets.addColor("darkGray", 0xFF898989);
        assets.addColor("transparentGray", 0x44444444);
        assets.addColor("transparentBlue", 0x554378FF);
        assets.addColor("transparent", 0xFFFF00FF);
        assets.addColor("blue", 0xFF1F57FF);
        assets.addColor("violet", 0xFF8042FF);
        assets.addColor("pink", 0xFFB477FF);
        assets.addColor("orange", 0xFFFFA82C);
    }

    private void initializeFonts() {
        assets.addFont("HBE24", "/HelveticaBoldExtended24.png", Font.getExtendedAlphabet());
        assets.addFont("HBE32", "/HelveticaBoldExtended32.png", Font.getExtendedAlphabet());
        assets.addFont("HBE48", "/HelveticaBoldExtended48.png", Font.getExtendedAlphabet());
    }

    private void initializeScenes() {
        scene.addCollection("gui");
        scene.addCollection("login");
    }

    private void initializeLoginControllers() {
        scene.switchCollection("login");
        app.gui.login.backgrounds.BackgroundController.getBackgroundController();
        app.gui.login.texts.TextController.getTextController();
        app.gui.login.buttons.ButtonController.getButtonController();
        app.gui.login.textFields.TextFieldController.getTextFieldController();
    }

    private void initializeGuiControllers() {
        scene.switchCollection("gui");
        BackgroundController.getBackgroundController();
        TextController.getTextController();
        ButtonController.getButtonController();
        TextFieldController.getTextFieldController();
        FileController.getFileController();
        //UserController.getReceiversController();
    }

    public static void main(String[] args) {
        new Initializer().initialize();
    }

}