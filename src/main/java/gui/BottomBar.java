package gui;

import org.jetbrains.annotations.NotNull;
import other.Game;
import other.GameState;
import states.EditMode;

import javax.swing.*;
import java.awt.*;

public class BottomBar extends JMenuBar {

    private static final int BOTTOM_BAR_HEIGHT = 32;

    private final JButton cityEditButton;
    private final JButton pipesEditButton;


    public BottomBar(@NotNull EditMode editMode) {
        setPreferredSize(new Dimension(Game.DEFAULT_WIDTH, BOTTOM_BAR_HEIGHT));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        cityEditButton = new JButton("Edit city");
        pipesEditButton = new JButton("Edit pipes");

        cityEditButton.addActionListener(e -> {
            editMode.setLayer(GameState.getCity());
        });

        pipesEditButton.addActionListener(e -> {
            editMode.setLayer(GameState.getPipes());
        });

        add(cityEditButton);
        add(pipesEditButton);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }
}
