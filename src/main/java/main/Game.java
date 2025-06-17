package main;

import gui.GameWindow;
import layers.*;
import org.jetbrains.annotations.NotNull;
import systems.FinancialSystem;
import systems.MigrationSystem;
import systems.UrbanizationSystem;

/**
 * Instance of currently loaded game
 */
public class Game {

    public static final int DEFAULT_MAP_SIZE = 200;
    public static final int DEFAULT_CELL_SIZE = 64;
    public static final double DEFAULT_WATER_LEVEL = 0.45;

    // Window
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
    private final MigrationSystem migrationSystem;
    private final FinancialSystem financialSystem;


    public Game(@NotNull GameWindow gameWindow) {
        this.gameWindow = gameWindow;

        mapWidth = DEFAULT_MAP_SIZE;
        mapHeight = DEFAULT_MAP_SIZE;

        topographyMap = new TopographyLayer(this, 10, DEFAULT_CELL_SIZE, DEFAULT_WATER_LEVEL);
        cityMap = new CityLayer(this);
        pipesMap = new PipesLayer(this);
        wiresMap = new WiresLayer(this);

        urbanizationSystem = new UrbanizationSystem(this);
        migrationSystem = new MigrationSystem(this);
        financialSystem = new FinancialSystem(this);
    }

    /**
     * Update state of a game
     * @param deltaTime time in seconds
     */
    public void update(float deltaTime) {
        urbanizationSystem.update(deltaTime);
        migrationSystem.update(deltaTime);
        financialSystem.update(deltaTime);
    }

    @NotNull
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

    @NotNull
    public TopographyLayer getTopographyMap() {
        return topographyMap;
    }

    @NotNull
    public CityLayer getCityMap() {
        return cityMap;
    }

    @NotNull
    public PipesLayer getPipesMap() {
        return pipesMap;
    }

    @NotNull
    public WiresLayer getWiresMap() {
        return wiresMap;
    }

    // Systems

    @NotNull
    public UrbanizationSystem getUrbanizationSystem() {
        return urbanizationSystem;
    }

    @NotNull
    public MigrationSystem getMigrationSystem() {
        return migrationSystem;
    }

    @NotNull
    public FinancialSystem getFinancialSystem() {
        return financialSystem;
    }

}
