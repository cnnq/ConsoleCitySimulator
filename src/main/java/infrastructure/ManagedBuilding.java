package infrastructure;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;
import other.Sprite;

import java.awt.*;

/**
 * Building managed by government
 */
public class ManagedBuilding extends ManagedInfrastructure implements Building {

    private static final double SOLAR_PANEL_ELECTRICITY_PRODUCTIOIN = 10;
    private static final double WATER_PUMP_WATER_PRODUCTION = 10;

    // ====
    public static final ManagedBuilding SOLAR_PANELS = new ManagedBuilding(Sprite.SOLAR_PANELS, Color.CYAN, 10, 0.1, 0, 0, -SOLAR_PANEL_ELECTRICITY_PRODUCTIOIN);
    public static final ManagedBuilding WATER_PUMP = new ManagedBuilding(Sprite.WATER_PUMP, Color.GRAY, 10, 0.1, 0, -WATER_PUMP_WATER_PRODUCTION, 0);


    private final int capacity;
    private final double waterUsage;
    private final double electricityUsage;

    /**
     * Create instance of government managed building
     * @param sprite
     * @param defaultColor color to display if sprite is null or null if you want transparent tile
     * @param buildingCost cost of building
     * @param maintenanceCost cost of maintaining the building
     * @param waterUsage usage of water, negative values mean production
     * @param electricityUsage usage of electricity, negative values mean production
     */
    protected ManagedBuilding(@Nullable Sprite sprite,
                              @Nullable Color defaultColor,
                              double buildingCost, double maintenanceCost,
                              @Range(from = 0, to = Integer.MAX_VALUE) int capacity,
                              double waterUsage, double electricityUsage) {

        super(sprite, defaultColor, buildingCost, maintenanceCost);

        this.capacity = capacity;
        this.waterUsage = waterUsage;
        this.electricityUsage = electricityUsage;
    }

    @Override
    public @Range(from = 0, to = Integer.MAX_VALUE) int getCapacity() {
        return capacity;
    }

    @Override
    public double getWaterUsage() {
        return waterUsage;
    }

    @Override
    public double getElectricityUsage() {
        return electricityUsage;
    }
}
