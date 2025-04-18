package other;

import layers.Renderable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.io.IOException;

import static java.lang.Integer.MAX_VALUE;

public class Screen {

    private int width;
    private int height;
    private char[][] buffer;

    // Used to clear output
    private static boolean isWindows;
    private static ProcessBuilder processBuilder;

    static {
        isWindows = System.getProperty("os.name").toLowerCase().contains("windows");
        if (isWindows) {
            processBuilder = new ProcessBuilder("cmd", "/c", "cls").inheritIO();
        }
    }

    /**
     * Constructs a screen instance
     * @param width number of displayed characters in row
     * @param height number of displayed characters in column
     */
    public Screen(@Range(from = 0, to = MAX_VALUE) int width, @Range(from = 0, to = MAX_VALUE) int height) {
        this.width = width;
        this.height = height;
        buffer = new char[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                buffer[i][j] = '.';
            }
        }
    }

    /**
     * Render into buffer before drawing it on a console
     * @param renderable object that will be rendered
     */
    public void render(@NotNull Renderable renderable) {
        renderable.render(this, buffer);
    }

    /**
     * Draws next frame to console
     */
    public void draw() {
        clear();

        for (int y = 0; y < height; y++) {
            System.out.println(buffer[y]);
        }
    }

    /**
     * Clears console output
     */
    private void clear() {
        System.out.println(); // for IDE

        try {
            processBuilder.start().waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
