package systems;

import data.*;
import infrastructure.*;
import layers.CityLayer;
import layers.Layer;
import layers.PipesLayer;
import layers.WiresLayer;
import main.Game;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * System that simulates water and electricity flow
 */
public class MediaSystem implements GameSystem {

    private static final ManagedBuilding solarPanels = InfrastructureManager.INSTANCE.getInfrastructure("SOLAR_PANELS", ManagedBuilding.class);
    private static final ManagedBuilding waterPump = InfrastructureManager.INSTANCE.getInfrastructure("WATER_PUMP", ManagedBuilding.class);

    private static final Random random = new Random();

    private final Game game;
    private final CityLayer cityMap;


    public MediaSystem(@NotNull Game game) {
        this.game = game;
        this.cityMap = game.getCityMap();
    }

    @Override
    public void update(float deltaTime) {
        CityLayer cityMap = this.cityMap;
        PipesLayer pipesMap = game.getPipesMap();
        WiresLayer wiresMap = game.getWiresMap();

        var waterPumpCoords = new ArrayList<Coord>();
        var solarPanelCoords = new ArrayList<Coord>();

        // Pump water / electricity into the underground system
        for (int y = 0; y < cityMap.getHeight(); y++) {
            for (int x = 0; x < cityMap.getWidth(); x++) {

                Infrastructure infrastructure = cityMap.get(x, y);

                if (!(infrastructure instanceof ManagedBuilding managedBuilding)) continue;

                if (managedBuilding == waterPump) {
                    waterPumpCoords.add(new Coord(x, y));

                    if (pipesMap.get(x, y) == ConductionState.Empty &&
                        random.nextFloat() < (-managedBuilding.getWaterUsage() * deltaTime)) {

                        pipesMap.set(x, y, ConductionState.Filled);
                    }

                } else if (managedBuilding == solarPanels) {
                    solarPanelCoords.add(new Coord(x, y));

                    if (wiresMap.get(x, y) == ConductionState.Empty &&
                        random.nextFloat() < (-managedBuilding.getElectricityUsage() * deltaTime)) {

                        wiresMap.set(x, y, ConductionState.Filled);
                    }
                }
            }
        }

        // Simulate water and electricity flow
        simulateFlow(pipesMap, waterPumpCoords);
        simulateFlow(wiresMap, solarPanelCoords);

        // Simulate usage
        simulateUsage(pipesMap, deltaTime, false);
        simulateUsage(wiresMap, deltaTime, true);
    }

    private void simulateFlow(@NotNull Layer<ConductionState> map, @NotNull List<Coord> initialCoords) {
        var checked = new HashSet<Coord>();
        var nextToCheck = new Stack<Coord>();

        for (Coord c : initialCoords) {
            nextToCheck.push(c);

            while(!nextToCheck.empty()) {
                Coord current = nextToCheck.pop();
                checked.add(current);

                int x = current.x();
                int y = current.y();

                if (map.get(x, y) == ConductionState.Filled) {

                    EnumSet<Directions> emptyPipes = map.getNeighbourData(x, y, ConductionState.Empty);

                    if (emptyPipes.isEmpty()) {
                        // Search another filled pipe

                        EnumSet<Directions> filledPipes = map.getNeighbourData(x, y, ConductionState.Filled);

                        for (Directions direction : filledPipes) {
                            Coord coord = moveCoord(x, y, direction);
                            if(!checked.contains(coord)) nextToCheck.add(coord);
                        }

                    } else {
                        // Move water
                        Coord coord = moveCoord(x, y, emptyPipes.iterator().next());

                        map.set(x, y, ConductionState.Empty);
                        map.set(coord.x(), coord.y(), ConductionState.Filled);
                    }
                }
            }
        }
    }

    private void simulateUsage(@NotNull Layer<ConductionState> map, double deltaTime, boolean electricityUsage) {
        for (int y = 0; y < cityMap.getHeight(); y++) {
            for (int x = 0; x < cityMap.getWidth(); x++) {

                Infrastructure infrastructure = cityMap.get(x, y);

                if (infrastructure instanceof Building building) {

                    double usage = electricityUsage ? building.getElectricityUsage() : building.getWaterUsage();
                    if (usage <= 0) continue;

                    if (random.nextFloat() < usage * deltaTime) {

                        var neighbourData = map.getNeighbourData(x, y, ConductionState.Filled);

                        if (!neighbourData.isEmpty()) {
                            Coord coord = moveCoord(x, y, neighbourData.iterator().next());
                            map.set(coord.x(), coord.y(), ConductionState.Empty);
                        }
                    }
                }
            }
        }
    }

    private Coord moveCoord(int x, int y, Directions direction) {
        return switch (direction) {
            case UP -> new Coord(x, y - 1);
            case RIGHT -> new Coord(x + 1, y);
            case DOWN -> new Coord(x, y + 1);
            case LEFT -> new Coord(x - 1, y);
        };
    }
}
