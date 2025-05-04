package other;

import org.jetbrains.annotations.NotNull;
import states.EditMode;
import states.GameMode;

import javax.swing.*;
import java.awt.*;

/**
 * Instance of one game session
 */
public class Game extends JFrame implements Runnable {

    public static final int DEFAULT_WIDTH = 512;
    public static final int DEFAULT_HEIGHT = 512;
    public static final int DEFAULT_FPS = 20;

    // Use changeGameMode() method to change
    private GameMode gameMode;

    private Thread thread;

    private boolean running;


    public Game() {
        super("Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        running = true;

        changeGameMode(new EditMode());

        // Show
        setVisible(true);

        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        // Repaint frame every 1/FPS ms

        final int targetNanos = 1_000_000_000 / DEFAULT_FPS;

        long lastTime = System.nanoTime();

        // run
        while (running) {

            GameState.update(1f / DEFAULT_FPS);
            repaint();

            long currentTime = System.nanoTime();

            // wait
            try {
                long timeToWait = (targetNanos - currentTime + lastTime) / 1_000_000;
                if (timeToWait > 0) Thread.sleep(timeToWait);

            } catch (InterruptedException e) {
                e.printStackTrace();
                running = false;
                break;
            }

            lastTime += targetNanos;
        }
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.PINK);
        g.clearRect(0, 0, WIDTH, HEIGHT);
        super.paint(g);
    }

    private void changeGameMode(@NotNull GameMode newGameMode) {
        gameMode = newGameMode;
        setContentPane(newGameMode.getContentPane());
        pack();
    }
}
