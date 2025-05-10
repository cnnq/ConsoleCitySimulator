package gui;

import org.jetbrains.annotations.NotNull;
import other.Game;
import modes.EditMode;

import javax.swing.*;
import java.awt.*;

public class BottomBar extends JMenuBar {

    private static final int BOTTOM_BAR_HEIGHT = 32;

    private final JButton cityEditButton;
    private final JButton pipesEditButton;
    private final JButton wiresEditButton;


    public BottomBar(@NotNull EditMode editMode) {
        setPreferredSize(new Dimension(Game.DEFAULT_WIDTH, BOTTOM_BAR_HEIGHT));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        cityEditButton = new JButton("Edit city");
        pipesEditButton = new JButton("Edit pipes");
        wiresEditButton = new JButton("Edit wires");

        cityEditButton.addActionListener(e -> {
            editMode.setLayer(editMode.getGameState().getCityMap());
        });

        pipesEditButton.addActionListener(e -> {
            editMode.setLayer(editMode.getGameState().getPipesMap());
        });

        wiresEditButton.addActionListener(e -> {
            editMode.setLayer(editMode.getGameState().getWiresMap());
        });

        add(cityEditButton);
        add(pipesEditButton);
        add(wiresEditButton);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }
}
