package layers;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.screen.Screen;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import other.PerlinNoise;

public class TerrainLayer extends Layer {

    /**
     * Generate map of terrain
     * @param width number of displayed characters horizontally
     * @param height number of displayed characters vertically
     * @param seed seed to generate terrain from
     * @param cellSize determines how big islands of the same terrain should be
     */
    public TerrainLayer(@Range(from = 0, to = Integer.MAX_VALUE) int width,
                        @Range(from = 0, to = Integer.MAX_VALUE) int height,
                        int seed,
                        @Range(from = 1, to = Integer.MAX_VALUE) int cellSize) {

        super(width, height);

        PerlinNoise noise = new PerlinNoise(seed);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                double h = noise.getNoiseAt((double)x / cellSize, (double)y * 2 / cellSize);

                if (h < 0.25) {
                    set(x, y, ' ');
                } else if(h < 0.5) {
                    set(x, y, '.');
                } else if(h < 0.75) {
                    set(x, y, '/');
                } else {
                    set(x, y, '#');
                }
            }
        }
    }

    @Override
    public void render(@NotNull Screen screen) {
        TerminalSize size = screen.getTerminalSize();

        for (int y = 0; y < size.getRows(); y++) {
            for (int x = 0; x < size.getColumns(); x++) {
                screen.setCharacter(x, y, new TextCharacter(get(x, y)));
            }
        }
    }
}
