package states;

import org.jetbrains.annotations.NotNull;

import java.awt.*;

/**
 * Defines how information will be displayed and how input should be processed.
 */
public abstract class GameMode {

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
