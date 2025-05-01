package other;

import layers.TerrainLayer;
import layers.TerrainType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Pane on which will be drawn map
 */
public class MapPanel extends JPanel implements MouseMotionListener {

    private TerrainLayer terrain;


    public MapPanel() {
        setPreferredSize(new Dimension(Game.DEFAULT_WIDTH, Game.DEFAULT_HEIGHT - 64));

        terrain = new TerrainLayer(200, 200, 10, 32);

        addMouseMotionListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        terrain.draw(g);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int x = e.getX() / TerrainLayer.TILE_SIZE;
        int y = e.getY() / TerrainLayer.TILE_SIZE;
        if (x >= 0 && x < terrain.getWidth() && y >= 0 && y < terrain.getHeight()) terrain.set(x, y, TerrainType.BUILDING);
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
