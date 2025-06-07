package systems;

import data.ElectricityStats;
import data.WaterStats;
import infrastructure.*;
import layers.CityLayer;
import layers.PipesLayer;
import layers.WiresLayer;
import org.jetbrains.annotations.NotNull;
import other.Game;

import java.util.Random;

public class UrbanizationSystem {

    private static Random random = new Random();

    private Game game;


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
                if (cityMap.neighbours(x, y, Infrastructure.ROAD) &&
                        pipesMap.neighbours(x, y, true) &&
                        wiresMap.neighbours(x, y, true)) {

                    Infrastructure area = cityMap.get(x, y);
                    Building building;

                    // Choose appropriate building
                    if (area == Infrastructure.HOUSING_AREA) {
                        building = switch (random.nextInt(2)) {
                            case 0 -> House.HOUSE_1;
                            default -> House.HOUSE_2;
                        };

                    } else if (area == Infrastructure.COMMERCIAL_AREA) {
                        building = switch (random.nextInt(2)) {
                            case 0 -> CommercialBuilding.SHOP_1;
                            default -> CommercialBuilding.SHOP_2;
                        };

                    } else if (area == Infrastructure.INDUSTRIAL_AREA) {
                        building = IndustrialBuilding.FACTORY_1;

                    } else {
                        continue;
                    }

                    // Build only if building won't use too much water or electricity
                    if (waterStats.usage() + building.getWaterUsage() <= waterStats.production() &&
                            electricityStats.usage() + building.getElectricityUsage() <= electricityStats.production()) {

                        cityMap.set(x, y, building);

                        // Update stats
                        waterStats = new WaterStats(waterStats.usage() + building.getWaterUsage(), waterStats.production());
                        electricityStats = new ElectricityStats(electricityStats.usage() + building.getElectricityUsage(), electricityStats.production());

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
                if(!(current instanceof Building building)) continue;

                Infrastructure area;
                switch (building) {
                    case House house -> area = Infrastructure.HOUSING_AREA;
                    case CommercialBuilding commercialBuilding -> area = Infrastructure.COMMERCIAL_AREA;
                    case IndustrialBuilding industrialBuilding -> area = Infrastructure.INDUSTRIAL_AREA;
                    default -> {
                        continue;
                    }
                }

                cityMap.set(x, y, area);

                // Update stats
                waterStats = new WaterStats(waterStats.usage() - building.getWaterUsage(), waterStats.production());
                electricityStats = new ElectricityStats(electricityStats.usage() - building.getElectricityUsage(), electricityStats.production());

                // Resource usage stabilized - no need to iterate
                if (waterStats.usage() <= waterStats.production() && electricityStats.usage() <= electricityStats.production()) {
                    return;
                }
            }
        }
    }
}
