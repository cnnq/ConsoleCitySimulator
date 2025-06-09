package infrastructure;

import org.jetbrains.annotations.Nullable;
import other.Directions;
import other.Sprite;

import java.awt.*;
import java.util.EnumSet;

public abstract class Infrastructure {

    private final Sprite sprite;
    private final Color defaultColor;
    private final double buildingCost;

    /**
     * Create instance of infrastructure
     * @param sprite
     * @param defaultColor color to display if sprite is null or null if you want transparent tile
     * @param buildingCost cost of building
     */
    protected Infrastructure(@Nullable Sprite sprite,
                             @Nullable Color defaultColor,
                             double buildingCost) {

        if (buildingCost < 0) throw new IllegalArgumentException("buildingCost cannot be negative");

        this.sprite = sprite;
        this.defaultColor = defaultColor;
        this.buildingCost = buildingCost;
    }

    /**
     * Draws current building
     * @param g
     * @param x displayed tile coordinate
     * @param y displayed tile coordinate
     * @param neighbourData null if {$code isNeighbourDependent() is false}
     */
    public void draw(Graphics g, int x, int y, EnumSet<Directions> neighbourData) {
        if (sprite != null) {
            sprite.draw(g, x, y, neighbourData);

        } else if (defaultColor != null) {
            int displayXPixel = x * Sprite.DEFAULT_SPRITE_SIZE;
            int displayYPixel = y * Sprite.DEFAULT_SPRITE_SIZE;

            g.setColor(defaultColor);
            g.fillRect(displayXPixel, displayYPixel, Sprite.DEFAULT_SPRITE_SIZE, Sprite.DEFAULT_SPRITE_SIZE);
        }
    }

    public double getBuildingCost() {
        return buildingCost;
    }

    public boolean isNeighbourDependent() {
        if (sprite == null) return false;
        return sprite.isNeighbourDependent();
    }
}
