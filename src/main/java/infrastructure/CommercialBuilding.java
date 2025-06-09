package infrastructure;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;
import graphics.Sprite;

public class CommercialBuilding extends UnmanagedBuilding {

    private final double income;

    /**
     * Create instance of commercial building
     * @param sprite
     * @param buildingCost cost of building
     * @param waterUsage usage of water, negative values mean production
     * @param electricityUsage usage of electricity, negative values mean production
     * @param income money that building earn
     */
    @JsonCreator
    protected CommercialBuilding(@JsonProperty("sprite") @Nullable Sprite sprite,
                                 @JsonProperty("buildingCost") double buildingCost,
                                 @JsonProperty("capacity") @Range(from = 0, to = Integer.MAX_VALUE) int capacity,
                                 @JsonProperty("waterUsage") double waterUsage, @JsonProperty("electricityUsage") double electricityUsage,
                                 @JsonProperty("income") double income) {

        super(sprite, buildingCost, capacity, waterUsage, electricityUsage);

        if (income < 0) throw new IllegalArgumentException("income cannot be negative");

        this.income = income;
    }

    public double getIncome() {
        return income;
    }
}
