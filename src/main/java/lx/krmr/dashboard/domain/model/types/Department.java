package lx.krmr.dashboard.domain.model.types;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public record Department(Superior superior, Map<String, Optional<FederalMinistryStatistic>> subordinates) {

    public static Department create(String superiorName, List<String> subordinateNames) {
        return new Department(new Superior(superiorName),
                              subordinateNames.stream()
                                              .collect(Collectors.toMap(subordinateName -> subordinateName, v -> Optional.empty())));
    }

    public static Department create(String superiorName) {
        return new Department(new Superior(superiorName),
                              new HashMap<>());
    }

    public Department addIfPartOfDepartment(List<FederalMinistryStatistic> statistics) {
        return statistics.stream()
                         .map(this::addIfPartOfDepartment)
                         .reduce(this, this::combinePresentStatistics);
    }

    private Department addIfPartOfDepartment(FederalMinistryStatistic statistic) {
        if (matchesSuperior(statistic)) {
            Superior updatedSuperior = new Superior(statistic.name(), Optional.of(statistic));
            return new Department(updatedSuperior, this.subordinates());
        }

        if (matchesSubordinate(statistic)) {
            subordinates.put(statistic.name(), Optional.of(statistic));
            return this;
        }

        return this;
    }

    private boolean matchesSuperior(FederalMinistryStatistic statistic) {
        return superior.name()
                       .equals(statistic.name());
    }

    private boolean matchesSubordinate(FederalMinistryStatistic statistic) {
        return subordinates.containsKey(statistic.name());
    }

    private Department combinePresentStatistics(Department one, Department two) {
        Superior superior = pickSuperiorWithStatistic(one.superior(), two.superior());
        Map<String, Optional<FederalMinistryStatistic>> subordinates = pickSubordinateWithMostStatistics(one.subordinates(), two.subordinates());
        return new Department(superior, subordinates);
    }

    private Map<String, Optional<FederalMinistryStatistic>> pickSubordinateWithMostStatistics(Map<String, Optional<FederalMinistryStatistic>> one,
                                                                                              Map<String, Optional<FederalMinistryStatistic>> two) {
        return one.size() > two.size() ? one : two;
    }

    private Superior pickSuperiorWithStatistic(Superior one, Superior two) {
        return one.maybeStatistics()
                  .isPresent() ? one : two;
    }
}
