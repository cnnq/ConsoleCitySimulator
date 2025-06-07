package gui;

import org.jetbrains.annotations.NotNull;
import modes.EditMode;

import javax.swing.*;
import java.awt.*;

public class BottomBar extends JMenuBar {

    private static final int BOTTOM_BAR_HEIGHT = 32;

    private final TopBar cityTopBar;
    private final TopBar pipesTopBar;
    private final TopBar wiresTopBar;

    private final JButton cityEditButton;
    private final JButton pipesEditButton;
    private final JButton wiresEditButton;


    public BottomBar(@NotNull EditMode editMode) {
        setPreferredSize(new Dimension(GameWindow.DEFAULT_WIDTH, BOTTOM_BAR_HEIGHT));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        cityTopBar = new CityTopBar(editMode);
        pipesTopBar = new PipesTopBar(editMode);
        wiresTopBar = new WiresTopBar(editMode);

        cityEditButton = new JButton("Edit city");
        pipesEditButton = new JButton("Edit pipes");
        wiresEditButton = new JButton("Edit wires");

        cityEditButton.addActionListener(e -> {
            editMode.setTopBar(cityTopBar);
            editMode.setLayer(editMode.getGame().getCityMap());
        });

        pipesEditButton.addActionListener(e -> {
            editMode.setTopBar(pipesTopBar);
            editMode.setLayer(editMode.getGame().getPipesMap());
        });

        wiresEditButton.addActionListener(e -> {
            editMode.setTopBar(wiresTopBar);
            editMode.setLayer(editMode.getGame().getWiresMap());
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
