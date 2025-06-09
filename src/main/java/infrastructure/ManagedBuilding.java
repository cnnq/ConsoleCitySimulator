package infrastructure;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;
import graphics.Sprite;

import java.awt.*;

/**
 * Building managed by government
 */
public class ManagedBuilding extends ManagedInfrastructure implements Building {

    private final int capacity;
    private final double waterUsage;
    private final double electricityUsage;

    /**
     * Create instance of government managed building
     * @param sprite
     * @param buildingCost cost of building
     * @param maintenanceCost cost of maintaining the building
     * @param waterUsage usage of water, negative values mean production
     * @param electricityUsage usage of electricity, negative values mean production
     */
    @JsonCreator
    protected ManagedBuilding(@JsonProperty("sprite") @Nullable Sprite sprite,
                              @JsonProperty("buildingCost") double buildingCost, @JsonProperty("maintenanceCost") double maintenanceCost,
                              @JsonProperty("capacity") @Range(from = 0, to = Integer.MAX_VALUE) int capacity,
                              @JsonProperty("waterUsage") double waterUsage, @JsonProperty("electricityUsage") double electricityUsage) {

        super(sprite, buildingCost, maintenanceCost);

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
