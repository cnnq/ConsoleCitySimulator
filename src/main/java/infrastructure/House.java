package infrastructure;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;
import graphics.Sprite;

public class House extends UnmanagedBuilding {

    /**
     * Create instance of house
     * @param sprite
     * @param buildingCost cost of building
     * @param waterUsage usage of water, negative values mean production
     * @param electricityUsage usage of electricity, negative values mean production
     * @param capacity maximum number of inhabitants
     */
    @JsonCreator
    protected House(@JsonProperty("sprite") @Nullable Sprite sprite,
                    @JsonProperty("buildingCost") double buildingCost,
                    @JsonProperty("capacity") @Range(from = 0, to = Integer.MAX_VALUE) int capacity,
                    @JsonProperty("waterUsage") double waterUsage, @JsonProperty("electricityUsage") double electricityUsage) {

        super(sprite, buildingCost, capacity, waterUsage, electricityUsage);
    }
}
