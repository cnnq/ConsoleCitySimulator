package gui;

import org.jetbrains.annotations.NotNull;
import data.ElectricityStats;
import main.Game;

import javax.swing.*;
import java.awt.*;

/**
 * Top bar which enables editing {@link layers.WiresLayer}
 */
public class WiresTopBar extends TopBarInstance {

    private final JLabel electricityStatsLabel;


    public WiresTopBar(@NotNull TopBar topBar) {
        super(topBar);

        electricityStatsLabel = new JLabel("Usage: error", JLabel.CENTER);
        var infoLabels = new TopBarInfoLabels(topBar);

        add(electricityStatsLabel, BorderLayout.WEST);
        add(infoLabels, BorderLayout.EAST);
    }

    @Override
    public void paint(Graphics g) {
        Game game = getTopBar().getEditMode().getGame();
        ElectricityStats electricityStats = game.getUrbanizationSystem().getElectricityStats();

        electricityStatsLabel.setText("Usage: " + String.format("%.1f", electricityStats.usage()) + " / " + String.format("%.1f", electricityStats.production()));

        super.paint(g);
    }
}
