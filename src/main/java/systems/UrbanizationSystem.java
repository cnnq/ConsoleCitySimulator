package systems;

import data.ElectricityStats;
import data.ConductionState;
import data.WaterStats;
import infrastructure.*;
import layers.CityLayer;
import layers.PipesLayer;
import layers.WiresLayer;
import org.jetbrains.annotations.NotNull;
import main.Game;

import java.util.Random;

/**
 * System that simulates citizens building and destroying their owned infrastructure
 * @see UnmanagedBuilding
 */
public class UrbanizationSystem implements GameSystem {

    private static final ManagedInfrastructure road = InfrastructureManager.INSTANCE.getInfrastructure("ROAD", ManagedInfrastructure.class);
    private static final UnmanagedInfrastructure housingArea = InfrastructureManager.INSTANCE.getInfrastructure("HOUSING_AREA", UnmanagedInfrastructure.class);
    private static final UnmanagedInfrastructure commercialArea = InfrastructureManager.INSTANCE.getInfrastructure("COMMERCIAL_AREA", UnmanagedInfrastructure.class);
    private static final UnmanagedInfrastructure industrialArea = InfrastructureManager.INSTANCE.getInfrastructure("INDUSTRIAL_AREA", UnmanagedInfrastructure.class);

    private static final Random random = new Random();

    private WaterStats waterStats;
    private ElectricityStats electricityStats;

    private final Game game;
    private final CityLayer cityMap;

    public UrbanizationSystem(@NotNull Game game) {
        this.game = game;
        this.cityMap = game.getCityMap();
    }

    /**
     * Update city urbanization
     * @param deltaTime time in seconds
     */
    @Override
    public void update(float deltaTime) {
        WaterStats waterStats = getWaterStats();
        ElectricityStats electricityStats = getElectricityStats();

        // TODO destroy houses if have no access to media

        // Build only if there is enough resources
        if (waterStats.usage() < waterStats.production() && electricityStats.usage() < electricityStats.production()) {
            build(waterStats, electricityStats);

        } else if (waterStats.usage() > waterStats.production() || electricityStats.usage() > electricityStats.production()) {
            destroy(waterStats, electricityStats);
        }

        // Recalculate stats
        calculateWaterStats();
        calculateElectricityStats();
    }

    /**
     * Builds buildings as long as resource usage max-out
     * @param waterStats
     * @param electricityStats
     */
    private void build(@NotNull WaterStats waterStats, @NotNull ElectricityStats electricityStats) {
        PipesLayer pipesMap = game.getPipesMap();
        WiresLayer wiresMap = game.getWiresMap();

        for (int y = 0; y < cityMap.getHeight(); y++) {
            for (int x = 0; x < cityMap.getWidth(); x++) {

                // Build only if close to road and with access to water pipes and electric wires
                if (cityMap.neighbours(x, y, road) &&
                        pipesMap.neighbours(x, y, ConductionState.Filled) &&
                        wiresMap.neighbours(x, y, ConductionState.Filled)) {

                    Infrastructure area = cityMap.get(x, y);
                    UnmanagedBuilding unmanagedBuilding;

                    // Choose appropriate building
                    if (area == housingArea) {
                        unmanagedBuilding = switch (random.nextInt(4)) {
                            case 0 -> InfrastructureManager.INSTANCE.getInfrastructure("HOUSE_1", UnmanagedBuilding.class);
                            case 1 -> InfrastructureManager.INSTANCE.getInfrastructure("HOUSE_2", UnmanagedBuilding.class);
                            case 2 -> InfrastructureManager.INSTANCE.getInfrastructure("HOUSE_3", UnmanagedBuilding.class);
                            default -> InfrastructureManager.INSTANCE.getInfrastructure("HOUSE_4", UnmanagedBuilding.class);
                        };

                    } else if (area == commercialArea) {
                        unmanagedBuilding = switch (random.nextInt(3)) {
                            case 0 -> InfrastructureManager.INSTANCE.getInfrastructure("SHOP_1", UnmanagedBuilding.class);
                            case 1 -> InfrastructureManager.INSTANCE.getInfrastructure("SHOP_2", UnmanagedBuilding.class);
                            default -> InfrastructureManager.INSTANCE.getInfrastructure("OFFICE_1", UnmanagedBuilding.class);
                        };

                    } else if (area == industrialArea) {
                        unmanagedBuilding = switch (random.nextInt(2)) {
                            case 0 -> InfrastructureManager.INSTANCE.getInfrastructure("FACTORY_1", UnmanagedBuilding.class);
                            default -> InfrastructureManager.INSTANCE.getInfrastructure("FACTORY_2", UnmanagedBuilding.class);
                        };

                    } else {
                        continue;
                    }

                    // Build only if building won't use too much water or electricity
                    if (waterStats.usage() + unmanagedBuilding.getWaterUsage() <= waterStats.production() &&
                            electricityStats.usage() + unmanagedBuilding.getElectricityUsage() <= electricityStats.production()) {

                        cityMap.set(x, y, unmanagedBuilding);
                        game.getEconomySystem().raiseConcretePrice(1);

                        // Update stats
                        waterStats = new WaterStats(waterStats.usage() + unmanagedBuilding.getWaterUsage(), waterStats.production());
                        electricityStats = new ElectricityStats(electricityStats.usage() + unmanagedBuilding.getElectricityUsage(), electricityStats.production());

                        if (waterStats.usage() >= waterStats.production() || electricityStats.usage() >= electricityStats.production()) {
                            // Resource usage maxed out - no need to iterate
                            return;
                        }
                    }
                }
            }
        }
    }

