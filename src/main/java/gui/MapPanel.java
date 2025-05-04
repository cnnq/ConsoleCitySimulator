package gui;

import layers.TerrainLayer;
import layers.TerrainType;
import other.Game;
import other.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Pane on which map will be drawn
 */
public class MapPanel extends JPanel implements KeyListener, MouseListener, MouseMotionListener {

    private TerrainLayer terrain;

    private int xOffset, yOffset;


    public MapPanel() {
        setPreferredSize(new Dimension(Game.DEFAULT_WIDTH, Game.DEFAULT_HEIGHT - 64));

        terrain = GameState.getTerrain();

        // Set view to center of the map
        xOffset = (terrain.getWidth() - Game.DEFAULT_WIDTH / TerrainLayer.TILE_SIZE) / 2;
        yOffset = (terrain.getHeight() - (Game.DEFAULT_HEIGHT - 64) / TerrainLayer.TILE_SIZE) / 2;

        setFocusable(true);

        addKeyListener(this);
        addMouseListener(this);
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
    public void mouseClicked(MouseEvent e) {
        tryBuilding(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        tryBuilding(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }


    private void tryBuilding(MouseEvent e) {
        if (GameState.getMoney() < GameState.DEFAULT_BUILDING_PRICE) return;

        int x = e.getX() / TerrainLayer.TILE_SIZE + xOffset;
        int y = e.getY() / TerrainLayer.TILE_SIZE + yOffset;

        // If not out of bounds and terrain is suitable to build on
        if (x >= 0 && x < terrain.getWidth() && y >= 0 && y < terrain.getHeight() && terrain.get(x, y) == TerrainType.GRASS) {

            terrain.set(x, y, TerrainType.BUILDING);
            GameState.setMoney(GameState.getMoney() - GameState.DEFAULT_BUILDING_PRICE);
        }
    }
}
