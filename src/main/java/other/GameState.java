package other;

import layers.CityLayer;
import layers.PipesLayer;
import layers.TerrainType;
import layers.TopographyLayer;

/**
 * Manages state of currently loaded game
 */
public class GameState {

    public static final int DEFAULT_MAP_SIZE = 200;
    public static final int TILE_SIZE = 8;

    // Prices in thousands of dollars
    public static final double DEFAULT_MONEY = 1000;
    public static final double DEFAULT_RENT = 1;
    public static final double DEFAULT_ROAD_PRICE = 10;
    public static final double DEFAULT_HOUSING_AREA_PRICE = 10;
    public static final double DEFAULT_PIPE_PRICE = 1;

    private static TopographyLayer topography;
    private static CityLayer city;
    private static PipesLayer pipes;
    private static double money = DEFAULT_MONEY;


    static {
        topography = new TopographyLayer(DEFAULT_MAP_SIZE, DEFAULT_MAP_SIZE, 10, 64, 0.45);
        city = new CityLayer(topography);
        pipes = new PipesLayer(city);
    }


    /**
     * Update state of a game
     * @param deltaTime time in seconds
     */
    public static void update(float deltaTime) {
        if (deltaTime < 0) throw new IllegalArgumentException("deltaTime cannot be negative");

        int houses = 0;
        for (int x = 0; x < city.getWidth(); x++) {
            for (int y = 0; y < city.getHeight(); y++) {

                // Build house if close to road
                if (city.get(x, y) == TerrainType.HOUSING_AREA) {
                    if (x > 0 && city.get(x - 1, y) == TerrainType.ROAD ||
                        x + 1 < city.getWidth() && city.get(x + 1, y) == TerrainType.ROAD ||
                        y > 0 && city.get(x, y - 1) == TerrainType.ROAD ||
                        y + 1 < city.getHeight() && city.get(x, y + 1) == TerrainType.ROAD) {

                        city.set(x, y, TerrainType.HOUSE);
                    }
                }

                if (city.get(x, y) == TerrainType.HOUSE) houses++;
            }
        }

        money += houses * DEFAULT_RENT * deltaTime;
    }

    public static TopographyLayer getTopography() {
        return topography;
    }

    public static CityLayer getCity() {
        return city;
    }

    public static PipesLayer getPipes() {
        return pipes;
    }

    public static double getMoney() {
        return money;
    }

    public static void setMoney(double money) {
        GameState.money = money;
    }
}
