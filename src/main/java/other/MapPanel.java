package other;

import layers.TerrainLayer;

import javax.swing.*;
import java.awt.*;

/**
 * Pane on which will be drawn map
 */
public class MapPanel extends JPanel {

    private TerrainLayer terrain;


    public MapPanel() {
        setPreferredSize(new Dimension(Game.DEFAULT_WIDTH, Game.DEFAULT_HEIGHT - 200));

        terrain = new TerrainLayer(200, 200, 10, 32);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        terrain.draw(g);
    }

    public TerrainLayer getTerrain() {
        return terrain;
    }
}
