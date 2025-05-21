package gui;

import modes.EditMode;
import org.jetbrains.annotations.NotNull;
import other.*;

import javax.swing.*;
import java.awt.*;

public class CityTopBar extends TopBar {

    private final JButton roadButton;
    private final JButton housingAreaButton;
    private final JButton solarPanelButton;
    private final JButton waterPumpButton;

    private JLabel moneyLabel;
    private JLabel populationLabel;

    private Infrastructure choosenInfrastructure;


    public CityTopBar(@NotNull EditMode editMode) {
        super(editMode);

        roadButton = new JButton("Road");
        housingAreaButton = new JButton("House");
        solarPanelButton = new JButton("Solar panel");
        waterPumpButton = new JButton("Water pump");

        choosenInfrastructure = Infrastructure.ROAD;

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

        populationLabel = new JLabel("Population: error", JLabel.CENTER);
        moneyLabel = new JLabel("Money: error", JLabel.CENTER);

        add(populationLabel);
        add(moneyLabel);
    }

    @Override
    public void paint(Graphics g) {
        Game game = getEditMode().getGame();
        PopulationStats populationStats = game.getPopulationStats();
        double money = Math.round(game.getMoney());

        populationLabel.setText("Population: " + populationStats.population() + " / " + populationStats.capacity() + " ");
        moneyLabel.setText("Money: " + money + "k $");

        super.paint(g);
    }

    @NotNull
    public Infrastructure getChoosenInfrastructure() {
        return choosenInfrastructure;
    }
}
