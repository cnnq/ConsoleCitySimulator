import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.swing.TerminalEmulatorAutoCloseTrigger;
import layers.TerrainLayer;

import java.io.IOException;

public class Game {

    private static final int DEFAULT_WIDTH = 120;
    private static final int DEFAULT_HEIGHT = 40;
    private static final int DEFAULT_FPS = 1;

    private Screen screen;

    private TerrainLayer terrain;

    public Game() {

        try {
            screen = new DefaultTerminalFactory()
                    .setInitialTerminalSize(new TerminalSize(DEFAULT_WIDTH, DEFAULT_HEIGHT))
                    .setTerminalEmulatorFrameAutoCloseTrigger(TerminalEmulatorAutoCloseTrigger.CloseOnEscape)
                    .createScreen();

        } catch (IOException e) {
            e.printStackTrace();
        }

        terrain = new TerrainLayer(200, 200, 10, 32);
    }

    /**
     * Run game
     */
    public void run() {
        final int targetNanos = 1_000_000_000 / DEFAULT_FPS;
        boolean running = true;

        // start
        try {
            screen.startScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }

        long lastTime = System.nanoTime();

        // run
        while (running) {

            // update

            try {

                screen.doResizeIfNecessary();

                // render
                terrain.render(screen);

                // display
                screen.refresh();

            } catch (IOException e) {
                e.printStackTrace();
            }

            long currentTime = System.nanoTime();

            // wait
            try {
                Thread.sleep((targetNanos - currentTime + lastTime) / 1_000_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            lastTime += targetNanos;
        }

        // stop
        try {
            screen.stopScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
