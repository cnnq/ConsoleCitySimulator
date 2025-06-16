package gui;

import org.jetbrains.annotations.NotNull;
import main.Game;
import data.WaterStats;

import javax.swing.*;
import java.awt.*;

/**
 * Top bar which enables editing {@link layers.PipesLayer}
 */
public class PipesTopBar extends TopBarInstance {

    private JLabel waterStatsLabel;
    private JLabel moneyLabel;


    public PipesTopBar(@NotNull TopBar topBar) {
        super(topBar);

        waterStatsLabel = new JLabel("Usage: error", JLabel.CENTER);
        moneyLabel = new JLabel("Money: error", JLabel.CENTER);

        add(waterStatsLabel, BorderLayout.WEST);
        add(moneyLabel, BorderLayout.EAST);
    }

    @Override
    public void paint(Graphics g) {
        Game game = getTopBar().getEditMode().getGame();
        WaterStats waterStats = game.getWaterStats();

        waterStatsLabel.setText("Usage: " + String.format("%.1f", waterStats.usage()) + " / " + String.format("%.1f", waterStats.production()));
        moneyLabel.setText("Money: " + String.format("%.1f", game.getFinancialSystem().getMoney())  + "k $");

        super.paint(g);
    }
}
