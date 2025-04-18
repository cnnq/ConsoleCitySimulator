package layers;

import com.googlecode.lanterna.screen.Screen;
import org.jetbrains.annotations.NotNull;

public interface Renderable {

    /**
     * Renders into screen
     * @param screen target screen
     */
    void render(@NotNull Screen screen);
}
