package systems;

import data.ElectricityStats;
import data.WaterStats;
import main.Game;
import org.jetbrains.annotations.NotNull;

/**
 * System which manages prices of goods
 */
public class EconomySystem implements GameSystem {

    public static double DEFAULT_ELECTRICITY_PRICE = 0.16;
    public static double DEFAULT_WATER_PRICE = 3.0;
    public static double DEFAULT_CONCRETE_PRICE = 165.0;
    public static double DEFAULT_FOOD_PRICE = 12.0;

    private static double MIN_ELECTRICITY_PRICE = 0.1;
    private static double MIN_WATER_PRICE = 1.5;

    private static double HALF_WAY_CHANGE_TIME = 2;

    private double electricityPrice;
    private double waterPrice;
    private double concretePrice;
    private double foodPrice;

    public Game game;

    public EconomySystem(@NotNull Game game) {
        this.game = game;

        electricityPrice = DEFAULT_ELECTRICITY_PRICE;
        waterPrice = DEFAULT_WATER_PRICE;
        concretePrice = DEFAULT_CONCRETE_PRICE;
        foodPrice = DEFAULT_FOOD_PRICE;
    }

    @Override
    public void update(float deltaTime) {
        UrbanizationSystem urbanizationSystem = game.getUrbanizationSystem();

        // === Speed of change ===
        // Price changes are smoothed by function:
        //   f(x) = f_old * t + f_target * (1 - t)
        // Where:
        //   f(x) - new price
        //   f_old - price from previous iteration (old f(x))
        //   f_target - target price
        //   t - change rate
        //
        // Change speed:
        //   t(x) = 0.5 ^ (h * x)
        // Where:
        //   h - time in seconds at which change will be halfway through (HALF_WAY_CHANGE_TIME)
        //   x - deltaTime in seconds
        //
        // This should give nice fast change rate when current and target prices are very different and
        // slow when these values are nearly the same.

        double delta = Math.pow(0.5, HALF_WAY_CHANGE_TIME * deltaTime);

        // === Calculate electricity price ===
        // Variable electricityTargetPrice is calculated using function that:
        //   electricityTargetPrice = f(electricitySaturation)
        //   f(0) = MIN_ELECTRICITY_PRICE
        //   f(p) = DEFAULT_ELECTRICITY_PRICE
        //   lim(x -> 1) f(x) = + infinity

        // Math.abs to prevent negative infinity

        final double p = 0.95;
        final double electricityCurveCoefficient = (p - 1) * (DEFAULT_ELECTRICITY_PRICE - MIN_ELECTRICITY_PRICE) / p;

        ElectricityStats electricityStats = urbanizationSystem.getElectricityStats();
        double electricitySaturation = electricityStats.production() != 0 ? electricityStats.usage() / electricityStats.production() : 0;
        double electricityTargetPrice = Math.abs(electricityCurveCoefficient / (electricitySaturation * 0.99 - 1) + electricityCurveCoefficient + MIN_ELECTRICITY_PRICE);

        electricityPrice = electricityPrice * delta + electricityTargetPrice * (1 - delta);

        // === Calculate water price ===

        final double waterCurveCoefficient = (p - 1) * (DEFAULT_WATER_PRICE - MIN_WATER_PRICE) / p;

        WaterStats waterStats = urbanizationSystem.getWaterStats();
        double waterSaturation = waterStats.production() != 0 ? waterStats.usage() / waterStats.production() : 0;
        double waterTargetPrice = Math.abs(waterCurveCoefficient / (waterSaturation * 0.99 - 1) + waterCurveCoefficient + MIN_WATER_PRICE);

        waterPrice = waterPrice * delta + waterTargetPrice * (1 - delta);

        // === Calculate concrete price ===

        concretePrice = concretePrice * delta + DEFAULT_CONCRETE_PRICE * (1 - delta);
    }

    public double getElectricityPrice() {
        return electricityPrice;
    }

    public double getWaterPrice() {
        return waterPrice;
    }

    public double getConcretePrice() {
        return concretePrice;
    }

    public void raiseConcretePrice(double amount) {
        if (amount < 0) throw new IllegalArgumentException("amount cannot be negative");
        concretePrice += amount;
    }

    public double getFoodPrice() {
        return foodPrice;
    }
}
