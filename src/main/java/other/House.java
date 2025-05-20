package other;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.awt.*;

public class House extends Building {

    public static final House HOUSE_1 = new House(defaultSpritemap, 0, 3, false, Color.RED, DEFAULT_HOUSING_AREA_PRICE, 1, 1, 4);
    public static final House HOUSE_2 = new House(defaultSpritemap, 1, 3, false, Color.RED, DEFAULT_HOUSING_AREA_PRICE, 2, 2, 6);


    private int capacity;

    /**
     * Create instance of house
     * @param spritemap from which texture should be taken
     * @param spritemapX x tile coordinate in spritemap
     * @param spritemapY y tile coordinate in spritemap
     * @param neighbourDependent should look of a tile differ depending on neighbouring tiles
     * @param defaultColor color to display if spritemap is null, null if transparent
     * @param buildingCost cost of building
     * @param waterUsage usage of water, negative values mean production
     * @param electricityUsage usage of electricity, negative values mean production
     * @param capacity maximum number of inhabitants
     */
    protected House(@Nullable Image spritemap,
                     @Range(from = 0, to = Integer.MAX_VALUE) int spritemapX,
                     @Range(from = 0, to = Integer.MAX_VALUE) int spritemapY,
                     boolean neighbourDependent,
                     @Nullable Color defaultColor,
                     double buildingCost, double waterUsage, double electricityUsage,
                     @Range(from = 0, to = Integer.MAX_VALUE) int capacity) {

        super(spritemap, spritemapX, spritemapY, neighbourDependent, defaultColor, buildingCost, waterUsage, electricityUsage);

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
