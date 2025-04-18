package layers;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import other.PerlinNoise;

public class TerrainLayer implements Layer<TerrainType>, Renderable {

    private final int width;
    private final int height;
    protected TerrainType[][] buffer;

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

        this.width = width;
        this.height = height;
        buffer = new TerrainType[width][height];

        // Generate terrain
        PerlinNoise noise1 = new PerlinNoise(seed);
        PerlinNoise noise2 = new PerlinNoise(seed + 3);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                double h = 0;
                h += noise1.getNoiseAt((double)x / cellSize, (double)y * 2 / cellSize);
                h += noise2.getNoiseAt((double)x * 2 / cellSize, (double)y * 2 * 2 / cellSize);
                h /= 2;

                if (h < 0.27) {
                    set(x, y, TerrainType.DEEP_WATER);
                } else if (h < 0.5) {
                    set(x, y, TerrainType.WATER);
                } else if (h < 0.7) {
                    set(x, y, TerrainType.GRASS);
                } else {
                    set(x, y, TerrainType.STONE);
                }
            }
        }
    }

    @Override
    public void render(@NotNull Screen screen) {
        TerminalSize size = screen.getTerminalSize();

        for (int y = 0; y < size.getRows(); y++) {
            for (int x = 0; x < size.getColumns(); x++) {
                TextGraphics textGraphics = screen.newTextGraphics();

                TextColor color = switch (get(x, y)) {
                    case DEEP_WATER -> TextColor.ANSI.BLUE;
                    case WATER -> TextColor.ANSI.BLUE_BRIGHT;
                    case GRASS -> TextColor.ANSI.GREEN_BRIGHT;
                    case STONE -> TextColor.ANSI.BLACK_BRIGHT;
                    default -> TextColor.ANSI.DEFAULT;
                };

                textGraphics.setBackgroundColor(color);
                textGraphics.setCharacter(x, y, ' ');
            }
        }
    }

    @Override
    public TerrainType get(@Range(from = 0, to = Integer.MAX_VALUE) int x, @Range(from = 0, to = Integer.MAX_VALUE) int y) {
        return buffer[y][x];
    }

    @Override
    public void set(@Range(from = 0, to = Integer.MAX_VALUE) int x, @Range(from = 0, to = Integer.MAX_VALUE) int y, TerrainType value) {
        buffer[y][x] = value;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
