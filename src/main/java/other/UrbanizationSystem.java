package other;

import data.ElectricityStats;
import data.WaterStats;
import infrastructure.Building;
import infrastructure.House;
import infrastructure.Infrastructure;
import layers.CityLayer;
import layers.PipesLayer;
import layers.WiresLayer;
import org.jetbrains.annotations.NotNull;

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

        CityLayer cityMap = game.getCityMap();
        PipesLayer pipesMap = game.getPipesMap();
        WiresLayer wiresMap = game.getWiresMap();

        WaterStats waterStats = game.getWaterStats();
        ElectricityStats electricityStats = game.getElectricityStats();

        // Build houses only if there is enough resources
        if (waterStats.usage() < waterStats.production() && electricityStats.usage() < electricityStats.production()) {

            for (int x = 0; x < cityMap.getWidth(); x++) {
                for (int y = 0; y < cityMap.getHeight(); y++) {

                    // Build house if close to road and with access to water pipes and electric wires
                    if (cityMap.get(x, y) == Infrastructure.HOUSING_AREA &&
                            cityMap.neighbours(x, y, Infrastructure.ROAD) &&
                            pipesMap.neighbours(x, y, true) &&
                            wiresMap.neighbours(x, y, true)) {

                        // Choose random house
                        House house = switch (random.nextInt(2)) {
                            case 0 -> House.HOUSE_1;
                            default -> House.HOUSE_2;
                        };

                        // Build only if enough water and electricity
                        if (waterStats.usage() + house.getWaterUsage() <= waterStats.production() &&
                                electricityStats.usage() + house.getElectricityUsage() <= electricityStats.production()) {
                            cityMap.set(x, y, house);

                            // Update stats
                            waterStats = new WaterStats(waterStats.usage() + house.getWaterUsage(), waterStats.production());
                            electricityStats = new ElectricityStats(electricityStats.usage() + house.getElectricityUsage(), electricityStats.production());

                            // Resource usage maxed out
                            if (waterStats.usage() >= waterStats.production() || electricityStats.usage() >= electricityStats.production()) return;
                        }
                    }
                }
            }

        } else if (waterStats.usage() > waterStats.production() || electricityStats.usage() > electricityStats.production()) {
            // Not enough resources - destroy houses

            for (int x = 0; x < cityMap.getWidth(); x++) {
                for (int y = 0; y < cityMap.getHeight(); y++) {

                    if (cityMap.get(x, y) instanceof House house) {

                        cityMap.set(x, y, Building.HOUSING_AREA);

                        // Update stats
                        waterStats = new WaterStats(waterStats.usage() - house.getWaterUsage(), waterStats.production());
                        electricityStats = new ElectricityStats(electricityStats.usage() - house.getElectricityUsage(), electricityStats.production());

                        // Resource usage stabilized
                        if (waterStats.usage() <= waterStats.production() && electricityStats.usage() <= electricityStats.production()) return;
                    }
                }
            }
        }
    }
}
