package gui;

import modes.EditMode;
import org.jetbrains.annotations.NotNull;
import other.Building;
import other.ElectricityStats;
import other.Game;

import javax.swing.*;
import java.awt.*;

public class WiresTopBar extends TopBar {

    private JLabel electricityUsageLabel;
    private JLabel electricityProductionLabel;
    private JLabel moneyLabel;

    private Building choosenBuilding;


    public WiresTopBar(@NotNull EditMode editMode) {
        super(editMode);

        electricityUsageLabel = new JLabel("Usage: error", JLabel.CENTER);
        electricityProductionLabel = new JLabel("Production: error", JLabel.CENTER);
        moneyLabel = new JLabel("Money: error", JLabel.CENTER);

        add(electricityUsageLabel);
        add(electricityProductionLabel);
        add(moneyLabel);
    }

    @Override
    public void paint(Graphics g) {
        Game game = getEditMode().getGame();
        ElectricityStats electricityStats = game.getElectricityStats();
        double money = game.getMoney();

        electricityUsageLabel.setText("Usage: " + Double.toString(Math.round(electricityStats.usage())) + " ");
        electricityProductionLabel.setText("Production: " + Double.toString(Math.round(electricityStats.production())) + " ");
        moneyLabel.setText("Money: " + Double.toString(Math.round(money)) + "k $");

        super.paint(g);
    }
}
