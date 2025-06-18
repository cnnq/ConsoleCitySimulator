package gui;

import org.jetbrains.annotations.NotNull;
import systems.EconomySystem;

import javax.swing.*;
import java.awt.*;

/**
 * Manages price display, used only by {@link TopBar}
 */
class TopBarPriceLabels extends JPanel {

    private final JLabel electricityPrice;
    private final JLabel waterPrice;
    private final JLabel concretePrice;
    private final JLabel foodPrice;

    private final EconomySystem economySystem;


    public TopBarPriceLabels(@NotNull TopBar topBar) {
        economySystem = topBar.getEditMode().getGame().getEconomySystem();

        setLayout(new GridLayout(2, 5));

        JLabel electricityPriceStringLabel = new JLabel("Electricity:", JLabel.CENTER);
        JLabel waterPriceStringLabel = new JLabel("Water:", JLabel.CENTER);
        JLabel concretePriceStringLabel = new JLabel("Concrete:", JLabel.CENTER);
        JLabel foodPriceStringLabel = new JLabel("Food:", JLabel.CENTER);

        add(electricityPriceStringLabel);
        add(waterPriceStringLabel);
        add(concretePriceStringLabel);
        add(foodPriceStringLabel);

        electricityPrice = new JLabel("error", JLabel.CENTER);
        waterPrice = new JLabel("error", JLabel.CENTER);
        concretePrice = new JLabel("error", JLabel.CENTER);
        foodPrice = new JLabel("error", JLabel.CENTER);

        add(electricityPrice);
        add(waterPrice);
        add(concretePrice);
        add(foodPrice);
    }

    @Override
    public void paint(Graphics g) {
        electricityPrice.setText(String.format("%.2f", economySystem.getElectricityPrice()) + " $/kWh");
        waterPrice.setText(String.format("%.2f", economySystem.getWaterPrice()) + " $/m3");
        concretePrice.setText(String.format("%.2f", economySystem.getConcretePrice()) + " $/m3");
        foodPrice.setText(String.format("%.2f", economySystem.getFoodPrice()) + " $/day");

        super.paint(g);
    }
}
