package layers;

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
     * @param width width of the map
     * @param height height of the map
     * @param seed seed to generate terrain from
     * @param cellSize determines how big islands of the same terrain should be
     */
    public TopographyLayer(@Range(from = 0, to = Integer.MAX_VALUE) int width,
                           @Range(from = 0, to = Integer.MAX_VALUE) int height,
                           long seed,
                           @Range(from = 1, to = Integer.MAX_VALUE) int cellSize,
                           double waterLevel) {

        if (waterLevel < 0 || waterLevel > 1) throw new IllegalArgumentException("waterLevel must be between 0 and 1");

        this.width = width;
        this.height = height;
        this.waterLevel = waterLevel;
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
    public void draw(Graphics g, int xOffset, int yOffset, int width, int height) {
        int minX = Math.max(0, -xOffset);
        int minY = Math.max(0, -yOffset);

        int maxX = Math.min(width, this.width - xOffset);
        int maxY = Math.min(height, this.height - yOffset);

        for (int x = minX; x < maxX; x++) {
            for (int y = minY; y < maxY; y++) {
                double h = get(x + xOffset, y + yOffset);

                Color color;

                if (h <= waterLevel) {
                    // land
                    final Color deepWater = new Color(0, 23,61);
                    final Color shallowWater = new Color(32, 131,240);

                    color = mix(deepWater, shallowWater, (h / waterLevel) * (h / waterLevel));
                } else {
                    // water
                    final Color lowLand = new Color(90, 220,22);
                    final Color highLand = new Color(22, 47, 13);

                    color = mix(lowLand, highLand, (h - waterLevel) / (1 - waterLevel));
                }

                g.setColor(color);
                g.fillRect(x * GameState.TILE_SIZE, y * GameState.TILE_SIZE, GameState.TILE_SIZE, GameState.TILE_SIZE);
            }
        }
    }

    private static Color mix(Color a, Color b, double t) {
        return new Color((int)(a.getRed() * (1 - t) + b.getRed() * t), (int)(a.getGreen() * (1 - t) + b.getGreen() * t), (int)(a.getBlue() * (1 - t) + b.getBlue() * t));
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
}
