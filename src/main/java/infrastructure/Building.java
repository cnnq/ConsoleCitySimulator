package infrastructure;

import org.jetbrains.annotations.Range;

public interface Building {

    /**
     * Maximum number of people in building
     */
    @Range(from = 0, to = Integer.MAX_VALUE)
    int getCapacity();

    /**
     * Usage of water, negative values mean production
     */
    double getWaterUsage();

    /**
     * Usage of electricity, negative values mean production
     */
    double getElectricityUsage();
}
