package infrastructure;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import graphics.Sprite;

import java.awt.*;

/**
 * Infrastructure managed by citizens or not managed at all
 */
public class UnmanagedInfrastructure extends Infrastructure {

    /**
     * Create instance of infrastructure
     * @param sprite
     * @param buildingCost cost of building
     */
    @JsonCreator
    protected UnmanagedInfrastructure(@JsonProperty("sprite") @Nullable Sprite sprite,
                                      @JsonProperty("buildingCost") double buildingCost) {

        super(sprite, buildingCost);
    }
}
