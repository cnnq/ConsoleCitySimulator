package other;

import java.util.Random;

public class PerlinNoise {

    private final int[] permutation;

    /**
     * Set perlin noise permutation table for given seed
     * @param seed
     */
    public PerlinNoise(int seed) {
        permutation = new int[512];

        Random rand = new Random(seed);

        // Initialize permutation array
        int[] p = new int[256];
        for (int i = 0; i < 256; i++) {
            p[i] = i;
        }

        // Shuffle
        for (int i = 255; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            int temp = p[i];
            p[i] = p[index];
            p[index] = temp;
        }

        // Duplicate the array
        for (int i = 0; i < 512; i++) {
            permutation[i] = p[i % 256];
        }
    }

    /**
     * Gives noise value at (x, y) point.
     * Integer x, y values exist on cell edges.
     * @param x
     * @param y
     * @return Value between 0 and 1
     */
    public double getNoiseAt(double x, double y) {
        // Find unit grid cell containing point
        // Using Math.floor() because of possible negative values
        int X = (int) Math.floor(x) & 255;
        int Y = (int) Math.floor(y) & 255;

        // Relative x, y
        x -= Math.floor(x);
        y -= Math.floor(y);

        // Compute fade curves
        double u = fade(x);
        double v = fade(y);

        // Hash coordinates of the 4 square corners
        int aa = permutation[X + permutation[Y]];
        int ab = permutation[X + permutation[Y + 1]];
        int ba = permutation[X + 1 + permutation[Y]];
        int bb = permutation[X + 1 + permutation[Y + 1]];

        // Add blended results from all corners
        double result = lerp(v,
                lerp(u, grad(aa, x, y), grad(ba, x - 1, y)),
                lerp(u, grad(ab, x, y - 1), grad(bb, x - 1, y - 1))
        );

        return (result + 1.0) / 2.0; // Normalize to [0, 1]
    }

    private double fade(double t) {
        // 6t^5 - 15t^4 + 10t^3
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    private double lerp(double t, double a, double b) {
        return a + t * (b - a);
    }

    private double grad(int hash, double x, double y) {
        int h = hash & 7;           // Convert low 3 bits of hash code
        double u = h < 4 ? x : y;   // If 3rd bit set on
        double v = h < 4 ? y : x;   // - same -
        return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
    }
}