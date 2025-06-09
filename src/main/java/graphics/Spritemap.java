package graphics;

import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Spritemap {

    private static final String DEFAULT_SPRITEMAP_PATH = "res/spritemap.png";

    public static Spritemap DEFAULT = new Spritemap(DEFAULT_SPRITEMAP_PATH);

    protected Image image;


    public Spritemap(@NotNull String path) {
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Image getImage() {
        return image;
    }
}
