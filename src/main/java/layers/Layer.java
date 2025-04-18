package layers;

import org.jetbrains.annotations.Range;

import static java.lang.Integer.MAX_VALUE;

public abstract class Layer implements Renderable {

    private final int width;
    private final int height;
    protected char[][] buffer;

    public Layer(@Range(from = 0, to = MAX_VALUE) int width, @Range(from = 0, to = MAX_VALUE) int height) {
        this.width = width;
        this.height = height;
        buffer = new char[height][width];
    }

    /**
     * Get char from buffer at (x, y) coordinates
     * @param x
     * @param y
     * @return
     */
    public char get(int x, int y) {
        return buffer[y][x];
    }

    /**
     * Set char at buffer at (x, y) coordinates
     * @param x
     * @param y
     * @return
     */
    public void set(int x, int y, char value) {
        buffer[y][x] = value;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
