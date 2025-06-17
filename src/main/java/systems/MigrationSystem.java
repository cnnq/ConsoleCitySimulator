package systems;

import data.PopulationStats;
import infrastructure.House;
import infrastructure.Infrastructure;
import layers.CityLayer;
import main.Game;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.Random;

/**
 * System which manages population and migration
 */
public class MigrationSystem implements GameSystem {

    private static final double TOO_BIG_TAX_FACTOR = 0.6;

    private static final Random random = new Random();

    @Range(from = 0, to = Integer.MAX_VALUE)
    private int population;

    private final Game game;


    public MigrationSystem(@NotNull Game game) {
        this.game = game;
    }

    @Override
    public void update(float deltaTime) {
        double perCapitaTax = game.getFinancialSystem().getPerCapitaTax();
        PopulationStats populationStats = getPopulationStats();

        // Get data
        double profitability = perCapitaTax / TOO_BIG_TAX_FACTOR;
        double saturation = populationStats.capacity() != 0 ? (double)populationStats.population() / populationStats.capacity() : 1;

        // Calculate from per capita tax and housing market saturation
        double migrationFactor = 1 / (profitability * profitability + saturation * saturation + 1) - 0.5;
        // Or: 1 - profitability - saturation

        // Population cannot be negative
        if (population + migrationFactor >= 0) population += (int)migrationFactor;
    }

    public PopulationStats getPopulationStats() {
        CityLayer cityMap = game.getCityMap();

        int capacity = 0;

        for (int x = 0; x < cityMap.getWidth(); x++) {
            for (int y = 0; y < cityMap.getHeight(); y++) {
                Infrastructure infrastructure = cityMap.get(x, y);
                if (!(infrastructure instanceof House house)) continue;

                capacity += house.getCapacity();
            }
        }
        return new PopulationStats(population, capacity);
    }
}
