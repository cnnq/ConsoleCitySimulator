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

    private final JLabel waterStatsLabel;


    public PipesTopBar(@NotNull TopBar topBar) {
        super(topBar);

        waterStatsLabel = new JLabel("Usage: error", JLabel.CENTER);
        var infoLabels = new TopBarInfoLabels(topBar);

        add(waterStatsLabel, BorderLayout.WEST);
        add(infoLabels, BorderLayout.EAST);
    }

    @Override
    public void paint(Graphics g) {
        Game game = getTopBar().getEditMode().getGame();
        WaterStats waterStats = game.getUrbanizationSystem().getWaterStats();

        waterStatsLabel.setText("Usage: " + String.format("%.1f", waterStats.usage()) + " / " + String.format("%.1f", waterStats.production()));

        super.paint(g);
    }
}
