package layers;

import org.jetbrains.annotations.Range;

import java.awt.*;

public interface Layer<T> {

    /**
     * Get T from buffer at (x, y) coordinates
     * @param x coord
     * @param y coord
     * @return T
     */
    T get(@Range(from = 0, to = Integer.MAX_VALUE) int x, @Range(from = 0, to = Integer.MAX_VALUE) int y);

    /**
     * Set T at buffer at (x, y) coordinates
     * @param x coord
     * @param y coord
     */
    void set(@Range(from = 0, to = Integer.MAX_VALUE) int x, @Range(from = 0, to = Integer.MAX_VALUE) int y, T value);

    /**
     * Draws current layer
     * @param xOffset x layer coordinate from which start drawing
     * @param yOffset y layer coordinate from which start drawing
     * @param width width of target image
     * @param height height of target image
     */
    void draw(Graphics g, int xOffset, int yOffset, int width, int height);

    int getWidth();

    int getHeight();
}
