package layers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import other.Building;
import other.GameState;

import java.awt.*;
import java.awt.event.MouseEvent;

class CityLayerTest {

    private static CityLayer cityLayer;

    @BeforeEach
    void setUp() {
        cityLayer = new CityLayer(new GameState());
    }


    @Test
    void edit_noButton() {
        Rectangle rectangle = new Rectangle(0, 0, 16, 24);

        boolean success = cityLayer.edit(rectangle, MouseEvent.NOBUTTON);

        Assertions.assertFalse(success);
    }

    @Test
    void edit_button1() {
        Rectangle rectangle = new Rectangle(0, 0, 16, 24);
        int selected = Math.max(rectangle.width, rectangle.height) + 1;

        boolean success = cityLayer.edit(rectangle, MouseEvent.BUTTON1);

        Assertions.assertTrue(success);
        Assertions.assertEquals(selected, cityLayer.count(rectangle, Building.ROAD));
    }

    @Test
    void edit_button1_outOfBoundsNegative() {
        Rectangle rectangle = new Rectangle(-8, -12, 16, 24);
        int selected = Math.max(rectangle.width + rectangle.x, rectangle.height + rectangle.y) + 1;

        boolean success = cityLayer.edit(rectangle, MouseEvent.BUTTON1);

        Assertions.assertTrue(success);
        Assertions.assertEquals(selected, cityLayer.count(rectangle, Building.ROAD));
    }

    @Test
    void edit_button1_outOfBoundsPositive() {
        Rectangle rectangle = new Rectangle(cityLayer.getWidth() - 8, cityLayer.getHeight() - 12, 16, 24);
        int selected = Math.max(cityLayer.getWidth() - rectangle.x, cityLayer.getHeight() - rectangle.y);

        boolean success = cityLayer.edit(rectangle, MouseEvent.BUTTON1);

        Assertions.assertTrue(success);
        Assertions.assertEquals(selected, cityLayer.count(rectangle, Building.ROAD));
    }
}