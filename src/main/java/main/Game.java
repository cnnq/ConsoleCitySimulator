package main;

import data.ElectricityStats;
import data.PopulationStats;
import data.WaterStats;
import gui.GameWindow;
import infrastructure.Building;
import infrastructure.House;
import infrastructure.Infrastructure;
import infrastructure.InfrastructureManager;
import layers.*;
import org.jetbrains.annotations.NotNull;
import systems.FinancialSystem;
import systems.UrbanizationSystem;

import java.util.Random;

/**
 * Instance of currently loaded game
 */
public class Game {

    public static final int DEFAULT_MAP_SIZE = 200;
    public static final int DEFAULT_CELL_SIZE = 64;
    public static final double DEFAULT_WATER_LEVEL = 0.45;

    //
    private static final double MIGRATION_FACTOR = 1;

    //
    private final GameWindow gameWindow;

    private final int mapWidth;
    private final int mapHeight;

    // Layers
    private final TopographyLayer topographyMap;
    private final CityLayer cityMap;
    private final PipesLayer pipesMap;
    private final WiresLayer wiresMap;

    // Systems
    private final UrbanizationSystem urbanizationSystem;
    private final FinancialSystem financialSystem;

    // Data
    private int population;

    private static final Random random = new Random();


    public Game(@NotNull GameWindow gameWindow) {
        this.gameWindow = gameWindow;

        mapWidth = DEFAULT_MAP_SIZE;
        mapHeight = DEFAULT_MAP_SIZE;

        topographyMap = new TopographyLayer(this, 10, DEFAULT_CELL_SIZE, DEFAULT_WATER_LEVEL);
        cityMap = new CityLayer(this);
        pipesMap = new PipesLayer(this);
        wiresMap = new WiresLayer(this);

        urbanizationSystem = new UrbanizationSystem(this);
        financialSystem = new FinancialSystem(this);

        InfrastructureManager.INSTANCE.getInfrastructure("ROAD");
    }

    /**
     * Update state of a game
     * @param deltaTime time in seconds
     */
    public void update(float deltaTime) {

        // Build / destroy houses
        urbanizationSystem.update(deltaTime);

        // Migration
        PopulationStats populationStats = getPopulationStats();

        int actualMigrationFactor = (int)MIGRATION_FACTOR + (random.nextDouble() < (MIGRATION_FACTOR - Math.floor(MIGRATION_FACTOR)) ? 1 : 0);

        if (populationStats.population() > populationStats.capacity()) population -= actualMigrationFactor;
        if (populationStats.population() < populationStats.capacity()) population += actualMigrationFactor;

        // Taxes and spending
        financialSystem.update(deltaTime);
    }

    public GameWindow getGameWindow() {
        return gameWindow;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    // Layers

    public TopographyLayer getTopographyMap() {
        return topographyMap;
    }

    public CityLayer getCityMap() {
        return cityMap;
    }

    public PipesLayer getPipesMap() {
        return pipesMap;
    }

    public WiresLayer getWiresMap() {
        return wiresMap;
    }

    // Systems

    public UrbanizationSystem getUrbanizationSystem() {
        return urbanizationSystem;
    }

    public FinancialSystem getFinancialSystem() {
        return financialSystem;
    }

    // Stats

    public WaterStats getWaterStats() {
        double usage = 0;
        double production = 0;

        for (int x = 0; x < cityMap.getWidth(); x++) {
            for (int y = 0; y < cityMap.getHeight(); y++) {
                Infrastructure infrastructure = cityMap.get(x, y);
                if(!(infrastructure instanceof Building building)) continue;

                double waterUsage = building.getWaterUsage();

                if (waterUsage > 0) usage += waterUsage;
                else production -= waterUsage;
            }
        }
        return new WaterStats(usage, production);
    }

    public ElectricityStats getElectricityStats() {
        double usage = 0;
        double production = 0;

        for (int x = 0; x < cityMap.getWidth(); x++) {
            for (int y = 0; y < cityMap.getHeight(); y++) {
                Infrastructure infrastructure = cityMap.get(x, y);
                if (!(infrastructure instanceof Building building)) continue;

                double electricityUsage = building.getElectricityUsage();

                if (electricityUsage > 0) usage += electricityUsage;
                else production -= electricityUsage;
            }
        }
        return new ElectricityStats(usage, production);
    }

    public PopulationStats getPopulationStats() {
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
