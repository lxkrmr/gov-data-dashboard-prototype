package lx.krmr.dashboard.domain.model.types;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record Department(Superior superior, Map<String, FederalMinistryStatistic> subordinates) {

    public static Department create(String superiorName, List<String> subordinateNames) {
        return new Department(new Superior(superiorName),
                              subordinateNames.stream()
                                              .collect(Collectors.toMap(subordinateName -> subordinateName,
                                                                        subordinateName -> new FederalMinistryStatistic(subordinateName, 0))));
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
            Superior updatedSuperior = new Superior(statistic.name(), statistic);
            return new Department(updatedSuperior, this.subordinates());
        }

        if (matchesSubordinate(statistic)) {
            subordinates.put(statistic.name(), statistic);
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
        Map<String, FederalMinistryStatistic> subordinates = pickSubordinateWithMostStatistics(one.subordinates(), two.subordinates());
        return new Department(superior, subordinates);
    }

    private Map<String, FederalMinistryStatistic> pickSubordinateWithMostStatistics(Map<String, FederalMinistryStatistic> one,
                                                                                    Map<String, FederalMinistryStatistic> two) {
        return one.size() > two.size() ? one : two;
    }

    private Superior pickSuperiorWithStatistic(Superior one, Superior two) {
        return one.statistic()
                  .numberOfPublishedDataSets() > 0 ? one : two;
    }
}
