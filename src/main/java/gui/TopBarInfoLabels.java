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

    private final JLabel happinessLabel;
    private final JLabel populationLabel;
    private final JLabel moneyLabel;

    private final TopBar topBar;


    public TopBarInfoLabels(@NotNull TopBar topBar) {
        this.topBar = topBar;

        setLayout(new GridLayout(2, 3));

        JLabel happinessStringLabel = new JLabel("Happiness:", JLabel.CENTER);
        JLabel populationStringLabel = new JLabel("Population:", JLabel.CENTER);
        JLabel moneyStringLabel = new JLabel("Money:", JLabel.CENTER);

        happinessLabel = new JLabel("error", JLabel.CENTER);
        populationLabel = new JLabel("error", JLabel.CENTER);
        moneyLabel = new JLabel("error", JLabel.CENTER);

        add(happinessStringLabel);
        add(populationStringLabel);
        add(moneyStringLabel);
        add(happinessLabel);
        add(populationLabel);
        add(moneyLabel);
    }

    @Override
    public void paint(Graphics g) {
        Game game = topBar.getEditMode().getGame();
        PopulationStats populationStats = game.getPopulationSystem().getPopulationStats();

        happinessLabel.setText(String.format("%.1f", game.getPopulationSystem().getHappiness() * 100) + "%");
        populationLabel.setText(populationStats.population() + " / " + populationStats.residences());
        moneyLabel.setText(String.format("%.1f", game.getFinancialSystem().getMoney()) + "k $");

        super.paint(g);
    }
}
