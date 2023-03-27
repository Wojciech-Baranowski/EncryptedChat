package app.engine.display;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.EventListener;

public class Window {

    private final BufferStrategy bufferStrategy;
    private BufferedImage windowImage;
    private final Canvas canvas;
    private final Graphics2D graphics2D;
    private static final int x = 0;
    private static final int y = 0;
    static final int w = 800;
    static final int h = 640;

    Window() {
        this.canvas = createCanvas();
        createWindow();
        this.windowImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        this.bufferStrategy = this.canvas.getBufferStrategy();
        this.graphics2D = (Graphics2D) this.bufferStrategy.getDrawGraphics();
        wait(420);
    }

    void draw(int[] pixels) {
        this.windowImage.setRGB(0, 0, w, h, pixels, 0, w);
        this.graphics2D.drawImage(this.windowImage, x, y, w, h, null);
        this.bufferStrategy.show();
    }

    void addListener(EventListener eventListener) {
        if (eventListener instanceof KeyListener) {
            this.canvas.addKeyListener((KeyListener) (eventListener));
        }
        if (eventListener instanceof MouseMotionListener) {
            this.canvas.addMouseMotionListener((MouseMotionListener) (eventListener));
        }
        if (eventListener instanceof MouseListener) {
            this.canvas.addMouseListener((MouseListener) (eventListener));
        }
    }

    private void createWindow() {
        JFrame jFrame = new JFrame("Palinka <3");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLayout(new BorderLayout());
        jFrame.add(this.canvas, BorderLayout.CENTER);
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
    }

    private Canvas createCanvas() {
        Canvas canvas = new Canvas();
        Dimension s = new Dimension(w, h);
        canvas.setPreferredSize(s);
        canvas.setMaximumSize(s);
        canvas.setMinimumSize(s);
        canvas.createBufferStrategy(1);
        return canvas;
    }

    private void wait(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}



