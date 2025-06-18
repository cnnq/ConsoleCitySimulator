package gui;

import org.jetbrains.annotations.NotNull;

import java.awt.*;

/**
 * Top bar which show city economy
 */
class EconomyTopBar extends TopBarInstance {

    public EconomyTopBar(@NotNull TopBar topBar) {
        super(topBar);

        var taxSliders = new TopBarTaxSliders(topBar);
        var infoLabels = new TopBarInfoLabels(topBar);

        add(taxSliders, BorderLayout.CENTER);
        add(infoLabels, BorderLayout.EAST);
    }
}
