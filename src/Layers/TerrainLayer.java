package Layers;

import Other.Screen;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public class TerrainLayer extends Layer {

    public TerrainLayer(@Range(from = 0, to = Integer.MAX_VALUE) int width, @Range(from = 0, to = Integer.MAX_VALUE) int height) {
        super(width, height);

        for (int y = 0; y < width; y++) {
            for (int x = 0; x < width; x++) {
                buffer[y][x] = 'T';
            }
        }
    }

    @Override
    public void render(@NotNull Screen screen, char[][] screenBuffer) {
        for (int y = 0; y < screen.getHeight(); y++) {
            if (screen.getWidth() >= 0) System.arraycopy(buffer[y], 0, screenBuffer[y], 0, screen.getWidth());
        }
    }
}
