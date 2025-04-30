package other;

import javax.swing.*;
import java.awt.*;

public class Game extends JFrame implements Runnable {

    public static final int DEFAULT_WIDTH = 600;
    public static final int DEFAULT_HEIGHT = 600;
    public static final int DEFAULT_FPS = 20;

    private boolean running;

    private final MapPanel map;

    public Game() {
        super("Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        running = true;

        // Layout
        setLayout(new FlowLayout());

        JLabel top = new JLabel("Top", JLabel.CENTER);
        top.setSize(new Dimension(DEFAULT_WIDTH, 40));

        map = new MapPanel();

        JLabel bottom = new JLabel("Bottom", JLabel.CENTER);
        top.setSize(new Dimension(DEFAULT_WIDTH, 40));

        add(top);
        add(map);
        add(bottom);

        // Show
        setVisible(true);
    }

    @Override
    public void run() {
        // Repaint frame every 1/FPS ms

        final int targetNanos = 1_000_000_000 / DEFAULT_FPS;

        long lastTime = System.nanoTime();

        // run
        while (running) {
            repaint();

            long currentTime = System.nanoTime();

            // wait
            try {
                Thread.sleep((targetNanos - currentTime + lastTime) / 1_000_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                running = false;
                break;
            }

            lastTime += targetNanos;
        }
    }
}
