package other;

import data.ElectricityStats;
import data.PopulationStats;
import data.WaterStats;
import gui.GameWindow;
import infrastructure.Building;
import infrastructure.House;
import infrastructure.Infrastructure;
import layers.*;
import org.jetbrains.annotations.NotNull;
import systems.UrbanizationSystem;

import java.util.Random;

/**
 * Instance of currently loaded game
 */
public class Game {

    public static final int DEFAULT_MAP_SIZE = 200;
    public static final int DEFAULT_CELL_SIZE = 64;
    public static final double DEFAULT_WATER_LEVEL = 0.45;

    // Prices in thousands of dollars
    public static final double DEFAULT_MONEY = 1000;
    public static final double DEFAULT_TAX = 0.1;
    private static final double MINIMAL_WAGE = 1;

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
    UrbanizationSystem urbanizationSystem;

    // Data
    private double money;
    private int population;

    private double tax;

    private static Random random = new Random();


    public Game(@NotNull GameWindow gameWindow) {
        this.gameWindow = gameWindow;

        mapWidth = DEFAULT_MAP_SIZE;
        mapHeight = DEFAULT_MAP_SIZE;

        topographyMap = new TopographyLayer(this, 10, DEFAULT_CELL_SIZE, DEFAULT_WATER_LEVEL);
        cityMap = new CityLayer(this);
        pipesMap = new PipesLayer(this);
        wiresMap = new WiresLayer(this);

        urbanizationSystem = new UrbanizationSystem(this);

        money = DEFAULT_MONEY;
        tax = DEFAULT_TAX;
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

        // Gather taxes
        money += deltaTime * population * MINIMAL_WAGE / 30.5 * tax;
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


    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }


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
