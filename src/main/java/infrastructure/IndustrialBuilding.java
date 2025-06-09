package infrastructure;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;
import graphics.Sprite;

public class IndustrialBuilding extends UnmanagedBuilding {

    private final double income;
    private final double pollutionLevel;

    /**
     * Create instance of commercial building
     * @param sprite
     * @param buildingCost cost of building
     * @param waterUsage usage of water, negative values mean production
     * @param electricityUsage usage of electricity, negative values mean production
     * @param income money that building earn
     * @param pollutionLevel how much building pollute environment
     */
    @JsonCreator
    protected IndustrialBuilding(@JsonProperty("sprite") @Nullable Sprite sprite,
                                 @JsonProperty("buildingCost") double buildingCost,
                                 @JsonProperty("capacity") @Range(from = 0, to = Integer.MAX_VALUE) int capacity,
                                 @JsonProperty("waterUsage") double waterUsage, @JsonProperty("electricityUsage") double electricityUsage,
                                 @JsonProperty("income") double income,
                                 @JsonProperty("pollutionLevel") double pollutionLevel) {

        super(sprite, buildingCost, capacity, waterUsage, electricityUsage);

        if (income < 0) throw new IllegalArgumentException("income cannot be negative");

        this.income = income;
        this.pollutionLevel = pollutionLevel;
    }

    public double getIncome() {
        return income;
    }

    public double getPollutionLevel() {
        return pollutionLevel;
    }
}
