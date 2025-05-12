package layers;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import other.Building;
import other.Directions;
import other.GameState;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.EnumSet;

public class CityLayer implements Layer<Building> {

    private final GameState gameState;

    private final int width;
    private final int height;
    protected Building[][] buffer;


    /**
     * Generate map of terrain
     */
    public CityLayer(@NotNull GameState gameState) {
        this.gameState = gameState;

        this.width = gameState.getMapWidth();
        this.height = gameState.getMapWidth();
        buffer = new Building[width][height];

        TopographyLayer topography = gameState.getTopographyMap();

        // Fill with water
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double h = topography.get(x, y);

                if (h <= topography.getWaterLevel()) {
                    set(x, y, Building.WATER);
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

                Building building = get(x + xOffset, y + yOffset);
                if (building == null) continue;

                EnumSet<Directions> neighbourData = null;
                if (building.isNeighbourDependent()) neighbourData = getNeighbourData(x + xOffset, y + yOffset, building);

                building.draw(g, x, y, neighbourData);
            }
        }
    }

    @Override
    public boolean edit(@NotNull Rectangle rectangle, int button) {
        Building building;

        switch (button){
            case MouseEvent.BUTTON1:
                // Convert selection to straight line
                if (rectangle.width >= rectangle.height) {
                    rectangle.height = 0;
                } else {
                    rectangle.width = 0;
                }

                building = Building.ROAD;
                break;

            case MouseEvent.BUTTON3:
                building = Building.HOUSING_AREA;
                break;

            default:
                return false;
        }

        double price = building.getBuildingCost() * count(rectangle, building);

        if (gameState.getMoney() < price) return false;

        replace(rectangle, null, building);
        gameState.setMoney(gameState.getMoney() - price);
        return true;
    }


    @Override
    public Building get(@Range(from = 0, to = Integer.MAX_VALUE) int x, @Range(from = 0, to = Integer.MAX_VALUE) int y) {
        return buffer[x][y];
    }

    @Override
    public void set(@Range(from = 0, to = Integer.MAX_VALUE) int x, @Range(from = 0, to = Integer.MAX_VALUE) int y, Building value) {
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
