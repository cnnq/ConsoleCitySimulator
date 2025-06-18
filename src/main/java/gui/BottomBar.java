package gui;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * Bottom part of GUI used to swap {@link TopBar} functionality
 */
class BottomBar extends JPanel {

    public BottomBar(@NotNull EditMode editMode) {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JButton cityEditButton = new JButton("Edit city");
        JButton pipesEditButton = new JButton("Edit pipes");
        JButton wiresEditButton = new JButton("Edit wires");
        JButton economyButton = new JButton("Economy");

        cityEditButton.addActionListener(e -> {
            editMode.getTopBar().setCurrentTopBarInstance("CITY");
            editMode.setLayer(editMode.getGame().getCityMap());
        });

        pipesEditButton.addActionListener(e -> {
            editMode.getTopBar().setCurrentTopBarInstance("PIPES");
            editMode.setLayer(editMode.getGame().getPipesMap());
        });

        wiresEditButton.addActionListener(e -> {
            editMode.getTopBar().setCurrentTopBarInstance("WIRES");
            editMode.setLayer(editMode.getGame().getWiresMap());
        });

        economyButton.addActionListener(e -> {
            editMode.getTopBar().setCurrentTopBarInstance("ECONOMY");
        });

        add(cityEditButton);
        add(pipesEditButton);
        add(wiresEditButton);
        add(economyButton);
    }
}
