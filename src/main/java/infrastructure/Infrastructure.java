package infrastructure;

import org.jetbrains.annotations.Nullable;
import other.Directions;
import other.Sprite;

import java.awt.*;
import java.util.EnumSet;

public class Infrastructure {

    // Prices in thousands of dollars
    private static final double DEFAULT_ROAD_PRICE = 10;

    protected static final double DEFAULT_HOUSING_AREA_PRICE = 10;
    protected static final double DEFAULT_COMMERCIAL_AREA_PRICE = 5;
    protected static final double DEFAULT_INDUSTRIAL_AREA_PRICE = 10;

    private static final double DEFAULT_PIPE_PRICE = 1;
    private static final double DEFAULT_WIRE_PRICE = 1;

    public static final Infrastructure WATER = new Infrastructure(null, 0);
    public static final Infrastructure HOUSING_AREA = new Infrastructure(new Color(40, 240, 80), DEFAULT_HOUSING_AREA_PRICE);
    public static final Infrastructure COMMERCIAL_AREA = new Infrastructure(new Color(40, 200, 200), DEFAULT_COMMERCIAL_AREA_PRICE);
    public static final Infrastructure INDUSTRIAL_AREA = new Infrastructure(new Color(150, 180, 40), DEFAULT_INDUSTRIAL_AREA_PRICE);

    public static final Infrastructure ROAD = new Infrastructure(Sprite.ROAD, Color.BLACK, DEFAULT_ROAD_PRICE);
    public static final Infrastructure PIPES = new Infrastructure(Sprite.PIPES, Color.GRAY, DEFAULT_PIPE_PRICE);
    public static final Infrastructure WIRES = new Infrastructure(Color.YELLOW, DEFAULT_WIRE_PRICE);


    private final Sprite sprite;
    private Color defaultColor;

    private double buildingCost;

    /**
     * Create instance of infrastructure
     * @param sprite
     * @param defaultColor color to display if spritemap is null, null if transparent
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
     * Create instance of infrastructure
     * @param defaultColor color to display instead of texture, null if transparent
     * @param buildingCost cost of building
     */
    private Infrastructure(@Nullable Color defaultColor, double buildingCost) {
        this(null, defaultColor, buildingCost);
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
