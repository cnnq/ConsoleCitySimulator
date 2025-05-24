package layers;

import gui.GameWindow;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import infrastructure.Infrastructure;
import other.Game;

import java.awt.*;

class LayerTest {

    static Layer<Infrastructure> layer;

    @BeforeEach
    void setUp() {
        layer = new CityLayer(new Game(new GameWindow()));
    }

    @Test
    void fill_inside() {
        Rectangle fillRectangle = new Rectangle(0, 0, 16, 24);
        Rectangle checkRectangle = new Rectangle(0, 0, 16 + 1, 24 + 1);
        int expected = (fillRectangle.width + 1) * (fillRectangle.height + 1);

        layer.fill(fillRectangle, Infrastructure.ROAD);

        Assertions.assertEquals(expected, layer.count(checkRectangle, Infrastructure.ROAD));
    }

    @Test
    void fill_outsideNegative() {
        Rectangle rectangle = new Rectangle(-8, -12, 16, 24);

        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> layer.fill(rectangle, Infrastructure.ROAD));
    }

    @Test
    void fill_outsidePositive() {
        Rectangle rectangle = new Rectangle(layer.getWidth() - 8, layer.getHeight() - 12, layer.getWidth() + 8, layer.getHeight() + 12);

        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> layer.fill(rectangle, Infrastructure.ROAD));
    }

    @Test
    void replace_inside() {
        Rectangle fillRectangle = new Rectangle(0, 0, 16, 24);
        Rectangle checkRectangle = new Rectangle(0, 0, 16 + 1, 24 + 1);
        int expected = layer.count(fillRectangle, null);

        layer.replace(fillRectangle, null, Infrastructure.ROAD);

        Assertions.assertEquals(expected, layer.count(checkRectangle, Infrastructure.ROAD));
    }

    @Test
    void replace_outsideNegative() {
        Rectangle rectangle = new Rectangle(-8, -12, 16, 24);

        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> layer.replace(rectangle, null, Infrastructure.ROAD));
    }

    @Test
    void replace_outsidePositive() {
        Rectangle rectangle = new Rectangle(layer.getWidth() - 8, layer.getHeight() - 12, 16, 24);

        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> layer.replace(rectangle, null, Infrastructure.ROAD));
    }

    @Test
    void count_inside() {
        Rectangle rectangle = new Rectangle(0, 0, 16, 24);
        int expected = (rectangle.width + 1) * (rectangle.height + 1);

        int actualNull = layer.count(rectangle, null);
        int actualWater = layer.count(rectangle, Infrastructure.WATER);

        Assertions.assertEquals(expected, actualNull + actualWater);
    }

    @Test
    void count_outsideNegative() {
        Rectangle rectangle = new Rectangle(-8, -12, 16, 24);

        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> layer.count(rectangle, null));
    }

    @Test
    void count_outsidePositive() {
        Rectangle rectangle = new Rectangle(layer.getWidth() - 8, layer.getHeight() - 12, 16, 24);

        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> layer.count(rectangle, null));
    }

    @Test
    void neighbours() {
    }

    @Test
    void getNeighbourData() {
    }
}