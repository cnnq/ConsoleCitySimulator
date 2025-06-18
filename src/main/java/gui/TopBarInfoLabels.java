package gui;

import data.PopulationStats;
import main.Game;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * Manages labels used only by {@link TopBar}
 */
class TopBarInfoLabels extends JPanel {

    private final JLabel populationStringLabel;
    private final JLabel moneyStringLabel;

    private final JLabel populationLabel;
    private final JLabel moneyLabel;

    private final TopBar topBar;


    public TopBarInfoLabels(@NotNull TopBar topBar) {
        this.topBar = topBar;

        setLayout(new GridLayout(2, 2));

        populationStringLabel = new JLabel("Population:", JLabel.CENTER);
        moneyStringLabel = new JLabel("Money:", JLabel.CENTER);

        populationLabel = new JLabel("error", JLabel.CENTER);
        moneyLabel = new JLabel("error", JLabel.CENTER);

        add(populationStringLabel);
        add(moneyStringLabel);
        add(populationLabel);
        add(moneyLabel);
    }

    @Override
    public void paint(Graphics g) {
        Game game = topBar.getEditMode().getGame();
        PopulationStats populationStats = game.getMigrationSystem().getPopulationStats();

        populationLabel.setText(populationStats.population() + " / " + populationStats.capacity());
        moneyLabel.setText(String.format("%.1f", game.getFinancialSystem().getMoney()) + "k $");

        super.paint(g);
    }
}
