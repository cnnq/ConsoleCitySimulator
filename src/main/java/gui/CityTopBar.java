package gui;

import modes.EditMode;
import org.jetbrains.annotations.NotNull;
import other.Building;

import javax.swing.*;
import java.awt.*;

public class CityTopBar extends TopBar {

    private final JButton roadButton;
    private final JButton housingAreaButton;
    private final JButton solarPanelButton;
    private final JButton waterPumpButton;

    private JLabel moneyLabel;

    private Building choosenBuilding;


    public CityTopBar(@NotNull EditMode editMode) {
        super(editMode);

        roadButton = new JButton("Road");
        housingAreaButton = new JButton("House");
        solarPanelButton = new JButton("Solar panel");
        waterPumpButton = new JButton("Water pump");

        choosenBuilding = Building.ROAD;

        roadButton.addActionListener(e -> {
            choosenBuilding = Building.ROAD;
        });

        housingAreaButton.addActionListener(e -> {
            choosenBuilding = Building.HOUSING_AREA;
        });

        solarPanelButton.addActionListener(e -> {
            choosenBuilding = Building.SOLAR_PANELS;
        });

        waterPumpButton.addActionListener(e -> {
            choosenBuilding = Building.WATER_PUMP;
        });

        add(roadButton);
        add(housingAreaButton);
        add(solarPanelButton);
        add(waterPumpButton);

        moneyLabel = new JLabel("Money: error", JLabel.CENTER);

        add(moneyLabel);
    }

    @Override
    public void paint(Graphics g) {
        moneyLabel.setText("Money: " + Double.toString(Math.round(getEditMode().getGame().getMoney())) + "k $");
        super.paint(g);
    }

    @NotNull
    public Building getChoosenBuilding() {
        return choosenBuilding;
    }
}
