package gui;

import data.PopulationStats;
import infrastructure.Building;
import infrastructure.Infrastructure;
import modes.EditMode;
import org.jetbrains.annotations.NotNull;
import other.*;

import javax.swing.*;
import java.awt.*;

public class CityTopBar extends TopBar {

    public static int TAX_STEPS = 20;

    private final JButton roadButton;
    private final JButton housingAreaButton;
    private final JButton solarPanelButton;
    private final JButton waterPumpButton;

    private JLabel taxLabel;
    private JSlider taxSlider;

    private JLabel populationLabel;
    private JLabel moneyLabel;

    private Infrastructure choosenInfrastructure;


    public CityTopBar(@NotNull EditMode editMode) {
        super(editMode);

        choosenInfrastructure = Infrastructure.ROAD;

        // Buttons
        roadButton = new JButton(Sprite.STRAIGHT_HORIZONTAL_ROAD);
        housingAreaButton = new JButton(Sprite.HOUSE_2);
        solarPanelButton = new JButton(Sprite.SOLAR_PANELS);
        waterPumpButton = new JButton(Sprite.WATER_PUMP);

        roadButton.setPreferredSize(new Dimension(Sprite.DEFAULT_SPRITE_SIZE, Sprite.DEFAULT_SPRITE_SIZE));
        housingAreaButton.setPreferredSize(new Dimension(Sprite.DEFAULT_SPRITE_SIZE, Sprite.DEFAULT_SPRITE_SIZE));
        solarPanelButton.setPreferredSize(new Dimension(Sprite.DEFAULT_SPRITE_SIZE, Sprite.DEFAULT_SPRITE_SIZE));
        waterPumpButton.setPreferredSize(new Dimension(Sprite.DEFAULT_SPRITE_SIZE, Sprite.DEFAULT_SPRITE_SIZE));

        roadButton.addActionListener(e -> {
            choosenInfrastructure = Infrastructure.ROAD;
        });

        housingAreaButton.addActionListener(e -> {
            choosenInfrastructure = Infrastructure.HOUSING_AREA;
        });

        solarPanelButton.addActionListener(e -> {
            choosenInfrastructure = Building.SOLAR_PANELS;
        });

        waterPumpButton.addActionListener(e -> {
            choosenInfrastructure = Building.WATER_PUMP;
        });

        add(roadButton);
        add(housingAreaButton);
        add(solarPanelButton);
        add(waterPumpButton);

        // Labels
        taxLabel = new JLabel("Tax: error", JLabel.CENTER);
        taxSlider = new JSlider(JSlider.HORIZONTAL, 0, TAX_STEPS, TAX_STEPS / 10);

        taxSlider.addChangeListener(e -> {
            getEditMode().getGame().setTax((double)taxSlider.getValue() / TAX_STEPS);
        });

        add(taxLabel);
        add(taxSlider);

        populationLabel = new JLabel("Population: error", JLabel.CENTER);
        moneyLabel = new JLabel("Money: error", JLabel.CENTER);

        add(populationLabel);
        add(moneyLabel);
    }

    @Override
    public void paint(Graphics g) {
        Game game = getEditMode().getGame();
        PopulationStats populationStats = game.getPopulationStats();

        taxLabel.setText("Tax: " + (int)(game.getTax() * 100) + "% ");
        populationLabel.setText("Population: " + populationStats.population() + " / " + populationStats.capacity() + " ");
        moneyLabel.setText("Money: " + String.format("%.1f", game.getMoney()) + "k $");

        super.paint(g);
    }

    @NotNull
    public Infrastructure getChoosenInfrastructure() {
        return choosenInfrastructure;
    }
}
