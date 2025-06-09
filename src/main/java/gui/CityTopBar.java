package gui;

import data.PopulationStats;
import graphics.Sprite;
import infrastructure.*;
import main.Game;
import modes.EditMode;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class CityTopBar extends TopBar {

    public static int TAX_STEPS = 20;

    private final JButton roadButton;
    private final JButton housingAreaButton;
    private final JButton commercialAreaButton;
    private final JButton industrialAreaButton;
    private final JButton solarPanelButton;
    private final JButton waterPumpButton;

    private JLabel taxLabel;
    private JSlider taxSlider;

    private JLabel populationLabel;
    private JLabel moneyLabel;

    private Infrastructure choosenInfrastructure;


    public CityTopBar(@NotNull EditMode editMode) {
        super(editMode);

        choosenInfrastructure = InfrastructureManager.INSTANCE.getInfrastructure("ROAD");

        // === Buttons ===
        roadButton = new JButton(Sprite.ROAD_ICON);
        housingAreaButton = new JButton(Sprite.HOUSING_AREA_ICON);
        commercialAreaButton = new JButton(Sprite.COMMERCIAL_AREA_ICON);
        industrialAreaButton = new JButton(Sprite.INDUSTRIAL_AREA_ICON);
        solarPanelButton = new JButton(Sprite.SOLAR_PANELS_ICON);
        waterPumpButton = new JButton(Sprite.WATER_PUMP_ICON);

        // Set preferred size
        roadButton.setPreferredSize(new Dimension(Sprite.DEFAULT_SPRITE_SIZE, Sprite.DEFAULT_SPRITE_SIZE));
        housingAreaButton.setPreferredSize(new Dimension(Sprite.DEFAULT_SPRITE_SIZE, Sprite.DEFAULT_SPRITE_SIZE));
        commercialAreaButton.setPreferredSize(new Dimension(Sprite.DEFAULT_SPRITE_SIZE, Sprite.DEFAULT_SPRITE_SIZE));
        industrialAreaButton.setPreferredSize(new Dimension(Sprite.DEFAULT_SPRITE_SIZE, Sprite.DEFAULT_SPRITE_SIZE));
        solarPanelButton.setPreferredSize(new Dimension(Sprite.DEFAULT_SPRITE_SIZE, Sprite.DEFAULT_SPRITE_SIZE));
        waterPumpButton.setPreferredSize(new Dimension(Sprite.DEFAULT_SPRITE_SIZE, Sprite.DEFAULT_SPRITE_SIZE));

        // Get elements
        var road = InfrastructureManager.INSTANCE.getInfrastructure("ROAD");
        var housingArea = InfrastructureManager.INSTANCE.getInfrastructure("HOUSING_AREA");
        var commercialArea = InfrastructureManager.INSTANCE.getInfrastructure("COMMERCIAL_AREA");
        var industrialArea = InfrastructureManager.INSTANCE.getInfrastructure("INDUSTRIAL_AREA");
        var solarPanels = InfrastructureManager.INSTANCE.getInfrastructure("SOLAR_PANELS");
        var waterPump = InfrastructureManager.INSTANCE.getInfrastructure("WATER_PUMP");

        // Add action listeners
        roadButton.addActionListener(e -> { choosenInfrastructure = road; });
        housingAreaButton.addActionListener(e -> { choosenInfrastructure = housingArea; });
        commercialAreaButton.addActionListener(e -> { choosenInfrastructure = commercialArea; });
        industrialAreaButton.addActionListener(e -> { choosenInfrastructure = industrialArea; });
        solarPanelButton.addActionListener(e -> { choosenInfrastructure = solarPanels; });
        waterPumpButton.addActionListener(e -> { choosenInfrastructure = waterPump; });

        // Add components
        add(roadButton);
        add(housingAreaButton);
        add(commercialAreaButton);
        add(industrialAreaButton);
        add(solarPanelButton);
        add(waterPumpButton);


        // === Tax slider ===
        taxLabel = new JLabel("Tax: error", JLabel.CENTER);
        taxSlider = new JSlider(JSlider.HORIZONTAL, 0, TAX_STEPS, TAX_STEPS / 10);

        taxSlider.addChangeListener(e -> {
            getEditMode().getGame().setTax((double)taxSlider.getValue() / TAX_STEPS);
        });

        add(taxLabel);
        add(taxSlider);

        // === Labels ===
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
