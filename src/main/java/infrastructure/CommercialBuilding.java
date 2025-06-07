package infrastructure;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;
import other.Sprite;

import java.awt.*;

public class CommercialBuilding extends Building {

    public static final CommercialBuilding SHOP_1 = new CommercialBuilding(Sprite.SHOP_1, Color.CYAN, DEFAULT_COMMERCIAL_AREA_PRICE, 1, 4, 3);
    public static final CommercialBuilding SHOP_2 = new CommercialBuilding(Sprite.SHOP_2, Color.CYAN, DEFAULT_COMMERCIAL_AREA_PRICE, 2, 2, 2);

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
    protected CommercialBuilding(@Nullable Sprite sprite,
                    @Nullable Color defaultColor,
                    double buildingCost, double waterUsage, double electricityUsage,
                    @Range(from = 0, to = Integer.MAX_VALUE) int income) {

        super(sprite, defaultColor, buildingCost, waterUsage, electricityUsage);

        this.income = income;
    }
}
