import app.Initializer;
import engine.main.Engine;

public class Main {

    public static void main(String[] args) {
        Engine.getEngine().initializeEngine();
        Initializer.getInitializer().initialize();
        Engine.getEngine().initializeListeners();
    }

}