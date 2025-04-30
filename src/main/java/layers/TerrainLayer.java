package layers;

import org.jetbrains.annotations.Range;
import other.PerlinNoise;

import java.awt.*;

public class TerrainLayer implements Layer<TerrainType> {

    public static final int TILE_SIZE = 8;

    private final int width;
    private final int height;
    protected TerrainType[][] buffer;

    /**
     * Generate map of terrain
     * @param width number of displayed characters horizontally
     * @param height number of displayed characters vertically
     * @param seed seed to generate terrain from
     * @param cellSize determines how big islands of the same terrain should be
     */
    public TerrainLayer(@Range(from = 0, to = Integer.MAX_VALUE) int width,
                        @Range(from = 0, to = Integer.MAX_VALUE) int height,
                        int seed,
                        @Range(from = 1, to = Integer.MAX_VALUE) int cellSize) {

        this.width = width;
        this.height = height;
        buffer = new TerrainType[width][height];

        // Generate terrain
        PerlinNoise noise1 = new PerlinNoise(seed);
        PerlinNoise noise2 = new PerlinNoise(seed + 3);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                double h = 0;
                h += noise1.getNoiseAt((double)x / cellSize, (double)y / cellSize);
                h += noise2.getNoiseAt((double)x * 2 / cellSize, (double)y * 2 / cellSize);
                h /= 2;

                if (h < 0.27) {
                    set(x, y, TerrainType.DEEP_WATER);
                } else if (h < 0.5) {
                    set(x, y, TerrainType.WATER);
                } else if (h < 0.7) {
                    set(x, y, TerrainType.GRASS);
                } else {
                    set(x, y, TerrainType.STONE);
                }
            }
        }
    }

    public void draw(Graphics g) {

        for (int y = 0; y < width; y++) {
            for (int x = 0; x < height; x++) {

                Color color = switch (get(x, y)) {
                    case DEEP_WATER -> Color.BLUE;
                    case WATER -> Color.CYAN;
                    case GRASS, BUILDING -> Color.GREEN;
                    case STONE -> Color.GRAY;
                };

                g.setColor(color);
                g.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
    }

    @Override
    public TerrainType get(@Range(from = 0, to = Integer.MAX_VALUE) int x, @Range(from = 0, to = Integer.MAX_VALUE) int y) {
        return buffer[y][x];
    }

    @Override
    public void set(@Range(from = 0, to = Integer.MAX_VALUE) int x, @Range(from = 0, to = Integer.MAX_VALUE) int y, TerrainType value) {
        buffer[y][x] = value;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
