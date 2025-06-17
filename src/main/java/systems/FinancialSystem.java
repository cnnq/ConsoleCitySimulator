package systems;

import infrastructure.CommercialBuilding;
import infrastructure.IndustrialBuilding;
import infrastructure.Infrastructure;
import infrastructure.ManagedInfrastructure;
import layers.CityLayer;
import main.Game;
import org.jetbrains.annotations.NotNull;

/**
 * System which manages money, taxes and spendings
 */
public class FinancialSystem implements GameSystem {

    private static final double AVERAGE_MONTH = 365.25 / 12;

    public static final double DEFAULT_MONEY = 1000;
    public static final double DEFAULT_TAX = 0.1;
    public static final double MINIMAL_WAGE = 1;

    private double money;

    // Taxes
    private double perCapitaTax;
    private double commercialTax;
    private double industrialTax;

    private final Game game;


    public FinancialSystem(@NotNull Game game) {
        this.game = game;

        money = DEFAULT_MONEY;
        perCapitaTax = DEFAULT_TAX;
        commercialTax = DEFAULT_TAX;
        industrialTax = DEFAULT_TAX;
    }

    /**
     * Update money
     * @param deltaTime time in seconds
     */
    @Override
    public void update(float deltaTime) {
        CityLayer cityMap = game.getCityMap();

        double totalCommercialIncome = 0;
        double totalIndustrialIncome = 0;
        double totalMaintenanceCost = 0;

        for (int y = 0; y < cityMap.getHeight(); y++) {
            for (int x = 0; x < cityMap.getWidth(); x++) {

                Infrastructure infrastructure = cityMap.get(x, y);

                switch (infrastructure) {
                    case CommercialBuilding commercialBuilding ->
                            totalCommercialIncome += commercialBuilding.getIncome();
                    case IndustrialBuilding industrialBuilding ->
                            totalIndustrialIncome += industrialBuilding.getIncome();
                    case ManagedInfrastructure managedInfrastructure ->
                            totalMaintenanceCost += managedInfrastructure.getMaintenanceCost();
                    case null, default -> {}
                }
            }
        }

        int population = game.getMigrationSystem().getPopulationStats().population();

        // Gather taxes
        double taxes = 0;

        taxes += population * MINIMAL_WAGE * perCapitaTax;
        taxes += totalCommercialIncome * commercialTax;
        taxes += totalIndustrialIncome * industrialTax;

        taxes = taxes / AVERAGE_MONTH * deltaTime;

        addMoney(taxes);

        // Spendings
        spendMoney(totalMaintenanceCost);
    }

    public double getMoney() {
        return money;
    }

    /**
     * Adds non-negative amount of money
     */
    public void addMoney(double money) {
        if (money < 0) throw new IllegalArgumentException("money cannot be negative");
        this.money += money;
    }

    /**
     * Subtracts non-negative amount of money
     */
    public void spendMoney(double money) {
        if (money < 0) throw new IllegalArgumentException("money cannot be negative");
        this.money -= money;
    }


    public double getPerCapitaTax() {
        return perCapitaTax;
    }

    public void setPerCapitaTax(double tax) {
        if (tax < 0 || tax > 1) throw new IllegalArgumentException("tax must be between 0 and 1");
        perCapitaTax = tax;
    }

    public double getCommercialTax() {
        return commercialTax;
    }

    public void setCommercialTax(double tax) {
        if (tax < 0 || tax > 1) throw new IllegalArgumentException("tax must be between 0 and 1");
        commercialTax = tax;
    }

    public double getIndustrialTax() {
        return industrialTax;
    }

    public void setIndustrialTax(double tax) {
        if (tax < 0 || tax > 1) throw new IllegalArgumentException("tax must be between 0 and 1");
        industrialTax = tax;
    }
}
