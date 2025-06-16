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

    private JLabel electricityStatsLabel;
    private JLabel moneyLabel;


    public WiresTopBar(@NotNull TopBar topBar) {
        super(topBar);

        electricityStatsLabel = new JLabel("Usage: error", JLabel.CENTER);
        moneyLabel = new JLabel("Money: error", JLabel.CENTER);

        add(electricityStatsLabel, BorderLayout.WEST);
        add(moneyLabel, BorderLayout.EAST);
    }

    @Override
    public void paint(Graphics g) {
        Game game = getTopBar().getEditMode().getGame();
        ElectricityStats electricityStats = game.getElectricityStats();

        electricityStatsLabel.setText("Usage: " + String.format("%.1f", electricityStats.usage()) + " / " + String.format("%.1f", electricityStats.production()));
        moneyLabel.setText("Money: " + String.format("%.1f", game.getFinancialSystem().getMoney()) + "k $");

        super.paint(g);
    }
}
