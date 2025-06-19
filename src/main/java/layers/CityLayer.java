package layers;

import graphics.Sprite;
import gui.CityTopBar;
import infrastructure.*;
import gui.EditMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;
import data.Directions;
import main.Game;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.EnumSet;

/**
 * Layer which store infrastructure data
 */
public class CityLayer implements Layer<Infrastructure> {

    private final Game game;

    private final int width;
    private final int height;
    protected Infrastructure[][] buffer;


    public CityLayer(@NotNull Game game) {
        this.game = game;

        this.width = game.getMapWidth();
        this.height = game.getMapWidth();
        buffer = new Infrastructure[width][height];

        var water = InfrastructureManager.INSTANCE.getInfrastructure("WATER", UnmanagedInfrastructure.class);

        var topography = game.getTopographyMap();

        // Fill with water
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double h = topography.get(x, y);

                if (h <= topography.getWaterLevel()) {
                    set(x, y, water);
                }
            }
        }
    }

    @Override
    public void draw(Graphics g, int xOffset, int yOffset, int width, int height) {

        game.getTopographyMap().draw(g, xOffset, yOffset, width, height);

        int minX = Math.max(0, -xOffset);
        int minY = Math.max(0, -yOffset);

        int maxX = Math.min(width / Sprite.DEFAULT_SPRITE_SIZE + 1, this.width - xOffset);
        int maxY = Math.min(height / Sprite.DEFAULT_SPRITE_SIZE + 1, this.height - yOffset);

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
        if(!(editMode.getTopBar().getCurrentTopBarInstance() instanceof CityTopBar topBar)) return false;

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

        if (game.getFinancialSystem().getMoney() < price) return false;

        replace(rectangle, null, infrastructure);
        game.getFinancialSystem().spendMoney(price);
        return true;
    }


    @Override
    public @Nullable Infrastructure get(@Range(from = 0, to = Integer.MAX_VALUE) int x, @Range(from = 0, to = Integer.MAX_VALUE) int y) {
        return buffer[x][y];
    }

    @Override
    public void set(@Range(from = 0, to = Integer.MAX_VALUE) int x, @Range(from = 0, to = Integer.MAX_VALUE) int y, @Nullable Infrastructure value) {
        buffer[x][y] = value;
    }

    /**
     * Counts all instances of given infrastructure type on the map
     * @param infrastructure infrastructure type to be counted
     */
    public int calculateNumberOf(@NotNull Infrastructure infrastructure) {
        int result = 0;

        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                if (infrastructure.equals(get(x, y))) result++;
            }
        }

        return result;
    }

    /**
     * Counts all instances of given infrastructure type on the map
     * @param type infrastructure type to be counted
     */
    public <T extends Infrastructure> int calculateNumberOf(@NotNull Class<T> type) {
        int result = 0;

        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                if (type.isInstance(get(x, y))) result++;
            }
        }

        return result;
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
