package gui;

import org.jetbrains.annotations.NotNull;
import systems.FinancialSystem;

import javax.swing.*;
import java.awt.*;

/**
 * Manages tax sliders used only by {@link CityTopBar}
 */
class CityTopBarTaxSliders extends JPanel {

    public static int TAX_STEPS = 20;

    public CityTopBarTaxSliders(@NotNull CityTopBar cityTopBar) {
        setLayout(new GridLayout(2, 3));

        // Labels
        JLabel perCapitaTaxLabel = new JLabel("Per capita tax: " + 100 / TAX_STEPS + "% ", JLabel.CENTER);
        JLabel commercialTaxLabel = new JLabel("Commercial tax: " + 100 / TAX_STEPS + "% ", JLabel.CENTER);
        JLabel industrialTaxLabel = new JLabel("Industrial tax: " + 100 / TAX_STEPS + "% ", JLabel.CENTER);

        add(perCapitaTaxLabel);
        add(commercialTaxLabel);
        add(industrialTaxLabel);

        // Sliders
        JSlider perCapitaTaxSlider = new JSlider(0, TAX_STEPS, TAX_STEPS / 10);
        JSlider commercialTaxSlider = new JSlider(0, TAX_STEPS, TAX_STEPS / 10);
        JSlider industrialTaxSlider = new JSlider(0, TAX_STEPS, TAX_STEPS / 10);

        FinancialSystem financialSystem = cityTopBar.getTopBar().getEditMode().getGame().getFinancialSystem();

        perCapitaTaxSlider.addChangeListener(e -> {
            double newTax = (double)perCapitaTaxSlider.getValue() / TAX_STEPS;
            perCapitaTaxLabel.setText("Per capita tax: " + (int)(newTax * 100) + "% ");
            financialSystem.setPerCapitaTax(newTax);
        });

        commercialTaxSlider.addChangeListener(e -> {
            double newTax = (double)commercialTaxSlider.getValue() / TAX_STEPS;
            commercialTaxLabel.setText("Commercial tax: " + (int)(newTax * 100) + "% ");
            financialSystem.setCommercialTax(newTax);
        });

        industrialTaxSlider.addChangeListener(e -> {
            double newTax = (double)industrialTaxSlider.getValue() / TAX_STEPS;
            industrialTaxLabel.setText("Industrial tax: " + (int)(newTax * 100) + "% ");
            financialSystem.setIndustrialTax(newTax);
        });

        add(perCapitaTaxSlider);
        add(commercialTaxSlider);
        add(industrialTaxSlider);
    }
}
