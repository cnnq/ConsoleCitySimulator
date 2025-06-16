package gui;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public abstract class TopBarInstance extends JPanel {

    private final TopBar topBar;


    public TopBarInstance(@NotNull TopBar topBar) {
        this.topBar = topBar;

        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setLayout(new BorderLayout());
    }

    @NotNull
    public TopBar getTopBar() {
        return topBar;
    }
}
