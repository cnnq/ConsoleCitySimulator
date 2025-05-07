package layers;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import other.GameState;

import java.awt.*;

public class CityLayer implements Layer<TerrainType> {

    private final int width;
    private final int height;
    protected TerrainType[][] buffer;

    TopographyLayer topography;

    /**
     * Generate map of terrain
     */
    public CityLayer(@NotNull TopographyLayer topography) {

        this.width = topography.getWidth();
        this.height = topography.getHeight();
        buffer = new TerrainType[width][height];

        this.topography = topography;

        // Generate terrain
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double h = topography.get(x, y);

                if (h <= topography.getWaterLevel()) {
                    set(x, y, TerrainType.WATER);
                } else {
                    set(x, y, TerrainType.LAND);
                }
            }
        }
    }

    @Override
    public void draw(Graphics g, int xOffset, int yOffset, int width, int height) {

        topography.draw(g, xOffset, yOffset, width, height);

        int minX = Math.max(0, -xOffset);
        int minY = Math.max(0, -yOffset);

        int maxX = Math.min(width, this.width - xOffset);
        int maxY = Math.min(height, this.height - yOffset);

        for (int x = minX; x < maxX; x++) {
            for (int y = minY; y < maxY; y++) {

                final Color roadColor = new Color(5, 4, 1);
                final Color housingAreaColor = new Color(44, 230, 83);
                final Color houseColor = new Color(181, 56, 25);
                final Color defaultColor = new Color(255, 0, 255);

                Color color;

                switch (get(x + xOffset, y + yOffset)) {
                    case WATER, LAND -> {
                        continue;
                    }
                    case ROAD -> color = roadColor;
                    case HOUSING_AREA -> color = housingAreaColor;
                    case HOUSE -> color = houseColor;
                    default -> color = defaultColor;
                }

                g.setColor(color);
                g.fillRect(x * GameState.TILE_SIZE, y * GameState.TILE_SIZE, GameState.TILE_SIZE, GameState.TILE_SIZE);}
        }
    }

    @Override
    public TerrainType get(@Range(from = 0, to = Integer.MAX_VALUE) int x, @Range(from = 0, to = Integer.MAX_VALUE) int y) {
        return buffer[x][y];
    }

    @Override
    public void set(@Range(from = 0, to = Integer.MAX_VALUE) int x, @Range(from = 0, to = Integer.MAX_VALUE) int y, TerrainType value) {
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
}
