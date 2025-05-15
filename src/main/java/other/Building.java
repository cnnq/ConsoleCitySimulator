package other;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.EnumSet;

public class Building {

    private static final String SPRITEMAP_PATH = "res/spritemap.png";

    private static Image defaultSpritemap;
    static {
        try {
            defaultSpritemap = ImageIO.read(new File(SPRITEMAP_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Prices in thousands of dollars
    private static final double DEFAULT_ROAD_PRICE = 10;
    private static final double DEFAULT_HOUSING_AREA_PRICE = 10;
    private static final double DEFAULT_PIPE_PRICE = 1;
    private static final double DEFAULT_WIRE_PRICE = 1;

    public static final Building WATER = new Building(null, 0);
    public static final Building HOUSING_AREA = new Building(new Color(44, 230, 83), DEFAULT_HOUSING_AREA_PRICE);

    public static final Building ROAD = new Building(defaultSpritemap, 0, 0, true, DEFAULT_ROAD_PRICE);
    public static final Building PIPES = new Building(defaultSpritemap, 0, 1, true, DEFAULT_PIPE_PRICE);
    public static final Building WIRES = new Building(Color.YELLOW, DEFAULT_WIRE_PRICE);

    public static final Building SOLAR_PANELS = new Building(defaultSpritemap, 0, 2, false, DEFAULT_HOUSING_AREA_PRICE);
    public static final Building WATER_PUMP = new Building(defaultSpritemap, 1, 2, false, DEFAULT_HOUSING_AREA_PRICE);

    public static final Building HOUSE_1 = new Building(defaultSpritemap, 0, 3, false, DEFAULT_HOUSING_AREA_PRICE);
    public static final Building HOUSE_2 = new Building(defaultSpritemap, 1, 3, false, DEFAULT_HOUSING_AREA_PRICE);


    private final Image spritemap;
    private final int spritemapX;
    private final int spritemapY;
    private final boolean neighbourDependent;

    private Color color;

    private double buildingCost;

    /**
     * Create instance of building
     * @param spritemap from which texture should be taken
     * @param spritemapX x tile coordinate in spritemap
     * @param spritemapY y tile coordinate in spritemap
     * @param neighbourDependent should look of a tile differ depending on neighbouring tiles
     * @param buildingCost cost of building
     */
    private Building(@Nullable Image spritemap, @Range(from = 0, to = Integer.MAX_VALUE) int spritemapX, @Range(from = 0, to = Integer.MAX_VALUE) int spritemapY, boolean neighbourDependent, double buildingCost) {
        if (buildingCost < 0) throw new IllegalArgumentException("buildingCost cannot be negative");

        this.spritemap = spritemap;
        this.spritemapX = spritemapX;
        this.spritemapY = spritemapY;
        this.neighbourDependent = neighbourDependent;

        this.buildingCost = buildingCost;
    }

    /**
     * Create instance of building
     * @param color color that should be used instead of texture
     * @param buildingCost cost of building
     */
    private Building(@Nullable Color color, double buildingCost) {
        this(null, 0, 0, false, buildingCost);

        this.color = color;
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



        } else if (color != null) {
            g.setColor(color);
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
