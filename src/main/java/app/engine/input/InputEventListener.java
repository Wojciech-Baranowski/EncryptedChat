package app.engine.input;

import app.engine.listener.SynchronizedCollection;

import java.awt.event.*;

public class InputEventListener implements KeyListener, MouseListener, MouseMotionListener {

    private static final long MOUSE_MOVE_INFO_INTERVAL = 50;
    public long lastMouseMoveTimestamp;

    private SynchronizedCollection<InputEvent> events;


    public InputEventListener() {
    }

    public void listen(SynchronizedCollection<InputEvent> events) {
        this.events = events;
        this.lastMouseMoveTimestamp = System.currentTimeMillis();
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        this.events.put(keyEvent);
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        this.events.put(keyEvent);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        this.events.put(mouseEvent);
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        this.events.put(mouseEvent);
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        if (System.currentTimeMillis() - this.lastMouseMoveTimestamp > MOUSE_MOVE_INFO_INTERVAL) {
            this.lastMouseMoveTimestamp = System.currentTimeMillis();
            this.events.put(mouseEvent);
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        //Not supported
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        //Not supported
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
        //Not supported
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        //Not supported
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        //Not supported
    }

}
