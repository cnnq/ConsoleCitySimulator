package infrastructure;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import graphics.Sprite;

/**
 * Infrastructure managed by government
 */
public class ManagedInfrastructure extends Infrastructure {

    private final double maintenanceCost;

    /**
     * Create instance of government managed infrastructure
     * @param sprite
     * @param buildingCost cost of building
     * @param maintenanceCost cost of maintaining an infrastructure
     */
    @JsonCreator
    protected ManagedInfrastructure(@JsonProperty("sprite") @Nullable Sprite sprite,
                                    @JsonProperty("buildingCost") double buildingCost, @JsonProperty("maintenanceCost") double maintenanceCost) {

        super(sprite, buildingCost);

        if (maintenanceCost < 0) throw new IllegalArgumentException("maintenanceCost cannot be negative");

        this.maintenanceCost = maintenanceCost;
    }

    public double getMaintenanceCost() {
        return maintenanceCost;
    }
}
