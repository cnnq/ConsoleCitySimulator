package data;

public record EmploymentStats(int jobSeekers, int jobs) {
    public EmploymentStats {
        if (jobSeekers < 0) throw new IllegalArgumentException("jobSeekers cannot be negative");
        if (jobs < 0) throw new IllegalArgumentException("jobs cannot be negative");
    }
}
