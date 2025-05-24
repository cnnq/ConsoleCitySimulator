package infrastructure;

import org.jetbrains.annotations.Nullable;
import other.Sprite;

import java.awt.*;

public class Building extends Infrastructure {

    private static final double SOLAR_PANEL_ELECTRICITY_PRODUCTIOIN = 10;
    private static final double WATER_PUMP_WATER_PRODUCTION = 10;

    public static final Building SOLAR_PANELS = new Building(Sprite.SOLAR_PANELS, Color.CYAN, DEFAULT_HOUSING_AREA_PRICE, 0, -SOLAR_PANEL_ELECTRICITY_PRODUCTIOIN);
    public static final Building WATER_PUMP = new Building(Sprite.WATER_PUMP, Color.GRAY, DEFAULT_HOUSING_AREA_PRICE, -WATER_PUMP_WATER_PRODUCTION, 0);


    private double waterUsage;
    private double electricityUsage;

    /**
     * Create instance of building
     * @param sprite
     * @param defaultColor color to display if spritemap is null, null if transparent
     * @param buildingCost cost of building
     * @param waterUsage usage of water, negative values mean production
     * @param electricityUsage usage of electricity, negative values mean production
     */
    protected Building(@Nullable Sprite sprite,
                     @Nullable Color defaultColor,
                     double buildingCost, double waterUsage, double electricityUsage) {

        super(sprite, defaultColor, buildingCost);

        this.waterUsage = waterUsage;
        this.electricityUsage = electricityUsage;
    }

    /**
     * Returns water usage, negative value mean production
     */
    public double getWaterUsage() {
        return waterUsage;
    }

    /**
     * Returns electricity usage, negative value mean production
     */
    public double getElectricityUsage() {
        return electricityUsage;
    }
}
