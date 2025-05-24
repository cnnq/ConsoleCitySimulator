package gui;

import modes.EditMode;
import org.jetbrains.annotations.NotNull;
import infrastructure.Infrastructure;
import other.Game;
import data.WaterStats;

import javax.swing.*;
import java.awt.*;

public class PipesTopBar extends TopBar {

    private JLabel waterStatsLabel;
    private JLabel moneyLabel;


    public PipesTopBar(@NotNull EditMode editMode) {
        super(editMode);

        waterStatsLabel = new JLabel("Usage: error", JLabel.CENTER);
        moneyLabel = new JLabel("Money: error", JLabel.CENTER);

        add(waterStatsLabel);
        add(moneyLabel);
    }

    @Override
    public void paint(Graphics g) {
        Game game = getEditMode().getGame();
        WaterStats waterStats = game.getWaterStats();

        waterStatsLabel.setText("Usage: " + String.format("%.1f", waterStats.usage()) + " / " + String.format("%.1f", waterStats.production()) + " ");
        moneyLabel.setText("Money: " + String.format("%.1f", game.getMoney())  + "k $");

        super.paint(g);
    }
}
