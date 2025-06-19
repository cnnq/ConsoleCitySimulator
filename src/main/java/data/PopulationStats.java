package data;

/**
 * @param population current number of inhabitants
 * @param residences maximum number of inhabitants
 */
public record PopulationStats(int population, int residences) {

    public PopulationStats {
        if (population < 0) throw new IllegalArgumentException("population cannot be negative");
        if (residences < 0) throw new IllegalArgumentException("residences cannot be negative");
    }
}
