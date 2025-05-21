package other;

import org.jetbrains.annotations.Range;

/**
 * @param population current number of inhabitants
 * @param capacity maximum number of inhabitants
 */
public record PopulationStats(@Range(from = 0, to = Integer.MAX_VALUE) int population, @Range(from = 0, to = Integer.MAX_VALUE) int capacity) {}
