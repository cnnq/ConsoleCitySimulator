package gui;

import modes.EditMode;
import org.jetbrains.annotations.NotNull;
import data.ElectricityStats;
import main.Game;

import javax.swing.*;
import java.awt.*;

public class WiresTopBar extends TopBar {

    private JLabel electricityStatsLabel;
    private JLabel moneyLabel;


    public WiresTopBar(@NotNull EditMode editMode) {
        super(editMode);

        electricityStatsLabel = new JLabel("Usage: error", JLabel.CENTER);
        moneyLabel = new JLabel("Money: error", JLabel.CENTER);

        add(electricityStatsLabel);
        add(moneyLabel);
    }

    @Override
    public void paint(Graphics g) {
        Game game = getEditMode().getGame();
        ElectricityStats electricityStats = game.getElectricityStats();

        electricityStatsLabel.setText("Usage: " + String.format("%.1f", electricityStats.usage()) + " / " + String.format("%.1f", electricityStats.production()) + " ");
        moneyLabel.setText("Money: " + String.format("%.1f", game.getMoney()) + "k $");

        super.paint(g);
    }
}
