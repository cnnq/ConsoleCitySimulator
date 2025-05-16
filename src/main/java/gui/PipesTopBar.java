package gui;

import modes.EditMode;
import org.jetbrains.annotations.NotNull;
import other.Building;
import other.Game;
import other.WaterStats;

import javax.swing.*;
import java.awt.*;

public class PipesTopBar extends TopBar {

    private JLabel waterUsageLabel;
    private JLabel waterProductionLabel;
    private JLabel moneyLabel;

    private Building choosenBuilding;


    public PipesTopBar(@NotNull EditMode editMode) {
        super(editMode);

        waterUsageLabel = new JLabel("Usage: error", JLabel.CENTER);
        waterProductionLabel = new JLabel("Production: error", JLabel.CENTER);
        moneyLabel = new JLabel("Money: error", JLabel.CENTER);

        add(waterUsageLabel);
        add(waterProductionLabel);
        add(moneyLabel);
    }

    @Override
    public void paint(Graphics g) {
        Game game = getEditMode().getGame();
        WaterStats waterStats = game.getWaterStats();
        double money = game.getMoney();

        waterUsageLabel.setText("Usage: " + Double.toString(Math.round(waterStats.usage())) + " ");
        waterProductionLabel.setText("Production: " + Double.toString(Math.round(waterStats.production())) + " ");
        moneyLabel.setText("Money: " + Double.toString(Math.round(money)) + "k $");

        super.paint(g);
    }
}