    /**
     * Destroys buildings as long as resource usage stabilize
     * @param waterStats
     * @param electricityStats
     */
    private void destroy(@NotNull WaterStats waterStats, @NotNull ElectricityStats electricityStats) {
        for (int y = 0; y < cityMap.getHeight(); y++) {
            for (int x = 0; x < cityMap.getWidth(); x++) {

                Infrastructure current = cityMap.get(x, y);
                if(!(current instanceof UnmanagedBuilding unmanagedBuilding)) continue;

                Infrastructure area;
                switch (unmanagedBuilding) {
                    case House ignored -> area = housingArea;
                    case CommercialBuilding ignored -> area = commercialArea;
                    case IndustrialBuilding ignored -> area = industrialArea;
                    default -> {
                        continue;
                    }
                }

                cityMap.set(x, y, area);

                // Update stats
                waterStats = new WaterStats(waterStats.usage() - unmanagedBuilding.getWaterUsage(), waterStats.production());
                electricityStats = new ElectricityStats(electricityStats.usage() - unmanagedBuilding.getElectricityUsage(), electricityStats.production());

                // Resource usage stabilized - no need to iterate
                if (waterStats.usage() <= waterStats.production() && electricityStats.usage() <= electricityStats.production()) {
                    return;
                }
            }
        }
    }

    private void calculateWaterStats() {
        double usage = 0;
        double production = 0;

        for (int x = 0; x < cityMap.getWidth(); x++) {
            for (int y = 0; y < cityMap.getHeight(); y++) {
                Infrastructure infrastructure = cityMap.get(x, y);
                if(!(infrastructure instanceof Building building)) continue;

                double waterUsage = building.getWaterUsage();

                if (waterUsage > 0) usage += waterUsage;
                else production -= waterUsage;
            }
        }

        waterStats = new WaterStats(usage, production);
    }

    private void calculateElectricityStats() {
        double usage = 0;
        double production = 0;

        for (int x = 0; x < cityMap.getWidth(); x++) {
            for (int y = 0; y < cityMap.getHeight(); y++) {
                Infrastructure infrastructure = cityMap.get(x, y);
                if (!(infrastructure instanceof Building building)) continue;

                double electricityUsage = building.getElectricityUsage();

                if (electricityUsage > 0) usage += electricityUsage;
                else production -= electricityUsage;
            }
        }

        electricityStats = new ElectricityStats(usage, production);
    }

    @NotNull
    public WaterStats getWaterStats() {
        if (waterStats == null) calculateWaterStats();
        return waterStats;
    }

    @NotNull
    public ElectricityStats getElectricityStats() {
        if (electricityStats == null) calculateElectricityStats();
        return electricityStats;
    }
}
