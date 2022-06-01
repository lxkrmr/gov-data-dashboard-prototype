package com.example.webclient.domain;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class DepartmentTest {

    @Test
    void shouldCreateDepartment() {
        // when
        Department result = Department.create("<superior>",
                                              List.of("<first-subordinate>", "<second-subordinate>"));

        // then
        assertThat(result.getSuperior()).isEqualTo(Map.of("<superior>", Optional.empty()));
        assertThat(result.getSubordinates()).isEqualTo(Map.of("<first-subordinate>", Optional.empty(),
                                                              "<second-subordinate>", Optional.empty()));
    }

    @Test
    void shouldCreateDepartmentWithoutSubordinates() {
        // when
        Department result = Department.create("<superior>",
                                              Collections.emptyList());

        // then
        assertThat(result.getSuperior()).isEqualTo(Map.of("<superior>", Optional.empty()));
        assertThat(result.getSubordinates()).isEqualTo(Collections.emptyMap());
    }

    @Test
    void shouldAddStatisticIfSuperiorNameIsStatisticName() {
        // given
        String superiorNameIsStatisticName = "<superior>";
        FederalMinistryStatistic statistic = new FederalMinistryStatistic("<display-name>",
                                                                          superiorNameIsStatisticName,
                                                                          12);
        Department department = Department.create(superiorNameIsStatisticName,
                                                  List.of("<first-subordinate>", "<second-subordinate>"));

        // when
        Department result = department.addIfPartOfDepartment(List.of(statistic));

        // then
        assertThat(result.getSuperior()
                         .get(superiorNameIsStatisticName)).contains(statistic);
    }

    @Test
    void shouldAddStatisticIfSubordinateNameIsStatisticName() {
        // given
        String subordinateNameIsStatisticName = "<first-subordinate>";
        FederalMinistryStatistic statistic = new FederalMinistryStatistic("<display-name>",
                                                                          subordinateNameIsStatisticName,
                                                                          12);
        Department department = Department.create("<superior>",
                                                  List.of(subordinateNameIsStatisticName, "<second-subordinate>"));

        // when
        Department result = department.addIfPartOfDepartment(List.of(statistic));

        // then
        assertThat(result.getSubordinates()
                         .get(subordinateNameIsStatisticName)).contains(statistic);
    }

    @Test
    void shouldAddStatistics() {
        // given
        String superiorNameIsStatisticName = "<superior>";
        FederalMinistryStatistic statisticForSuperior = new FederalMinistryStatistic("<display-name>",
                                                                          superiorNameIsStatisticName,
                                                                          12);
        String subordinateNameIsStatisticName = "<first-subordinate>";
        FederalMinistryStatistic statisticForSubordinate = new FederalMinistryStatistic("<display-name>",
                                                                          subordinateNameIsStatisticName,
                                                                          12);
        Department department = Department.create(superiorNameIsStatisticName,
                                                  List.of(subordinateNameIsStatisticName, "<second-subordinate>"));

        // when
        Department result = department.addIfPartOfDepartment(List.of(statisticForSuperior, statisticForSubordinate));

        // then
        assertThat(result.getSuperior()
                         .get(superiorNameIsStatisticName)).contains(statisticForSuperior);
        assertThat(result.getSubordinates()
                         .get(subordinateNameIsStatisticName)).contains(statisticForSubordinate);
    }

    @Test
    void shouldNotAddStatisticIfNeitherSuperiorNameNorSubordinateNameIsStatisticName() {
        // given
        FederalMinistryStatistic statistic = new FederalMinistryStatistic("<display-name>",
                                                                          "<name-is-not-matching>",
                                                                          12);
        Department department = Department.create("<superior>",
                                                  List.of("<first-subordinate>", "<second-subordinate>"));

        // when
        Department result = department.addIfPartOfDepartment(List.of(statistic));

        // then
        assertThat(result.getSuperior()
                         .values()).allMatch(Optional::isEmpty);
        assertThat(result.getSubordinates()
                         .values()).allMatch(Optional::isEmpty);
    }
}