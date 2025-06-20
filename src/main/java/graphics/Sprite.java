package graphics;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import data.Directions;

import javax.swing.*;
import java.awt.*;
import java.util.EnumSet;

public class Sprite implements Icon {

    public static final int DEFAULT_SPRITE_SIZE = 32;

    public static final Sprite ROAD_ICON = new Sprite(10, 0, false);

    public static final Sprite HOUSING_AREA_ICON = new Sprite(1, 4, false);
    public static final Sprite COMMERCIAL_AREA_ICON = new Sprite(0, 5, false);
    public static final Sprite INDUSTRIAL_AREA_ICON = new Sprite(0, 6, false);

    public static final Sprite SOLAR_PANELS_ICON = new Sprite(3, 3, false);
    public static final Sprite WATER_PUMP_ICON = new Sprite(4, 3, false);

    public static final Sprite FIRE_STATION_ICON = new Sprite(5, 3, false);
    public static final Sprite SCHOOL_ICON = new Sprite(6, 3, false);
    public static final Sprite HOSPITAL_ICON = new Sprite(7, 3, false);

    private final Spritemap spritemap;
    private final int spritemapX;
    private final int spritemapY;
    private boolean neighbourDependent;


    /**
     * Create instance of sprite
     * @param spritemap from which sprite should be taken
     * @param x tile coordinate
     * @param y tile coordinate
     * @param neighbourDependent true if it's look should differ depending on neighbouring sprites
     */
    public Sprite(@NotNull Spritemap spritemap, @Range(from = 0, to = Integer.MAX_VALUE) int x, @Range(from = 0, to = Integer.MAX_VALUE) int y, boolean neighbourDependent) {
        if (x < 0) throw new IllegalArgumentException("x cannot be negative");
        if (y < 0) throw new IllegalArgumentException("y cannot be negative");

        this.spritemap = spritemap;
        this.spritemapX = x;
        this.spritemapY = y;
        this.neighbourDependent = neighbourDependent;
    }

    /**
     * Create instance of sprite
     * @param x tile coordinate
     * @param y tile coordinate
     * @param neighbourDependent true if it's look should differ depending on neighbouring sprites
     */
    @JsonCreator
    public Sprite(@JsonProperty("x") @Range(from = 0, to = Integer.MAX_VALUE) int x,
                  @JsonProperty("y") @Range(from = 0, to = Integer.MAX_VALUE) int y,
                  @JsonProperty("neighbourDependent") boolean neighbourDependent) {

        this(Spritemap.DEFAULT, x, y, neighbourDependent);
    }

    /**
     * Draws sprite
     * @param g
     * @param x displayed tile coordinate
     * @param y displayed tile coordinate
     * @param neighbourData null if {$code isNeighbourDependent() is false}
     */
    public void draw(Graphics g, int x, int y, EnumSet<Directions> neighbourData) {
        int displayXPixel = x * DEFAULT_SPRITE_SIZE;
        int displayYPixel = y * DEFAULT_SPRITE_SIZE;

        int spritemapXPixel = spritemapX * DEFAULT_SPRITE_SIZE;
        int spritemapYPixel = spritemapY * DEFAULT_SPRITE_SIZE;

        if (neighbourDependent) {
            int spritemapXOffset = 0;
            if (neighbourData.contains(Directions.UP)) spritemapXOffset += 1;
            if (neighbourData.contains(Directions.RIGHT)) spritemapXOffset += 2;
            if (neighbourData.contains(Directions.DOWN)) spritemapXOffset += 4;
            if (neighbourData.contains(Directions.LEFT)) spritemapXOffset += 8;

            spritemapXPixel += spritemapXOffset * DEFAULT_SPRITE_SIZE;
        }

        g.drawImage(spritemap.getImage(),
                displayXPixel, displayYPixel,
                displayXPixel + DEFAULT_SPRITE_SIZE, displayYPixel + DEFAULT_SPRITE_SIZE,
                spritemapXPixel, spritemapYPixel,
                spritemapXPixel + DEFAULT_SPRITE_SIZE, spritemapYPixel + DEFAULT_SPRITE_SIZE, null);
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        int spritemapXPixel = spritemapX * DEFAULT_SPRITE_SIZE;
        int spritemapYPixel = spritemapY * DEFAULT_SPRITE_SIZE;

        g.drawImage(spritemap.getImage(),
                x, y,
                x + DEFAULT_SPRITE_SIZE, y + DEFAULT_SPRITE_SIZE,
                spritemapXPixel, spritemapYPixel,
                spritemapXPixel + DEFAULT_SPRITE_SIZE, spritemapYPixel + DEFAULT_SPRITE_SIZE, null);
    }

    @Override
    public int getIconWidth() {
        return DEFAULT_SPRITE_SIZE;
    }

    @Override
    public int getIconHeight() {
        return DEFAULT_SPRITE_SIZE;
    }

    public boolean isNeighbourDependent() {
        return neighbourDependent;
    }
}
