package gui;

import graphics.Sprite;
import infrastructure.InfrastructureManager;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * Manages buttons used only by {@link CityTopBar}
 */
class CityTopBarBuildButtons extends JPanel {

    public CityTopBarBuildButtons(@NotNull CityTopBar cityTopBar) {

        setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton roadButton = new JButton(Sprite.ROAD_ICON);
        JButton housingAreaButton = new JButton(Sprite.HOUSING_AREA_ICON);
        JButton commercialAreaButton = new JButton(Sprite.COMMERCIAL_AREA_ICON);
        JButton industrialAreaButton = new JButton(Sprite.INDUSTRIAL_AREA_ICON);
        JButton solarPanelButton = new JButton(Sprite.SOLAR_PANELS_ICON);
        JButton waterPumpButton = new JButton(Sprite.WATER_PUMP_ICON);

        // Set preferred size
        Dimension spriteDimension = new Dimension(Sprite.DEFAULT_SPRITE_SIZE, Sprite.DEFAULT_SPRITE_SIZE);

        roadButton.setPreferredSize(spriteDimension);
        housingAreaButton.setPreferredSize(spriteDimension);
        commercialAreaButton.setPreferredSize(spriteDimension);
        industrialAreaButton.setPreferredSize(spriteDimension);
        solarPanelButton.setPreferredSize(spriteDimension);
        waterPumpButton.setPreferredSize(spriteDimension);

        roadButton.setMaximumSize(spriteDimension);
        housingAreaButton.setMaximumSize(spriteDimension);
        commercialAreaButton.setMaximumSize(spriteDimension);
        industrialAreaButton.setMaximumSize(spriteDimension);
        solarPanelButton.setMaximumSize(spriteDimension);
        waterPumpButton.setMaximumSize(spriteDimension);

        // Add action listeners
        var road = InfrastructureManager.INSTANCE.getInfrastructure("ROAD");
        var housingArea = InfrastructureManager.INSTANCE.getInfrastructure("HOUSING_AREA");
        var commercialArea = InfrastructureManager.INSTANCE.getInfrastructure("COMMERCIAL_AREA");
        var industrialArea = InfrastructureManager.INSTANCE.getInfrastructure("INDUSTRIAL_AREA");
        var solarPanels = InfrastructureManager.INSTANCE.getInfrastructure("SOLAR_PANELS");
        var waterPump = InfrastructureManager.INSTANCE.getInfrastructure("WATER_PUMP");

        roadButton.addActionListener(e -> { cityTopBar.setChoosenInfrastructure(road); });
        housingAreaButton.addActionListener(e -> { cityTopBar.setChoosenInfrastructure(housingArea); });
        commercialAreaButton.addActionListener(e -> { cityTopBar.setChoosenInfrastructure(commercialArea); });
        industrialAreaButton.addActionListener(e -> { cityTopBar.setChoosenInfrastructure(industrialArea); });
        solarPanelButton.addActionListener(e -> { cityTopBar.setChoosenInfrastructure(solarPanels); });
        waterPumpButton.addActionListener(e -> { cityTopBar.setChoosenInfrastructure(waterPump); });

        // Add components
        add(roadButton);
        add(housingAreaButton);
        add(commercialAreaButton);
        add(industrialAreaButton);
        add(solarPanelButton);
        add(waterPumpButton);
    }
}
