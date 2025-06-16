package gui;

import infrastructure.*;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

/**
 * Top bar which enables editing {@link layers.CityLayer}
 */
public class CityTopBar extends TopBarInstance {

    private Infrastructure choosenInfrastructure;


    public CityTopBar(@NotNull TopBar topBar) {
        super(topBar);

        var buildButtons = new CityTopBarBuildButtons(this);
        var taxSliders = new CityTopBarTaxSliders(this);
        var infoLabels = new CityTopBarInfoLabels(this);

        add(buildButtons, BorderLayout.WEST);
        add(taxSliders, BorderLayout.CENTER);
        add(infoLabels, BorderLayout.EAST);

        choosenInfrastructure = InfrastructureManager.INSTANCE.getInfrastructure("ROAD");
    }

    @NotNull
    public Infrastructure getChoosenInfrastructure() {
        return choosenInfrastructure;
    }

    public void setChoosenInfrastructure(@NotNull Infrastructure choosenInfrastructure) {
        this.choosenInfrastructure = choosenInfrastructure;
    }
}
