package lx.krmr.dashboard.domain.model.types;

import java.util.Optional;

public record Superior(String name, Optional<FederalMinistryStatistic> maybeStatistics) {
    public Superior(String name) {
        this(name, Optional.empty());
    }
}
