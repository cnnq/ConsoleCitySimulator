package Layers;

import org.jetbrains.annotations.Range;

import static java.lang.Integer.MAX_VALUE;

public abstract class Layer implements Renderable {

    private int width;
    private int height;
    protected char[][] buffer;

    public Layer(@Range(from = 0, to = MAX_VALUE) int width, @Range(from = 0, to = MAX_VALUE) int height) {
        this.width = width;
        this.height = height;
        buffer = new char[height][width];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
