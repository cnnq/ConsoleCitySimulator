package states;

import layers.TerrainLayer;
import layers.TerrainType;
import other.Game;
import other.MapPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class EditMode extends GameMode {

    private static final int TOP_BAR_HEIGHT = 150;
    private static final int BOTTOM_BAR_HEIGHT = 50;

    private final MapPanel map;


    public EditMode() {
        Container container = new Container();

        container.setLayout(new FlowLayout());

        JLabel top = new JLabel("Top", JLabel.CENTER);
        top.setPreferredSize(new Dimension(Game.DEFAULT_WIDTH, TOP_BAR_HEIGHT));

        map = new MapPanel();

        JLabel bottom = new JLabel("Bottom", JLabel.CENTER);
        top.setPreferredSize(new Dimension(Game.DEFAULT_WIDTH, BOTTOM_BAR_HEIGHT));

        container.add(top);
        container.add(map);
        container.add(bottom);

        container.addKeyListener(this);
        container.addMouseListener(this);
        container.addMouseMotionListener(this);

        setContentPane(container);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX() / TerrainLayer.TILE_SIZE;
        int y = (e.getY() - TOP_BAR_HEIGHT / 2) / TerrainLayer.TILE_SIZE;

        map.getTerrain().set(x, y, TerrainType.BUILDING);
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

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
