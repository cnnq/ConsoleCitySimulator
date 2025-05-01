package states;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public abstract class GameMode implements KeyListener, MouseListener, MouseMotionListener {

    private Container contentPane;

    /**
     * Returns the contentPane object for this frame.
     */
    @NotNull
    public Container getContentPane() {
        return contentPane;
    }

    /**
     * Sets the contentPane property. This method is called by the constructor.
     */
    public void setContentPane(@NotNull Container container) {
        this.contentPane = container;
    }
}
