package systems;

import data.ElectricityStats;
import data.WaterStats;
import infrastructure.*;
import layers.CityLayer;
import layers.PipesLayer;
import layers.WiresLayer;
import org.jetbrains.annotations.NotNull;
import main.Game;

import java.util.Random;

public class UrbanizationSystem {

    private static final ManagedInfrastructure road = InfrastructureManager.INSTANCE.getInfrastructure("ROAD", ManagedInfrastructure.class);
    private static final UnmanagedInfrastructure housingArea = InfrastructureManager.INSTANCE.getInfrastructure("HOUSING_AREA", UnmanagedInfrastructure.class);
    private static final UnmanagedInfrastructure commercialArea = InfrastructureManager.INSTANCE.getInfrastructure("COMMERCIAL_AREA", UnmanagedInfrastructure.class);
    private static final UnmanagedInfrastructure industrialArea = InfrastructureManager.INSTANCE.getInfrastructure("INDUSTRIAL_AREA", UnmanagedInfrastructure.class);

    private static final Random random = new Random();

    private final Game game;


    public UrbanizationSystem(@NotNull Game game) {
        this.game = game;
    }

    /**
     * Update city population
     * @param deltaTime time in seconds
     */
    public void update(float deltaTime) {

        WaterStats waterStats = game.getWaterStats();
        ElectricityStats electricityStats = game.getElectricityStats();

        // TODO destroy houses if have no access

        // Build only if there is enough resources
        if (waterStats.usage() < waterStats.production() && electricityStats.usage() < electricityStats.production()) {
            build(waterStats, electricityStats);

        } else if (waterStats.usage() > waterStats.production() || electricityStats.usage() > electricityStats.production()) {
            destroy(waterStats, electricityStats);
        }
    }

    /**
     * Builds buildings as long as resource usage max-out
     * @param waterStats
     * @param electricityStats
     */
    private void build(@NotNull WaterStats waterStats, @NotNull ElectricityStats electricityStats) {
        CityLayer cityMap = game.getCityMap();
        PipesLayer pipesMap = game.getPipesMap();
        WiresLayer wiresMap = game.getWiresMap();

        for (int y = 0; y < cityMap.getHeight(); y++) {
            for (int x = 0; x < cityMap.getWidth(); x++) {

                // Build only if close to road and with access to water pipes and electric wires
                if (cityMap.neighbours(x, y, road) &&
                        pipesMap.neighbours(x, y, true) &&
                        wiresMap.neighbours(x, y, true)) {

                    Infrastructure area = cityMap.get(x, y);
                    UnmanagedBuilding unmanagedBuilding;

                    // Choose appropriate building
                    if (area == housingArea) {
                        unmanagedBuilding = switch (random.nextInt(2)) {
                            case 0 -> InfrastructureManager.INSTANCE.getInfrastructure("HOUSE_1", UnmanagedBuilding.class);
                            default -> InfrastructureManager.INSTANCE.getInfrastructure("HOUSE_2", UnmanagedBuilding.class);
                        };

                    } else if (area == commercialArea) {
                        unmanagedBuilding = switch (random.nextInt(2)) {
                            case 0 -> InfrastructureManager.INSTANCE.getInfrastructure("SHOP_1", UnmanagedBuilding.class);
                            default -> InfrastructureManager.INSTANCE.getInfrastructure("SHOP_2", UnmanagedBuilding.class);
                        };

                    } else if (area == industrialArea) {
                        unmanagedBuilding = InfrastructureManager.INSTANCE.getInfrastructure("FACTORY_1", UnmanagedBuilding.class);

                    } else {
                        continue;
                    }

                    // Build only if building won't use too much water or electricity
                    if (waterStats.usage() + unmanagedBuilding.getWaterUsage() <= waterStats.production() &&
                            electricityStats.usage() + unmanagedBuilding.getElectricityUsage() <= electricityStats.production()) {

                        cityMap.set(x, y, unmanagedBuilding);

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
        CityLayer cityMap = game.getCityMap();

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
}
