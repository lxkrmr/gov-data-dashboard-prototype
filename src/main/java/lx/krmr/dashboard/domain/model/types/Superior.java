package lx.krmr.dashboard.domain.model.types;

public record Superior(String name, FederalMinistryStatistic statistic) {
    public Superior(String name) {
        this(name, new FederalMinistryStatistic(name, 0));
    }
}
