package app.engine.common;

public interface Command {

    void execute();

    Command BLANK = () -> {
    };

}
