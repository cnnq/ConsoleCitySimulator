package infrastructure;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;
import other.Sprite;

import java.awt.*;

public class House extends UnmanagedBuilding {

    public static final House HOUSE_1 = new House(Sprite.HOUSE_1, Color.RED, DEFAULT_HOUSING_AREA_PRICE, 1, 1, 4);
    public static final House HOUSE_2 = new House(Sprite.HOUSE_2, Color.RED, DEFAULT_HOUSING_AREA_PRICE, 2, 2, 6);


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
                     double buildingCost,
                     @Range(from = 0, to = Integer.MAX_VALUE) int capacity,
                     double waterUsage, double electricityUsage) {

        super(sprite, defaultColor, buildingCost, capacity, waterUsage, electricityUsage);
    }
}
