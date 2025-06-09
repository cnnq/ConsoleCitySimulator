package layers;

import infrastructure.ManagedInfrastructure;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import other.Game;

import java.awt.*;
import java.awt.event.MouseEvent;

public class WiresLayer implements Layer<Boolean> {

    private final int width;
    private final int height;
    protected boolean[][] buffer;

    private final Game game;


    public WiresLayer(@NotNull Game game) {
        this.game = game;

        this.width = game.getMapWidth();
        this.height = game.getMapHeight();
        buffer = new boolean[width][height];
    }


    @Override
    public void draw(Graphics g, int xOffset, int yOffset, int width, int height) {

        game.getCityMap().draw(g, xOffset, yOffset, width, height);

        // Clamp to not draw out of bounds
        int minX = Math.max(0, -xOffset);
        int minY = Math.max(0, -yOffset);

        int maxX = Math.min(width, this.width - xOffset);
        int maxY = Math.min(height, this.height - yOffset);

        for (int x = minX; x < maxX; x++) {
            for (int y = minY; y < maxY; y++) {
                if (get(x + xOffset, y + yOffset)) {
                    ManagedInfrastructure.WIRES.draw(g, x, y, getNeighbourData(x + xOffset, y + yOffset, true));
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

                double price = ManagedInfrastructure.WIRES.getBuildingCost() * (rectangle.width + 1) * (rectangle.height + 1);

                if (game.getMoney() < price) return false;

                fill(rectangle, true);
                game.setMoney(game.getMoney() - price);
                return true;

            case MouseEvent.BUTTON3:
                fill(rectangle, false);
                return true;

            default:
                return false;
        }
    }


    @Override
    public Boolean get(@Range(from = 0, to = Integer.MAX_VALUE) int x, @Range(from = 0, to = Integer.MAX_VALUE) int y) {
        return buffer[x][y];
    }

    @Override
    public void set(@Range(from = 0, to = Integer.MAX_VALUE) int x, @Range(from = 0, to = Integer.MAX_VALUE) int y, Boolean value) {
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
