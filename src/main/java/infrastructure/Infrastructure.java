package infrastructure;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.jetbrains.annotations.Nullable;
import data.Directions;
import graphics.Sprite;

import java.awt.*;
import java.util.EnumSet;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = House.class, name = "house"),
        @JsonSubTypes.Type(value = CommercialBuilding.class, name = "commercialBuilding"),
        @JsonSubTypes.Type(value = IndustrialBuilding.class, name = "industrialBuilding"),
        @JsonSubTypes.Type(value = UnmanagedBuilding.class, name = "unmanagedBuilding"),
        @JsonSubTypes.Type(value = ManagedBuilding.class, name = "managedBuilding"),
        @JsonSubTypes.Type(value = UnmanagedInfrastructure.class, name = "unmanagedInfrastructure"),
        @JsonSubTypes.Type(value = ManagedInfrastructure.class, name = "managedInfrastructure"),
})
public abstract class Infrastructure {

    private final Sprite sprite;
    private final double buildingCost;

    /**
     * Create instance of infrastructure
     * @param sprite
     * @param buildingCost cost of building
     */
    protected Infrastructure(@Nullable Sprite sprite,
                             double buildingCost) {

        if (buildingCost < 0) throw new IllegalArgumentException("buildingCost cannot be negative");

        this.sprite = sprite;
        this.buildingCost = buildingCost;
    }

    /**
     * Draws current building
     * @param g
     * @param x displayed tile coordinate
     * @param y displayed tile coordinate
     * @param neighbourData null if {$code isNeighbourDependent() is false}
     */
    public void draw(Graphics g, int x, int y, EnumSet<Directions> neighbourData) {
        if (sprite != null) {
            sprite.draw(g, x, y, neighbourData);
        }
    }

    public double getBuildingCost() {
        return buildingCost;
    }

    public boolean isNeighbourDependent() {
        if (sprite == null) return false;
        return sprite.isNeighbourDependent();
    }
}
