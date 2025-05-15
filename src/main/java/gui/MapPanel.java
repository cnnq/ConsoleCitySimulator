package gui;

import org.jetbrains.annotations.NotNull;
import other.Game;
import modes.EditMode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Pane on which map will be drawn
 */
public class MapPanel extends JPanel implements MouseListener, MouseMotionListener {

    private final EditMode editMode;
    private int xOffset, yOffset;

    private Point selectionFrom, selectionTo;


    public MapPanel(@NotNull EditMode editMode) {
        setPreferredSize(new Dimension(GameWindow.DEFAULT_WIDTH, GameWindow.DEFAULT_HEIGHT - 64));

        this.editMode = editMode;

        // Set view to center of the map
        xOffset = (editMode.getLayer().getWidth() - GameWindow.DEFAULT_WIDTH / Game.TILE_SIZE) / 2;
        yOffset = (editMode.getLayer().getHeight() - (GameWindow.DEFAULT_HEIGHT - 64) / Game.TILE_SIZE) / 2;

        addMouseListener(this);
        addMouseMotionListener(this);

        initializeKeyBindings();
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw layer
        editMode.getLayer().draw(g, xOffset, yOffset, getWidth() / Game.TILE_SIZE, getHeight() / Game.TILE_SIZE);

        // Draw selected area
        if (selectionFrom != null && selectionTo != null) {
            g.setColor(Color.BLUE);

            Point a = new Point(Math.min(selectionFrom.x, selectionTo.x), Math.min(selectionFrom.y, selectionTo.y));
            Point b = new Point(Math.max(selectionFrom.x, selectionTo.x), Math.max(selectionFrom.y, selectionTo.y));

            g.fillRect((a.x - xOffset) * Game.TILE_SIZE,
                    (a.y - yOffset) * Game.TILE_SIZE,
                    (b.x - a.x + 1) * Game.TILE_SIZE,
                    (b.y - a.y + 1) * Game.TILE_SIZE);
        }
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


    private void initializeKeyBindings() {
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "moveUp");
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "moveRight");
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "moveDown");
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "moveLeft");

        getActionMap().put("moveUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                yOffset--;
            }
        });

        getActionMap().put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xOffset++;
            }
        });

        getActionMap().put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                yOffset++;
            }
        });

        getActionMap().put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xOffset--;
            }
        });
    }

    private Point getTilePosition(MouseEvent e) {
        return new Point(Math.clamp(e.getX() / Game.TILE_SIZE + xOffset, 0, editMode.getLayer().getWidth() - 1),
                         Math.clamp(e.getY() / Game.TILE_SIZE + yOffset, 0, editMode.getLayer().getHeight() - 1));
    }
}
