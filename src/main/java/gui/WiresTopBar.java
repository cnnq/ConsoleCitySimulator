package gui;

import modes.EditMode;
import org.jetbrains.annotations.NotNull;
import other.Building;

import javax.swing.*;
import java.awt.*;

public class WiresTopBar extends TopBar {

    private JLabel moneyLabel;

    private Building choosenBuilding;


    public WiresTopBar(@NotNull EditMode editMode) {
        super(editMode);

        moneyLabel = new JLabel("Money: error", JLabel.CENTER);

        add(moneyLabel);
    }

    @Override
    public void paint(Graphics g) {
        moneyLabel.setText("Money: " + Double.toString(Math.round(getEditMode().getGame().getMoney())) + "k $");
        super.paint(g);
    }
}
