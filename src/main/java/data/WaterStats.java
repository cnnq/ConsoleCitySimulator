package data;

public record WaterStats(double usage, double production) {

    public WaterStats {
        if (usage < 0) throw new IllegalArgumentException("usage cannot be negative");
        if (production < 0) throw new IllegalArgumentException("production cannot be negative");
    }
}