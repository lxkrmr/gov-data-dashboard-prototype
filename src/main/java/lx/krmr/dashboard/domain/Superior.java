package lx.krmr.dashboard.domain;

import java.util.Objects;
import java.util.Optional;

// FYI: Sadly this can't be a record as we have to update maybeStatistics
public class Superior {
    private final String name;
    private Optional<FederalMinistryStatistic> maybeStatistics;

    public Superior(String name) {
        this.name = name;
        this.maybeStatistics = Optional.empty();
    }

    public String name() {
        return name;
    }

    public Optional<FederalMinistryStatistic> maybeStatistics() {
        return maybeStatistics;
    }

    public void maybeStatistics(Optional<FederalMinistryStatistic> maybeStatistics) {
        this.maybeStatistics = maybeStatistics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Superior superior = (Superior) o;
        return Objects.equals(name, superior.name) && Objects.equals(maybeStatistics, superior.maybeStatistics);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, maybeStatistics);
    }
}
