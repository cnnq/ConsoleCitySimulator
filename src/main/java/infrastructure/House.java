package infrastructure;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;
import other.Sprite;

import java.awt.*;

public class House extends Building {

    public static final House HOUSE_1 = new House(Sprite.HOUSE_1, Color.RED, DEFAULT_HOUSING_AREA_PRICE, 1, 1, 4);
    public static final House HOUSE_2 = new House(Sprite.HOUSE_2, Color.RED, DEFAULT_HOUSING_AREA_PRICE, 2, 2, 6);


    private int capacity;

    /**
     * Create instance of house
     * @param sprite
     * @param defaultColor color to display if spritemap is null, null if transparent
     * @param buildingCost cost of building
     * @param waterUsage usage of water, negative values mean production
     * @param electricityUsage usage of electricity, negative values mean production
     * @param capacity maximum number of inhabitants
     */
    protected House(@Nullable Sprite sprite,
                     @Nullable Color defaultColor,
                     double buildingCost, double waterUsage, double electricityUsage,
                     @Range(from = 0, to = Integer.MAX_VALUE) int capacity) {

        super(sprite, defaultColor, buildingCost, waterUsage, electricityUsage);

        this.capacity = capacity;
    }

    /**
     * Returns maximum number of inhabitants
     */
    @Range(from = 0, to = Integer.MAX_VALUE)
    public int getCapacity() {
        return capacity;
    }
}
