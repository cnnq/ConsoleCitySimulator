package gui;

import org.jetbrains.annotations.NotNull;
import main.Game;

import javax.swing.*;

public class GameWindow extends JFrame implements Runnable {

    public static final int DEFAULT_WIDTH = 512 + 256;
    public static final int DEFAULT_HEIGHT = 512 + 256;
    public static final int DEFAULT_FPS = 20;

    private Game game;
    private GameMode gameMode;

    private Thread thread;

    private boolean running;


    public GameWindow() {
        super("Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        running = true;

        game = new Game(this);
        setGameMode(new EditMode(game));

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

            game.update(1f / DEFAULT_FPS);
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

    @NotNull
    public GameMode getGameMode() {
        return gameMode;
    }

    private void setGameMode(@NotNull GameMode newGameMode) {
        gameMode = newGameMode;
        setContentPane(newGameMode.getContentPane());
        pack();
    }
}
