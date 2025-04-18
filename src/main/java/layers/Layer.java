package layers;

import org.jetbrains.annotations.Range;

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

    int getWidth();

    int getHeight();
}
