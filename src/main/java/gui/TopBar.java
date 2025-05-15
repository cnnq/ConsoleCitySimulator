package gui;

import org.jetbrains.annotations.NotNull;
import modes.EditMode;

import javax.swing.*;
import java.awt.*;

public abstract class TopBar extends JMenuBar {

    private static final int TOP_BAR_HEIGHT = 32;

    private final EditMode editMode;


    public TopBar(@NotNull EditMode editMode) {
        setPreferredSize(new Dimension(GameWindow.DEFAULT_WIDTH, TOP_BAR_HEIGHT));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        this.editMode = editMode;
    }

    @NotNull
    public EditMode getEditMode() {
        return editMode;
    }
}
