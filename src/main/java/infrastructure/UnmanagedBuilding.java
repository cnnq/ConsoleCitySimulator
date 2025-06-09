package infrastructure;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;
import other.Sprite;

import java.awt.*;

/**
 * Building managed by citizens or not managed at all
 */
public class UnmanagedBuilding extends UnmanagedInfrastructure implements Building {

    private final int capacity;
    private final double waterUsage;
    private final double electricityUsage;

    /**
     * Create instance of building
     * @param sprite
     * @param defaultColor color to display if sprite is null or null if you want transparent tile
     * @param buildingCost cost of building
     * @param waterUsage usage of water, negative values mean production
     * @param electricityUsage usage of electricity, negative values mean production
     */
    protected UnmanagedBuilding(@Nullable Sprite sprite,
                                @Nullable Color defaultColor,
                                double buildingCost,
                                @Range(from = 0, to = Integer.MAX_VALUE) int capacity,
                                double waterUsage, double electricityUsage) {

        super(sprite, defaultColor, buildingCost);

        if (capacity < 0) throw new IllegalArgumentException("capacity cannot be negative");

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
