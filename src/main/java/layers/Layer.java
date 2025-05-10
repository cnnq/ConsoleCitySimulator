package layers;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.awt.*;

public interface Layer<T> {

    /**
     * Draws current layer
     * @param xOffset x tile coordinate
     * @param yOffset y tile coordinate
     * @param width width of target image in pixels
     * @param height height of target image in pixels
     */
    void draw(Graphics g, int xOffset, int yOffset, int width, int height);

    /**
     * Edit layer using selected area and key state
     * @param rectangle selected area
     * @param button value returned by {@code MouseEvent.getButton()}
     * @return true if edited successfully
     */
    boolean edit(@NotNull Rectangle rectangle, int button);


    /**
     * Get value from buffer at (x, y) coordinates
     * @param x tile coordinate
     * @param y tile coordinate
     * @return value
     */
    T get(@Range(from = 0, to = Integer.MAX_VALUE) int x, @Range(from = 0, to = Integer.MAX_VALUE) int y);

    /**
     * Set value at (x, y) coordinates
     * @param x tile coordinate
     * @param y tile coordinate
     * @param value value to be set in
     */
    void set(@Range(from = 0, to = Integer.MAX_VALUE) int x, @Range(from = 0, to = Integer.MAX_VALUE) int y, T value);


    /**
     * Fills buffer within {@code rectangle} with {@code value}
     * @param rectangle area to be filled (including bottom right corner)
     * @param value value to be filled in
     */
    default void fill(@NotNull Rectangle rectangle, T value){
        for (int x = rectangle.x; x <= rectangle.x + rectangle.width; x++) {
            for (int y = rectangle.y; y <= rectangle.y + rectangle.height; y++) {
                set(x, y, value);
            }
        }
    }

    /**
     * Replaces all instances of {@code oldValue} with {@code newValue}
     * inside area defined by {@code rectangle}
     * @param rectangle area to be replaced (including bottom right corner)
     * @param oldValue value to be replaced
     * @param newValue value to be filled in
     * @return number of replaced fields
     */
    default int replace(@NotNull Rectangle rectangle, T oldValue, T newValue) {
        int count = 0;
        for (int x = rectangle.x; x <= rectangle.x + rectangle.width; x++) {
            for (int y = rectangle.y; y <= rectangle.y + rectangle.height; y++) {
                if (get(x, y) == oldValue) {
                    set(x, y, newValue);
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Counts all instances of {@code value}
     * inside area defined by {@code rectangle}
     * @param rectangle area to be counted on
     * @param value value to be counted
     * @return number of instances of {@code value}
     */
    default int count(@NotNull Rectangle rectangle, T value) {
        int count = 0;
        for (int x = rectangle.x; x <= rectangle.x + rectangle.width; x++) {
            for (int y = rectangle.y; y <= rectangle.y + rectangle.height; y++) {
                if (get(x, y) == value) count++;
            }
        }
        return count;
    }

    /**
     * Checks if given and adjacent tiles are of specified type
     * @param x tile coordinate
     * @param y tile coordinate
     * @param tileType tile type to be checked
     * @return true if neighbours
     */
    default boolean neighbours(@Range(from = 0, to = Integer.MAX_VALUE) int x, @Range(from = 0, to = Integer.MAX_VALUE) int y, T tileType) {
        return (get(x, y) == tileType ||
                x > 0 && get(x - 1, y) == tileType ||
                x + 1 < getWidth() && get(x + 1, y) == tileType ||
                y > 0 && get(x, y - 1) == tileType ||
                y + 1 < getHeight() && get(x, y + 1) == tileType);
    }

    int getWidth();

    int getHeight();
}
