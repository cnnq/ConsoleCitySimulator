package layers;

import gui.CityTopBar;
import modes.EditMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import infrastructure.Infrastructure;
import other.Directions;
import other.Game;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.EnumSet;

public class CityLayer implements Layer<Infrastructure> {

    private final Game game;

    private final int width;
    private final int height;
    protected Infrastructure[][] buffer;


    /**
     * Generate map of terrain
     */
    public CityLayer(@NotNull Game game) {
        this.game = game;

        this.width = game.getMapWidth();
        this.height = game.getMapWidth();
        buffer = new Infrastructure[width][height];

        TopographyLayer topography = game.getTopographyMap();

        // Fill with water
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double h = topography.get(x, y);

                if (h <= topography.getWaterLevel()) {
                    set(x, y, Infrastructure.WATER);
                }
            }
        }
    }

    @Override
    public void draw(Graphics g, int xOffset, int yOffset, int width, int height) {

        game.getTopographyMap().draw(g, xOffset, yOffset, width, height);

        int minX = Math.max(0, -xOffset);
        int minY = Math.max(0, -yOffset);

        int maxX = Math.min(width, this.width - xOffset);
        int maxY = Math.min(height, this.height - yOffset);

        for (int x = minX; x < maxX; x++) {
            for (int y = minY; y < maxY; y++) {

                Infrastructure infrastructure = get(x + xOffset, y + yOffset);
                if (infrastructure == null) continue;

                // Avoid unnecessary checks
                EnumSet<Directions> neighbourData = EnumSet.noneOf(Directions.class);
                if (infrastructure.isNeighbourDependent()) neighbourData = getNeighbourData(x + xOffset, y + yOffset, infrastructure);

                infrastructure.draw(g, x, y, neighbourData);
            }
        }
    }

    @Override
    public boolean edit(@NotNull Rectangle rectangle, int button) {
        if(!(game.getGameWindow().getGameMode() instanceof EditMode editMode)) return false;
        if(!(editMode.getTopBar() instanceof CityTopBar topBar)) return false;

        Infrastructure infrastructure = topBar.getChoosenInfrastructure();

        switch (button) {
            case MouseEvent.BUTTON1:
                if (infrastructure.isNeighbourDependent()) {
                    // E.g. road or water pipes
                    // Convert selection to straight line
                    if (rectangle.width >= rectangle.height) {
                        rectangle.height = 0;
                    } else {
                        rectangle.width = 0;
                    }
                }
                break;

            case MouseEvent.BUTTON3:
                fill(rectangle, null);
                return true;

            default:
                return false;
        }

        double price = infrastructure.getBuildingCost() * count(rectangle, null);

        if (game.getMoney() < price) return false;

        replace(rectangle, null, infrastructure);
        game.setMoney(game.getMoney() - price);
        return true;
    }


    @Override
    public Infrastructure get(@Range(from = 0, to = Integer.MAX_VALUE) int x, @Range(from = 0, to = Integer.MAX_VALUE) int y) {
        return buffer[x][y];
    }

    @Override
    public void set(@Range(from = 0, to = Integer.MAX_VALUE) int x, @Range(from = 0, to = Integer.MAX_VALUE) int y, Infrastructure value) {
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
