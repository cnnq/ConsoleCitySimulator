package graphics;

import org.jetbrains.annotations.NotNull;

public class SpritemapNotLoadedException extends RuntimeException {
    public SpritemapNotLoadedException(@NotNull String path) {
        super("Spritemap" + path + " was not loaded.");
    }
}
