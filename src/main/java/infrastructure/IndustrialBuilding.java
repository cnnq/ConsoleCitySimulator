package infrastructure;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;
import other.Sprite;

import java.awt.*;

public class IndustrialBuilding extends CommercialBuilding {

    public static final IndustrialBuilding FACTORY_1 = new IndustrialBuilding(Sprite.FACTORY_1, Color.GRAY, DEFAULT_INDUSTRIAL_AREA_PRICE, 10, 5, 5, 8, 2);

    private final double pollutionLevel;

    /**
     * Create instance of commercial building
     * @param sprite
     * @param defaultColor color to display if spritemap is null, null if transparent
     * @param buildingCost cost of building
     * @param waterUsage usage of water, negative values mean production
     * @param electricityUsage usage of electricity, negative values mean production
     * @param income money that building earn
     * @param pollutionLevel how much building pollute environment
     */
    protected IndustrialBuilding(@Nullable Sprite sprite,
                                 @Nullable Color defaultColor,
                                 double buildingCost,
                                 @Range(from = 0, to = Integer.MAX_VALUE) int capacity,
                                 double waterUsage, double electricityUsage,
                                 double income,
                                 double pollutionLevel) {

        super(sprite, defaultColor, buildingCost, capacity, waterUsage, electricityUsage, income);

        this.pollutionLevel = pollutionLevel;
    }

    public double getPollutionLevel() {
        return pollutionLevel;
    }
}
