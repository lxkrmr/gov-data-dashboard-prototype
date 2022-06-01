package com.example.webclient.domain;

import java.util.*;
import java.util.stream.Collectors;

public class Department {

    private final Map<String, Optional<FederalMinistryStatistic>> superior;
    private final Map<String, Optional<FederalMinistryStatistic>> subordinates;

    private Department(Map<String, Optional<FederalMinistryStatistic>> superior,
                       Map<String, Optional<FederalMinistryStatistic>> subordinates) {
        this.superior = superior;
        this.subordinates = subordinates;
    }

    public static Department create(String superiorName, List<String> subordinateNames) {
        return new Department(new HashMap<>(Map.of(superiorName, Optional.empty())),
                              subordinateNames.stream()
                                              .collect(Collectors.toMap(subordinateName -> subordinateName, v -> Optional.empty())));
    }

    public static Department create(String superiorName) {
        return new Department(new HashMap<>(Map.of(superiorName, Optional.empty())),
                              new HashMap<>());
    }

    public Department addIfPartOfDepartment(List<FederalMinistryStatistic> statistics) {
        statistics.forEach(this::addIfPartOfDepartment);
        return this;
    }

    private Department addIfPartOfDepartment(FederalMinistryStatistic statistic) {
        if (superior.containsKey(statistic.name())) {
            superior.put(statistic.name(), Optional.of(statistic));
            return this;
        }

        if (subordinates.containsKey(statistic.name())) {
            subordinates.put(statistic.name(), Optional.of(statistic));
            return this;
        }

        return this;
    }

    public Map<String, Optional<FederalMinistryStatistic>> getSuperior() {
        return superior;
    }

    public Map<String, Optional<FederalMinistryStatistic>> getSubordinates() {
        return subordinates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Department that = (Department) o;
        return superior.equals(that.superior) && subordinates.equals(that.subordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(superior, subordinates);
    }
}
