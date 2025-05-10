package layers;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import other.GameState;

import java.awt.*;
import java.awt.event.MouseEvent;

public class CityLayer implements Layer<TerrainType> {

    private final GameState gameState;

    private final int width;
    private final int height;
    protected TerrainType[][] buffer;


    /**
     * Generate map of terrain
     */
    public CityLayer(@NotNull GameState gameState) {
        this.gameState = gameState;

        this.width = gameState.getMapWidth();
        this.height = gameState.getMapWidth();
        buffer = new TerrainType[width][height];

        TopographyLayer topography = gameState.getTopographyMap();

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

        gameState.getTopographyMap().draw(g, xOffset, yOffset, width, height);

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
                g.fillRect(x * GameState.TILE_SIZE, y * GameState.TILE_SIZE, GameState.TILE_SIZE, GameState.TILE_SIZE);
            }
        }
    }

    @Override
    public boolean edit(@NotNull Rectangle rectangle, int button) {
        double price;
        TerrainType tile;

        switch (button){
            case MouseEvent.BUTTON1:
                // Convert selection to straight line
                if (rectangle.width >= rectangle.height) {
                    rectangle.height = 0;
                } else {
                    rectangle.width = 0;
                }

                price = GameState.DEFAULT_ROAD_PRICE;
                tile = TerrainType.ROAD;

                break;

            case MouseEvent.BUTTON3:
                price = GameState.DEFAULT_HOUSING_AREA_PRICE;
                tile = TerrainType.HOUSING_AREA;
                break;

            default:
                return false;
        }

        price *= count(rectangle, TerrainType.LAND);

        if (gameState.getMoney() < price) return false;

        replace(rectangle, TerrainType.LAND, tile);
        gameState.setMoney(gameState.getMoney() - price);
        return true;
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
