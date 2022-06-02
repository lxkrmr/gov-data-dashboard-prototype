package com.example.webclient.domain;

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
        statistics.forEach(this::addIfPartOfDepartment);
        return this;
    }

    private void addIfPartOfDepartment(FederalMinistryStatistic statistic) {
        if (superior.name()
                    .equals(statistic.name())) {
            superior.maybeStatistics(Optional.of(statistic));
        }
        else if (subordinates.containsKey(statistic.name())) {
            subordinates.put(statistic.name(), Optional.of(statistic));
        }
    }
}
