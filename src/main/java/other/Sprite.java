package other;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import javax.swing.*;
import java.awt.*;
import java.util.EnumSet;

public class Sprite implements Icon {

    public static final int DEFAULT_SPRITE_SIZE = 32;

    public static final Sprite STRAIGHT_HORIZONTAL_ROAD = new Sprite(Spritemap.DEFAULT, 10, 0, false);

    public static final Sprite ROAD = new Sprite(Spritemap.DEFAULT, 0, 0, true);
    public static final Sprite PIPES = new Sprite(Spritemap.DEFAULT, 0, 1, true);

    public static final Sprite SOLAR_PANELS = new Sprite(Spritemap.DEFAULT, 0, 2, false);
    public static final Sprite WATER_PUMP = new Sprite(Spritemap.DEFAULT, 1, 2, false);

    public static final Sprite HOUSE_1 = new Sprite(Spritemap.DEFAULT, 0, 3, false);
    public static final Sprite HOUSE_2 = new Sprite(Spritemap.DEFAULT, 1, 3, false);

    private final Spritemap spritemap;
    private final int spritemapX;
    private final int spritemapY;
    private boolean neighbourDependent;


    /**
     * Create instance of sprite
     * @param spritemap from which sprite should be taken
     * @param x tile coordinate in spritemap
     * @param y tile coordinate in spritemap
     * @param neighbourDependent true if it's look should differ depending on neighbouring sprites
     */
    public Sprite(@NotNull Spritemap spritemap, @Range(from = 0, to = Integer.MAX_VALUE) int x, @Range(from = 0, to = Integer.MAX_VALUE) int y, boolean neighbourDependent) {
        this.spritemap = spritemap;
        this.spritemapX = x;
        this.spritemapY = y;
        this.neighbourDependent = neighbourDependent;
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
