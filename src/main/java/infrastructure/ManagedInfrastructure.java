package infrastructure;

import org.jetbrains.annotations.Nullable;
import other.Sprite;

import java.awt.*;

/**
 * Infrastructure managed by government
 */
public class ManagedInfrastructure extends Infrastructure {

    private static final double DEFAULT_ROAD_PRICE = 10;
    private static final double DEFAULT_PIPE_PRICE = 1;
    private static final double DEFAULT_WIRE_PRICE = 1;

    // ====
    public static final ManagedInfrastructure ROAD = new ManagedInfrastructure(Sprite.ROAD, Color.BLACK, DEFAULT_ROAD_PRICE, 0.1);
    public static final ManagedInfrastructure PIPES = new ManagedInfrastructure(Sprite.PIPES, Color.GRAY, DEFAULT_PIPE_PRICE, 0);
    public static final ManagedInfrastructure WIRES = new ManagedInfrastructure(null, Color.YELLOW, DEFAULT_WIRE_PRICE, 0);


    private final double maintenanceCost;

    /**
     * Create instance of government managed infrastructure
     * @param sprite
     * @param defaultColor color to display if sprite is null or null if you want transparent tile
     * @param buildingCost cost of building
     * @param maintenanceCost cost of maintaining an infrastructure
     */
    protected ManagedInfrastructure(@Nullable Sprite sprite,
                                    @Nullable Color defaultColor,
                                    double buildingCost, double maintenanceCost) {

        super(sprite, defaultColor, buildingCost);

        if (maintenanceCost < 0) throw new IllegalArgumentException("maintenanceCost cannot be negative");

        this.maintenanceCost = maintenanceCost;
    }

    public double getMaintenanceCost() {
        return maintenanceCost;
    }
}
