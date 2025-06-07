package infrastructure;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;
import other.Sprite;

import java.awt.*;

public class IndustrialBuilding extends Building {

    public static final IndustrialBuilding FACTORY_1 = new IndustrialBuilding(Sprite.FACTORY_1, Color.GRAY, DEFAULT_INDUSTRIAL_AREA_PRICE, 5, 5, 8);

    public double income;

    /**
     * Create instance of commercial building
     * @param sprite
     * @param defaultColor color to display if spritemap is null, null if transparent
     * @param buildingCost cost of building
     * @param waterUsage usage of water, negative values mean production
     * @param electricityUsage usage of electricity, negative values mean production
     * @param income money that building earn
     */
    protected IndustrialBuilding(@Nullable Sprite sprite,
                                 @Nullable Color defaultColor,
                                 double buildingCost, double waterUsage, double electricityUsage,
                                 @Range(from = 0, to = Integer.MAX_VALUE) int income) {

        super(sprite, defaultColor, buildingCost, waterUsage, electricityUsage);

        this.income = income;
    }
}
