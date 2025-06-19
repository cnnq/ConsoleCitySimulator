package systems;

import data.EmploymentStats;
import data.PopulationStats;
import infrastructure.*;
import layers.CityLayer;
import main.Game;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class PopulationSystem implements GameSystem {

    private static final int BUILDINS_PER_FIRE_STATION = 100;
    private static final int KIDS_PER_SCHOOL = 200;
    private static final int POPULATION_PER_HOSPITAL = 200;

    private static final double JOB_SEEKERS_RATIO = 0.4;
    private static final double KIDS_RATIO = 0.25;

    private static final double TOO_BIG_TAX_FACTOR = 0.75;

    private static final Random random = new Random();

    private int population;
    private int residences;
    private int jobs;

    private double safetyLevel;
    private double educationLevel;
    private double healthLevel;

    private double happiness;

    private final Game game;


    public PopulationSystem(@NotNull Game game) {
        this.game = game;
    }

    @Override
    public void update(float deltaTime) {
        CityLayer cityMap = game.getCityMap();

        // Recalculate residences and jobs
        residences = 0;
        jobs = 0;

        for (int x = 0; x < cityMap.getWidth(); x++) {
            for (int y = 0; y < cityMap.getHeight(); y++) {
                Infrastructure infrastructure = cityMap.get(x, y);
                if (infrastructure instanceof House house) {
                    residences += house.getCapacity();

                } else if (infrastructure instanceof Building building) {
                    jobs += building.getCapacity();
                }
            }
        }

        // Calculate levels of various stuff
        int fireStations = cityMap.calculateNumberOf(InfrastructureManager.INSTANCE.getInfrastructure("FIRE_STATION"));
        int schools = cityMap.calculateNumberOf(InfrastructureManager.INSTANCE.getInfrastructure("SCHOOL"));
        int hospitals = cityMap.calculateNumberOf(InfrastructureManager.INSTANCE.getInfrastructure("HOSPITAL"));
        int unmanagedBuildings = cityMap.calculateNumberOf(UnmanagedBuilding.class);

        if (unmanagedBuildings > 0) {
            safetyLevel = (double)fireStations * BUILDINS_PER_FIRE_STATION / unmanagedBuildings;

            if (safetyLevel > 1) safetyLevel = 1;

        } else {
            safetyLevel = 1;
        }

        if (population > 0) {
            educationLevel = schools * KIDS_PER_SCHOOL / (population * KIDS_RATIO);
            healthLevel = (double)hospitals * POPULATION_PER_HOSPITAL / population;

            if (educationLevel > 1) educationLevel = 1;
            if (healthLevel > 1) healthLevel = 1;

        } else {
            educationLevel = 1;
            healthLevel = 1;
        }

        // Calculate happiness
        happiness = (0.5 * safetyLevel + 0.5) * (0.5 * educationLevel + 0.5) * (0.5 * healthLevel + 0.5);
        happiness *= 0.5 * (1 - Math.pow(game.getFinancialSystem().getPerCapitaTax() / TOO_BIG_TAX_FACTOR, 4)) + 0.5;
        happiness *= 0.5 * (1 - Math.pow(game.getFinancialSystem().getCommercialTax() / TOO_BIG_TAX_FACTOR, 4)) + 0.5;
        happiness *= 0.5 * (1 - Math.pow(game.getFinancialSystem().getIndustrialTax() / TOO_BIG_TAX_FACTOR, 4)) + 0.5;

        // Migrations
        double migrationFactor = calculateMigrationFactor();

        // Reduce flickering
        if (Math.abs(migrationFactor) < 0.01) return;

        // Round by probability
        if (random.nextDouble() > Math.abs(migrationFactor - Math.floor(migrationFactor))) {
            migrationFactor++;
        }

        population += (int)Math.floor(migrationFactor);

        if (population < 0) population = 0;
    }

    private double calculateMigrationFactor() {
        double saturation = residences != 0 ? (double)population / residences : 1;

        // Calculate from per capita tax and housing market saturation
        return 1 / ((1 - happiness) * (1 - happiness) + saturation * saturation + 1) - 0.5;
    }

    public PopulationStats getPopulationStats() {
        return new PopulationStats(population, residences);
    }

    public EmploymentStats getEmploymentStats() {
        return new EmploymentStats((int)Math.round(population * JOB_SEEKERS_RATIO), jobs);
    }

    public double getHappiness() {
        return happiness;
    }
}
