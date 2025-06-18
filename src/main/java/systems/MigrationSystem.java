package systems;

import data.PopulationStats;
import infrastructure.House;
import infrastructure.Infrastructure;
import layers.CityLayer;
import main.Game;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * System which manages population and migration
 */
public class MigrationSystem implements GameSystem {

    private static final double TOO_BIG_TAX_FACTOR = 0.75;

    private static final Random random = new Random();

    private int population;

    private final Game game;


    public MigrationSystem(@NotNull Game game) {
        this.game = game;
    }

    @Override
    public void update(float deltaTime) {
        double migrationFactor = calculateMigrationFactor();

        // Reduce flickering
        if (Math.abs(migrationFactor) < 0.01) return;

        // Round by probability
        if (random.nextDouble() > Math.abs(migrationFactor - Math.floor(migrationFactor))) {
            migrationFactor++;
        }

        population += (int)Math.floor(migrationFactor);

        if(population < 0) population = 0;
    }

    private double calculateMigrationFactor() {
        double perCapitaTax = game.getFinancialSystem().getPerCapitaTax();
        PopulationStats populationStats = getPopulationStats();

        // Get data
        double profitability = perCapitaTax / TOO_BIG_TAX_FACTOR;
        double saturation = populationStats.capacity() != 0 ? (double)populationStats.population() / populationStats.capacity() : 1;

        // Calculate from per capita tax and housing market saturation
        return 1 / (profitability * profitability + saturation * saturation + 1) - 0.5;
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
