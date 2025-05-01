package other;

import layers.TerrainLayer;
import layers.TerrainType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Pane on which will be drawn map
 */
public class MapPanel extends JPanel implements KeyListener, MouseMotionListener {

    public static final int DEFAULT_MAP_SIZE = 200;

    private TerrainLayer terrain;

    private int xOffset, yOffset;


    public MapPanel() {
        setPreferredSize(new Dimension(Game.DEFAULT_WIDTH, Game.DEFAULT_HEIGHT - 64));

        terrain = new TerrainLayer(DEFAULT_MAP_SIZE, DEFAULT_MAP_SIZE, 10, 64);

        // Set view to center of the map
        xOffset = (terrain.getWidth() - Game.DEFAULT_WIDTH / TerrainLayer.TILE_SIZE) / 2;
        yOffset = (terrain.getHeight() - (Game.DEFAULT_HEIGHT - 64) / TerrainLayer.TILE_SIZE) / 2;

        setFocusable(true);

        addKeyListener(this);
        addMouseMotionListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        terrain.draw(g, xOffset, yOffset, getWidth(), getHeight());
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                xOffset++;
                break;
            case KeyEvent.VK_LEFT:
                xOffset--;
                break;
            case KeyEvent.VK_UP:
                yOffset--;
                break;
            case KeyEvent.VK_DOWN:
                yOffset++;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int x = e.getX() / TerrainLayer.TILE_SIZE + xOffset;
        int y = e.getY() / TerrainLayer.TILE_SIZE + yOffset;
        if (x >= 0 && x < terrain.getWidth() && y >= 0 && y < terrain.getHeight()) {
            terrain.set(x, y, TerrainType.BUILDING);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
