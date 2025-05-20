package other;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.EnumSet;

public class Infrastructure {

    private static final String SPRITEMAP_PATH = "res/spritemap.png";

    protected static Image defaultSpritemap;
    static {
        try {
            defaultSpritemap = ImageIO.read(new File(SPRITEMAP_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Prices in thousands of dollars
    private static final double DEFAULT_ROAD_PRICE = 10;
    protected static final double DEFAULT_HOUSING_AREA_PRICE = 10;
    private static final double DEFAULT_PIPE_PRICE = 1;
    private static final double DEFAULT_WIRE_PRICE = 1;

    public static final Infrastructure WATER = new Infrastructure(null, 0);
    public static final Infrastructure HOUSING_AREA = new Infrastructure(new Color(44, 230, 83), DEFAULT_HOUSING_AREA_PRICE);

    public static final Infrastructure ROAD = new Infrastructure(defaultSpritemap, 0, 0, true, Color.BLACK, DEFAULT_ROAD_PRICE);
    public static final Infrastructure PIPES = new Infrastructure(defaultSpritemap, 0, 1, true, Color.GRAY, DEFAULT_PIPE_PRICE);
    public static final Infrastructure WIRES = new Infrastructure(Color.YELLOW, DEFAULT_WIRE_PRICE);


    private final Image spritemap;
    private final int spritemapX;
    private final int spritemapY;
    private final boolean neighbourDependent;

    private Color defaultColor;

    private double buildingCost;

    /**
     * Create instance of infrastructure
     * @param spritemap from which texture should be taken
     * @param spritemapX x tile coordinate in spritemap
     * @param spritemapY y tile coordinate in spritemap
     * @param neighbourDependent should look of a tile differ depending on neighbouring tiles
     * @param defaultColor color to display if spritemap is null, null if transparent
     * @param buildingCost cost of building
     */
    protected Infrastructure(@Nullable Image spritemap,
                             @Range(from = 0, to = Integer.MAX_VALUE) int spritemapX,
                             @Range(from = 0, to = Integer.MAX_VALUE) int spritemapY,
                             boolean neighbourDependent,
                             @Nullable Color defaultColor,
                             double buildingCost) {

        if (buildingCost < 0) throw new IllegalArgumentException("buildingCost cannot be negative");

        this.spritemap = spritemap;
        this.spritemapX = spritemapX;
        this.spritemapY = spritemapY;
        this.neighbourDependent = neighbourDependent;
        this.defaultColor = defaultColor;

        this.buildingCost = buildingCost;
    }

    /**
     * Create instance of infrastructure
     * @param defaultColor color to display instead of texture, null if transparent
     * @param buildingCost cost of building
     */
    private Infrastructure(@Nullable Color defaultColor, double buildingCost) {
        this(null, 0, 0, false, defaultColor, buildingCost);
    }

    /**
     * Draws current building
     * @param g
     * @param x displayed tile coordinate
     * @param y displayed tile coordinate
     * @param neighbourData null if {$code isNeighbourDependent() is false}
     */
    public void draw(Graphics g, int x, int y, EnumSet<Directions> neighbourData) {
        int displayXPixel = x * Game.TILE_SIZE;
        int displayYPixel = y * Game.TILE_SIZE;

        if (spritemap != null) {
            int spritemapXPixel = spritemapX * Game.TILE_SIZE;
            int spritemapYPixel = spritemapY * Game.TILE_SIZE;

            if (neighbourDependent) {
                int spritemapXOffset = 0;
                if (neighbourData.contains(Directions.UP)) spritemapXOffset += 1;
                if (neighbourData.contains(Directions.RIGHT)) spritemapXOffset += 2;
                if (neighbourData.contains(Directions.DOWN)) spritemapXOffset += 4;
                if (neighbourData.contains(Directions.LEFT)) spritemapXOffset += 8;

                spritemapXPixel += spritemapXOffset * Game.TILE_SIZE;
            }

            g.drawImage(spritemap,
                    displayXPixel, displayYPixel,
                    displayXPixel + Game.TILE_SIZE, displayYPixel + Game.TILE_SIZE,
                    spritemapXPixel, spritemapYPixel,
                    spritemapXPixel + Game.TILE_SIZE, spritemapYPixel + Game.TILE_SIZE, null);



        } else if (defaultColor != null) {
            g.setColor(defaultColor);
            g.fillRect(displayXPixel, displayYPixel, Game.TILE_SIZE, Game.TILE_SIZE);
        }
    }


    public boolean isNeighbourDependent() {
        return neighbourDependent;
    }

    public double getBuildingCost() {
        return buildingCost;
    }

}
