import Layers.TerrainLayer;
import Other.Screen;

public class Game {

    private static final int DEFAULT_WIDTH = 120;
    private static final int DEFAULT_HEIGHT = 28;
    private static final int DEFAULT_FPS = 1;

    private final Screen screen;
    private final int FPS;

    private TerrainLayer terrain;

    public Game() {
        this.FPS = DEFAULT_FPS;
        screen = new Screen(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        terrain = new TerrainLayer(200, 200);
    }

    public void run() {
        final int targetNanos = 1_000_000_000 / FPS;
        boolean running = true;

        long lastTime = System.nanoTime();

        while (running) {

            // update
            // render
            screen.render(terrain);

            // draw
            screen.draw();

            long currentTime = System.nanoTime();

            // wait
            try {
                Thread.sleep((targetNanos - currentTime + lastTime) / 1_000_000);
            } catch (InterruptedException e) { }

            lastTime += targetNanos;
        }
    }
}
