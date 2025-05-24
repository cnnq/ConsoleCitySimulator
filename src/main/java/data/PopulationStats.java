package data;

/**
 * @param population current number of inhabitants
 * @param capacity maximum number of inhabitants
 */
public record PopulationStats(int population, int capacity) {

    public PopulationStats {
        if (population < 0) throw new IllegalArgumentException("population cannot be negative");
        if (capacity < 0) throw new IllegalArgumentException("capacity cannot be negative");
    }
}
