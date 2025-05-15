package layers;

import gui.GameWindow;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import other.Building;
import other.Game;

import java.awt.*;
import java.awt.event.MouseEvent;

class CityLayerTest {

    private static CityLayer cityLayer;

    @BeforeEach
    void setUp() {
        cityLayer = new CityLayer(new Game(new GameWindow()));
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
        Assertions.assertEquals(selected, cityLayer.count(rectangle, Building.HOUSE_1));
    }

    @Test
    void edit_button1_outOfBoundsNegative() {
        Rectangle rectangle = new Rectangle(-8, -12, 16, 24);

        Assertions.assertThrows(IllegalArgumentException.class, () -> cityLayer.edit(rectangle, MouseEvent.BUTTON1));
    }

    @Test
    void edit_button1_outOfBoundsPositive() {
        Rectangle rectangle = new Rectangle(cityLayer.getWidth() - 8, cityLayer.getHeight() - 12, 16, 24);

        Assertions.assertThrows(IllegalArgumentException.class, () -> cityLayer.edit(rectangle, MouseEvent.BUTTON1));
    }
}