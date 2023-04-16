package app;

import app.connection.ConnectionController;
import app.encryption.Rsa;
import app.encryption.Sha256;
import app.engine.assets.Assets;
import app.engine.assets.font.Font;
import app.engine.display.Display;
import app.engine.scene.Scene;
import app.gui.chat.backgrounds.ChatBackgroundController;
import app.gui.chat.buttons.ChatButtonController;
import app.gui.chat.textFields.ChatTextFieldController;
import app.gui.chat.texts.ChatTextController;
import app.gui.login.backgrounds.LoginBackgroundController;
import app.gui.login.buttons.LoginButtonController;
import app.gui.login.textFields.LoginTextFieldController;
import app.gui.login.texts.LoginTextController;
import app.services.FileService;
import app.services.UserService;

import static app.engine.assets.AssetsBean.getAssets;
import static app.engine.display.DisplayBean.getDisplay;
import static app.engine.scene.SceneBean.getScene;

public class Initializer {

    private static Initializer initializer;
    private final Assets assets;
    private final Display display;
    private final Scene scene;

    private Initializer() {
        this.assets = getAssets();
        this.display = getDisplay();
        this.scene = getScene();
    }

    public static Initializer getInitializer() {
        if (initializer == null) {
            initializer = new Initializer();
        }
        return initializer;
    }

    public void initialize(String keyPassword) {
        byte[] keyPasswordHash = Sha256.hash(keyPassword);
        if (Rsa.isKeyValid(keyPasswordHash)) {
            Rsa.initialize(keyPasswordHash);
            initializeColors();
            initializeFonts();
            initializeScenes();
            initializeGlobalControllers();
            initializeLoginControllers();
            initializeGuiControllers();
            this.scene.switchCollection("login");
            this.scene.update();
        }
    }

    private void initializeColors() {
        this.assets.addColor("black", 0xFF000000);
        this.assets.addColor("white", 0xFFFFFFFF);
        this.assets.addColor("green", 0xFF00FF00);
        this.assets.addColor("darkGreen", 0xFF0E4C05);
        this.assets.addColor("red", 0xFFAA0707);
        this.assets.addColor("lighterBlue", 0xFF01BAEF);
        this.assets.addColor("lightBlue", 0xFF559FFF);
        this.assets.addColor("lighterGray", 0xFF4F93FF);
        this.assets.addColor("yellow", 0xFFFFFF00);
        this.assets.addColor("darkYellow", 0xFFA08F2D);
        this.assets.addColor("lightGray", 0xFFA8A8A8);
        this.assets.addColor("gray", 0xFF4378FF);
        this.assets.addColor("darkGray", 0xFF898989);
        this.assets.addColor("transparentGray", 0x44444444);
        this.assets.addColor("transparentBlue", 0x554378FF);
        this.assets.addColor("transparent", 0xFFFF00FF);
        this.assets.addColor("blue", 0xFF1F57FF);
        this.assets.addColor("violet", 0xFF8042FF);
        this.assets.addColor("pink", 0xFFB477FF);
        this.assets.addColor("orange", 0xFFFFA82C);
    }

    private void initializeFonts() {
        this.assets.addFont("HBE24", "/HelveticaBoldExtended24.png", Font.getExtendedAlphabet());
        this.assets.addFont("HBE32", "/HelveticaBoldExtended32.png", Font.getExtendedAlphabet());
        this.assets.addFont("HBE48", "/HelveticaBoldExtended48.png", Font.getExtendedAlphabet());
    }

    private void initializeScenes() {
        this.scene.addCollection("chat");
        this.scene.addCollection("login");
        this.scene.addCollection("noConnection");
    }

    private void initializeGlobalControllers() {
        ConnectionController.getConnectionController();
    }

    private void initializeLoginControllers() {
        this.scene.switchCollection("login");
        LoginBackgroundController.getLoginBackgroundController();
        LoginTextController.getLoginTextController();
        LoginButtonController.getLoginButtonController();
        LoginTextFieldController.getLoginTextFieldController();
    }

    private void initializeGuiControllers() {
        this.scene.switchCollection("chat");
        ChatBackgroundController.getChatBackgroundController();
        ChatTextController.getChatTextController();
        ChatButtonController.getChatButtonController();
        ChatTextFieldController.getChatTextFieldController();
        FileService.getFileService();
        UserService.getUserService();
    }

    public static void main(String[] args) {
        new Initializer().initialize(args[0]);
    }

}