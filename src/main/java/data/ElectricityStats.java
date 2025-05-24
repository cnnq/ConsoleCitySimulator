package data;

public record ElectricityStats(double usage, double production) {

    public ElectricityStats {
        if (usage < 0) throw new IllegalArgumentException("usage cannot be negative");
        if (production < 0) throw new IllegalArgumentException("production cannot be negative");
    }
}