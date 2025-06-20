package main;

import graphics.Spritemap;
import graphics.SpritemapNotLoadedException;
import gui.GameMode;
import gui.GameWindow;
import layers.*;
import org.jetbrains.annotations.NotNull;
import systems.EconomySystem;
import systems.FinancialSystem;
import systems.PopulationSystem;
import systems.UrbanizationSystem;

/**
 * Instance of currently loaded game
 */
public class Game implements Runnable {

    public static final int DEFAULT_FPS = 20;

    public static final int DEFAULT_MAP_SIZE = 200;
    public static final int DEFAULT_CELL_SIZE = 64;
    public static final double DEFAULT_WATER_LEVEL = 0.45;


    // Window
    private final GameWindow gameWindow;
    private Thread thread;
    private boolean running;

    private final int mapWidth;
    private final int mapHeight;

    // Layers
    private final TopographyLayer topographyMap;
    private final CityLayer cityMap;
    private final PipesLayer pipesMap;
    private final WiresLayer wiresMap;

    // Systems
    private final UrbanizationSystem urbanizationSystem;
    private final PopulationSystem populationSystem;
    private final EconomySystem economySystem;
    private final FinancialSystem financialSystem;


    public Game() {

        mapWidth = DEFAULT_MAP_SIZE;
        mapHeight = DEFAULT_MAP_SIZE;

        topographyMap = new TopographyLayer(this, 10, DEFAULT_CELL_SIZE, DEFAULT_WATER_LEVEL);
        cityMap = new CityLayer(this);
        pipesMap = new PipesLayer(this);
        wiresMap = new WiresLayer(this);

        urbanizationSystem = new UrbanizationSystem(this);
        populationSystem = new PopulationSystem(this);
        economySystem = new EconomySystem(this);
        financialSystem = new FinancialSystem(this);

        // Window
        gameWindow = new GameWindow(this);
        running = true;

        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        // Repaint frame every 1/FPS ms

        final int targetNanos = 1_000_000_000 / DEFAULT_FPS;

        long lastTime = System.nanoTime();

        // run
        while (running) {

            update(1f / DEFAULT_FPS);
            gameWindow.repaint();

            long currentTime = System.nanoTime();

            // wait
            try {
                long timeToWait = (targetNanos - currentTime + lastTime) / 1_000_000;
                if (timeToWait > 0) Thread.sleep(timeToWait);

            } catch (InterruptedException e) {
                running = false;
                break;
            }

            lastTime += targetNanos;
        }
    }

    /**
     * Update state of a game
     * @param deltaTime time in seconds
     */
    public void update(float deltaTime) {
        urbanizationSystem.update(deltaTime);
        populationSystem.update(deltaTime);
        economySystem.update(deltaTime);
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
    public PopulationSystem getPopulationSystem() {
        return populationSystem;
    }

    @NotNull
    public EconomySystem getEconomySystem() {
        return economySystem;
    }

    @NotNull
    public FinancialSystem getFinancialSystem() {
        return financialSystem;
    }

}
