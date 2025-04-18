package layers;

import other.Screen;
import org.jetbrains.annotations.NotNull;

public interface Renderable {

    /**
     * Renders into screenBuffer
     * @param screen target screen
     * @param screenBuffer target screenBuffer
     */
    void render(@NotNull Screen screen, char[][] screenBuffer);
}
