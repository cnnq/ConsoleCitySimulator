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

        int houses = 0;
        for (int x = 0; x < cityMap.getWidth(); x++) {
            for (int y = 0; y < cityMap.getHeight(); y++) {

                // Build house if close to road and with access to water pipes
                if (cityMap.get(x, y) == Building.HOUSING_AREA &&
                    cityMap.neighbours(x, y, Building.ROAD) &&
                    pipesMap.neighbours(x, y, true) &&
                    wiresMap.neighbours(x, y, true)) {

                    switch (random.nextInt(2)){
                        case 0:
                            cityMap.set(x, y, Building.HOUSE_1);
                            break;
                        case 1:
                            cityMap.set(x, y, Building.HOUSE_2);
                            break;
                    }
                }

                if (cityMap.get(x, y) == Building.HOUSE_1 || cityMap.get(x, y) == Building.HOUSE_2) houses++;
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
}
