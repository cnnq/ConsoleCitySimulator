package gui;

import org.jetbrains.annotations.NotNull;
import other.Game;
import other.GameState;
import states.EditMode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Pane on which map will be drawn
 */
public class MapPanel extends JPanel implements KeyListener, MouseListener, MouseMotionListener {

    private final EditMode editMode;
    private int xOffset, yOffset;

    private Point selectionFrom, selectionTo;


    public MapPanel(@NotNull EditMode editMode) {
        setPreferredSize(new Dimension(Game.DEFAULT_WIDTH, Game.DEFAULT_HEIGHT - 64));

        this.editMode = editMode;

        // Set view to center of the map
        xOffset = (editMode.getLayer().getWidth() - Game.DEFAULT_WIDTH / GameState.TILE_SIZE) / 2;
        yOffset = (editMode.getLayer().getHeight() - (Game.DEFAULT_HEIGHT - 64) / GameState.TILE_SIZE) / 2;

        setFocusable(true);

        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw layer
        editMode.getLayer().draw(g, xOffset, yOffset, getWidth() / GameState.TILE_SIZE, getHeight() / GameState.TILE_SIZE);

        // Draw selected area
        if (selectionFrom != null && selectionTo != null) {
            g.setColor(Color.BLUE);

            Point a = new Point(Math.min(selectionFrom.x, selectionTo.x), Math.min(selectionFrom.y, selectionTo.y));
            Point b = new Point(Math.max(selectionFrom.x, selectionTo.x), Math.max(selectionFrom.y, selectionTo.y));

            g.fillRect((a.x - xOffset) * GameState.TILE_SIZE,
                    (a.y - yOffset) * GameState.TILE_SIZE,
                    (b.x - a.x + 1) * GameState.TILE_SIZE,
                    (b.y - a.y + 1) * GameState.TILE_SIZE);
        }
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

    }

    @Override
    public void mousePressed(MouseEvent e) {
        selectionFrom = getTilePosition(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        selectionTo = getTilePosition(e);

        // Convert selected points to a nice rectangle
        Point a = new Point(Math.min(selectionFrom.x, selectionTo.x), Math.min(selectionFrom.y, selectionTo.y));
        Point b = new Point(Math.max(selectionFrom.x, selectionTo.x), Math.max(selectionFrom.y, selectionTo.y));
        Rectangle rectangle = new Rectangle(a, new Dimension(b.x - a.x, b.y - a.y));

        editMode.getLayer().edit(rectangle, e.getButton());

        // Release selected area
        selectionFrom = null;
        selectionTo = null;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        selectionTo = getTilePosition(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }


    private Point getTilePosition(MouseEvent e) {
        return new Point(Math.clamp(e.getX() / GameState.TILE_SIZE + xOffset, 0, editMode.getLayer().getWidth() - 1),
                         Math.clamp(e.getY() / GameState.TILE_SIZE + yOffset, 0, editMode.getLayer().getHeight() - 1));
    }
}
