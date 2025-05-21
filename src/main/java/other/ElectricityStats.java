package other;

public record ElectricityStats(double usage, double production) {

    public ElectricityStats(double usage, double production) {
        if (usage < 0) throw new IllegalArgumentException("usage cannot be negative");
        if (production < 0) throw new IllegalArgumentException("production cannot be negative");
        this.usage = usage;
        this.production = production;
    }
}