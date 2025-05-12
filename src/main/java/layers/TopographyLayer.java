package layers;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import other.GameState;
import other.PerlinNoise;

import java.awt.*;
import java.util.Random;

public class TopographyLayer implements Layer<Double> {

    private final int width;
    private final int height;
    private final double waterLevel;
    protected double[][] buffer;

    /**
     * Generate topography of terrain
     * @param gameState
     * @param seed seed to generate terrain from
     * @param cellSize determines how big islands of the same terrain should be
     */
    public TopographyLayer(@NotNull GameState gameState,
                           long seed,
                           @Range(from = 1, to = Integer.MAX_VALUE) int cellSize,
                           double waterLevel) {

        if (waterLevel < 0 || waterLevel > 1) throw new IllegalArgumentException("waterLevel must be between 0 and 1");
        this.waterLevel = waterLevel;

        this.width = gameState.getMapWidth();
        this.height = gameState.getMapHeight();
        buffer = new double[width][height];

        Random random = new Random(seed);

        // Generate terrain
        PerlinNoise noise1 = new PerlinNoise(random.nextInt());
        PerlinNoise noise2 = new PerlinNoise(random.nextInt());
        PerlinNoise noise3 = new PerlinNoise(random.nextInt());

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

                double h = 0;
                h += noise1.getNoiseAt((double)x / cellSize, (double)y / cellSize);
                h += noise2.getNoiseAt((double)x * 2 / cellSize, (double)y * 2 / cellSize);
                h += noise3.getNoiseAt((double)x * 4 / cellSize, (double)y * 4 / cellSize);
                h /= 3;

                set(x, y, h);
            }
        }
    }


    @Override
    public void draw(Graphics g, int xOffset, int yOffset, int width, int height) {

        // Clamp to not draw out of bounds
        int minX = Math.max(-1, -xOffset);
        int minY = Math.max(-1, -yOffset);

        int maxX = Math.min(width, this.width - xOffset - 1);
        int maxY = Math.min(height, this.height - yOffset - 1);

        for (int displayedTileX = minX; displayedTileX < maxX; displayedTileX++) {
            for (int displayedTileY = minY; displayedTileY < maxY; displayedTileY++) {

                int tileX = displayedTileX + xOffset;
                int tileY = displayedTileY + yOffset;

                // Interpolate color
                /*
                Color color00 = getColor(get(tileX, tileY));
                Color color01 = getColor(get(tileX, tileY + 1));
                Color color10 = getColor(get(tileX + 1, tileY));
                Color color11 = getColor(get(tileX + 1, tileY + 1));

                for (int x = 0; x < GameState.TILE_SIZE; x++) {
                    Color color0 = mix(color00, color10, (double)x / GameState.TILE_SIZE);
                    Color color1 = mix(color01, color11, (double)x / GameState.TILE_SIZE);

                    for (int y = 0; y < GameState.TILE_SIZE; y++) {
                        Color color = mix(color0, color1, (double)y / GameState.TILE_SIZE);

                        g.setColor(color);
                        g.fillRect(displayedTileX * GameState.TILE_SIZE + GameState.TILE_SIZE / 2 + x, displayedTileY * GameState.TILE_SIZE + GameState.TILE_SIZE / 2 + y, 1, 1);
                    }
                } */

                g.setColor(getColor(get(tileX, tileY)));
                g.fillRect(displayedTileX * GameState.TILE_SIZE, displayedTileY * GameState.TILE_SIZE, GameState.TILE_SIZE, GameState.TILE_SIZE);
            }
        }
    }

    @Override
    public boolean edit(@NotNull Rectangle rectangle, int button) {
        return false;
    }


    /**
     * @return value between 0 and 1
     */
    @Override
    public Double get(@Range(from = 0, to = Integer.MAX_VALUE) int x, @Range(from = 0, to = Integer.MAX_VALUE) int y) {
        return buffer[x][y];
    }

    @Override
    public void set(@Range(from = 0, to = Integer.MAX_VALUE) int x, @Range(from = 0, to = Integer.MAX_VALUE) int y, Double value) {
        if (value < 0 || value > 1) throw new IllegalArgumentException("value must be between 0 and 1");

        buffer[x][y] = value;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public double getWaterLevel() {
        return waterLevel;
    }


    private Color getColor(double h) {
        if (h <= waterLevel) {
            // land
            final Color deepWater = new Color(0, 23,61);
            final Color shallowWater = new Color(32, 131,240);

            return mix(deepWater, shallowWater, (h / waterLevel) * (h / waterLevel));

        } else {
            // water
            final Color lowLand = new Color(90, 220,22);
            final Color highLand = new Color(22, 47, 13);

            return mix(lowLand, highLand, (h - waterLevel) / (1 - waterLevel));
        }
    }

    private static Color mix(Color a, Color b, double t) {
        return new Color((int)(a.getRed() * (1.0 - t) + b.getRed() * t), (int)(a.getGreen() * (1.0 - t) + b.getGreen() * t), (int)(a.getBlue() * (1.0 - t) + b.getBlue() * t));
    }
}
