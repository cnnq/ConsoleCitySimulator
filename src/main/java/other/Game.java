package other;

import gui.GameWindow;
import layers.*;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * Instance of currently loaded game
 */
public class Game {

    public static final int DEFAULT_MAP_SIZE = 200;
    public static final int DEFAULT_CELL_SIZE = 64;
    public static final double DEFAULT_WATER_LEVEL = 0.45;
    public static final int TILE_SIZE = 32;

    // Prices in thousands of dollars
    public static final double DEFAULT_MONEY = 1000;
    public static final double DEFAULT_RENT = 1;

    private final GameWindow gameWindow;

    private final int mapWidth;
    private final int mapHeight;

    private final TopographyLayer topographyMap;
    private final CityLayer cityMap;
    private final PipesLayer pipesMap;
    private final WiresLayer wiresMap;

    private double money;

    private static Random random = new Random();


    public Game(@NotNull GameWindow gameWindow) {
        this.gameWindow = gameWindow;

        mapWidth = DEFAULT_MAP_SIZE;
        mapHeight = DEFAULT_MAP_SIZE;

        topographyMap = new TopographyLayer(this, 10, DEFAULT_CELL_SIZE, DEFAULT_WATER_LEVEL);
        cityMap = new CityLayer(this);
        pipesMap = new PipesLayer(this);
        wiresMap = new WiresLayer(this);

        money = DEFAULT_MONEY;
    }

    /**
     * Update state of a game
     * @param deltaTime time in seconds
     */
    public void update(float deltaTime) {
        if (deltaTime < 0) throw new IllegalArgumentException("deltaTime cannot be negative");

        WaterStats waterStats = getWaterStats();
        ElectricityStats electricityStats = getElectricityStats();

        int houses = 0;

        for (int x = 0; x < cityMap.getWidth(); x++) {
            for (int y = 0; y < cityMap.getHeight(); y++) {

                // Build house if close to road and with access to water pipes
                if (cityMap.get(x, y) == Infrastructure.HOUSING_AREA &&
                    cityMap.neighbours(x, y, Infrastructure.ROAD) &&
                    pipesMap.neighbours(x, y, true) &&
                    wiresMap.neighbours(x, y, true)) {

                    // Choose random house
                    House house = switch (random.nextInt(2)) {
                        case 0 -> House.HOUSE_1;
                        default -> House.HOUSE_2;
                    };

                    // Build only if enough water and electricity
                    if (waterStats.usage() + house.getWaterUsage() <= waterStats.production() &&
                        electricityStats.usage() + house.getElectricityUsage() <= electricityStats.production()) {
                        cityMap.set(x, y, house);

                        // Update stats
                        waterStats = new WaterStats(waterStats.usage() + house.getWaterUsage(), waterStats.production());
                        electricityStats = new ElectricityStats(electricityStats.usage() + house.getElectricityUsage(), electricityStats.production());
                    }
                }

                // Count houses
                if (cityMap.get(x, y) instanceof House) houses++;
            }
        }

        money += houses * DEFAULT_RENT * deltaTime;
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
}
