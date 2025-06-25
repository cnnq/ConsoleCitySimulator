package layers;

import data.ConductionState;
import graphics.Sprite;
import infrastructure.InfrastructureManager;
import infrastructure.ManagedInfrastructure;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import main.Game;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Layer which store stores data about water pipes
 */
public class PipesLayer implements Layer<ConductionState> {

    private static final ManagedInfrastructure pipes = InfrastructureManager.INSTANCE.getInfrastructure("PIPES", ManagedInfrastructure.class);

    private final int width;
    private final int height;
    protected ConductionState[][] buffer;

    private Game game;


    public PipesLayer(@NotNull Game game) {
        this.game = game;

        this.width = game.getMapWidth();
        this.height = game.getMapHeight();
        buffer = new ConductionState[width][height];
    }


    @Override
    public void draw(Graphics g, int xOffset, int yOffset, int width, int height) {

        game.getCityMap().draw(g, xOffset, yOffset, width, height);

        // Clamp to not draw out of bounds
        int minX = Math.max(0, -xOffset);
        int minY = Math.max(0, -yOffset);

        int maxX = Math.min(width / Sprite.DEFAULT_SPRITE_SIZE + 1, this.width - xOffset);
        int maxY = Math.min(height / Sprite.DEFAULT_SPRITE_SIZE + 1, this.height - yOffset);

        for (int x = minX; x < maxX; x++) {
            for (int y = minY; y < maxY; y++) {

                int xOff = x + xOffset;
                int yOff = y + yOffset;

                ConductionState tile = get(xOff, yOff);

                var neighbourData = getNeighbourData(xOff, yOff, ConductionState.Empty);
                neighbourData.addAll(getNeighbourData(xOff, yOff, ConductionState.Filled));

                if (tile == ConductionState.Empty) {
                    pipes.draw(g, x, y, neighbourData);

                } else if (tile == ConductionState.Filled) {
                    g.setColor(Color.BLUE);
                    g.fillRect(x * Sprite.DEFAULT_SPRITE_SIZE, y * Sprite.DEFAULT_SPRITE_SIZE, Sprite.DEFAULT_SPRITE_SIZE, Sprite.DEFAULT_SPRITE_SIZE);
                    pipes.draw(g, x, y, neighbourData);
                }
            }
        }
    }

    @Override
    public boolean edit(@NotNull Rectangle rectangle, int button) {

        switch (button) {
            case MouseEvent.BUTTON1:
                // Convert selection to straight line
                if (rectangle.width >= rectangle.height) {
                    rectangle.height = 0;
                } else {
                    rectangle.width = 0;
                }

                double price = pipes.getBuildingCost() * (rectangle.width + 1) * (rectangle.height + 1);

                if (game.getFinancialSystem().getMoney() < price) return false;

                fill(rectangle, ConductionState.Empty);
                game.getFinancialSystem().spendMoney(price);
                return true;

            case MouseEvent.BUTTON3:
                fill(rectangle, null);
                return true;

            default:
                return false;
        }
    }


    @Override
    public ConductionState get(@Range(from = 0, to = Integer.MAX_VALUE) int x, @Range(from = 0, to = Integer.MAX_VALUE) int y) {
        return buffer[x][y];
    }

    @Override
    public void set(@Range(from = 0, to = Integer.MAX_VALUE) int x, @Range(from = 0, to = Integer.MAX_VALUE) int y, ConductionState value) {
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
