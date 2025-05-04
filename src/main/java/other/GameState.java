package other;

import layers.TerrainLayer;
import layers.TerrainType;

/**
 * Manages state of currently loaded game
 */
public class GameState {

    public static final int DEFAULT_MAP_SIZE = 200;
    public static final double DEFAULT_MONEY = 100;
    public static final double DEFAULT_RENT = 1;
    public static final double DEFAULT_BUILDING_PRICE = 10;

    private static TerrainLayer terrain;
    private static double money = DEFAULT_MONEY;


    static {
        terrain = new TerrainLayer(DEFAULT_MAP_SIZE, DEFAULT_MAP_SIZE, 10, 64);
    }


    /**
     * Update state of a game
     * @param deltaTime time in seconds
     */
    public static void update(float deltaTime) {
        if (deltaTime < 0) throw new IllegalArgumentException("deltaTime cannot be negative");

        int buildings = 0;
        for (int y = 0; y < terrain.getHeight(); y++) {
            for (int x = 0; x < terrain.getWidth(); x++) {
                if (terrain.get(x, y) == TerrainType.BUILDING) buildings++;
            }
        }

        money += buildings * DEFAULT_RENT * deltaTime;
    }


    public static TerrainLayer getTerrain() {
        return terrain;
    }

    public static double getMoney() {
        return money;
    }

    public static void setMoney(double money) {
        GameState.money = money;
    }
}
