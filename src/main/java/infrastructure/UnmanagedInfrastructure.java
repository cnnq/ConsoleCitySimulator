package infrastructure;

import org.jetbrains.annotations.Nullable;
import other.Sprite;

import java.awt.*;

/**
 * Infrastructure managed by citizens or not managed at all
 */
public class UnmanagedInfrastructure extends Infrastructure {

    // Prices in thousands of dollars
    protected static final double DEFAULT_HOUSING_AREA_PRICE = 10;
    protected static final double DEFAULT_COMMERCIAL_AREA_PRICE = 5;
    protected static final double DEFAULT_INDUSTRIAL_AREA_PRICE = 10;

    // ====
    public static final UnmanagedInfrastructure WATER = new UnmanagedInfrastructure(null, null, 0);
    public static final UnmanagedInfrastructure HOUSING_AREA = new UnmanagedInfrastructure(null, new Color(40, 240, 80), DEFAULT_HOUSING_AREA_PRICE);
    public static final UnmanagedInfrastructure COMMERCIAL_AREA = new UnmanagedInfrastructure(null, new Color(40, 200, 200), DEFAULT_COMMERCIAL_AREA_PRICE);
    public static final UnmanagedInfrastructure INDUSTRIAL_AREA = new UnmanagedInfrastructure(null, new Color(150, 180, 40), DEFAULT_INDUSTRIAL_AREA_PRICE);


    /**
     * Create instance of infrastructure
     * @param sprite
     * @param defaultColor color to display if sprite is null or null if you want transparent tile
     * @param buildingCost cost of building
     */
    protected UnmanagedInfrastructure(@Nullable Sprite sprite,
                             @Nullable Color defaultColor,
                             double buildingCost) {

        super(sprite, defaultColor, buildingCost);
    }
}
