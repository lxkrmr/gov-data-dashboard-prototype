package lx.krmr.dashboard.domain;

import lx.krmr.dashboard.domain.model.types.Department;
import lx.krmr.dashboard.domain.model.types.FederalMinistryStatistic;
import lx.krmr.dashboard.domain.model.types.Superior;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class DepartmentTest {

    @Test
    void shouldCreateDepartment() {
        // when
        Department result = Department.create("<superior>",
                                              List.of("<first-subordinate>", "<second-subordinate>"));

        // then
        assertThat(result.superior()).isEqualTo(new Superior("<superior>"));
        assertThat(result.subordinates()).isEqualTo(Map.of("<first-subordinate>", new FederalMinistryStatistic("<first-subordinate>", 0),
                                                           "<second-subordinate>", new FederalMinistryStatistic("<second-subordinate>", 0)));
    }

    @Test
    void shouldCreateDepartmentWithoutSubordinates() {
        // when
        Department result = Department.create("<superior>",
                                              Collections.emptyList());

        // then
        assertThat(result.superior()).isEqualTo(new Superior("<superior>"));
        assertThat(result.subordinates()).isEqualTo(Collections.emptyMap());
    }

    @Test
    void shouldAddStatisticIfSuperiorNameIsStatisticName() {
        // given
        String superiorNameIsStatisticName = "<superior>";
        FederalMinistryStatistic statistic = new FederalMinistryStatistic(superiorNameIsStatisticName,
                                                                          12);
        Department department = Department.create(superiorNameIsStatisticName,
                                                  List.of("<first-subordinate>", "<second-subordinate>"));

        // when
        Department result = department.addIfPartOfDepartment(List.of(statistic));

        // then
        assertThat(result.superior()
                         .statistic()).isEqualTo(statistic);
    }

    @Test
    void shouldAddStatisticIfSubordinateNameIsStatisticName() {
        // given
        String subordinateNameIsStatisticName = "<first-subordinate>";
        FederalMinistryStatistic statistic = new FederalMinistryStatistic(subordinateNameIsStatisticName,
                                                                          12);
        Department department = Department.create("<superior>",
                                                  List.of(subordinateNameIsStatisticName, "<second-subordinate>"));

        // when
        Department result = department.addIfPartOfDepartment(List.of(statistic));

        // then
        assertThat(result.subordinates()
                         .get(subordinateNameIsStatisticName)).isEqualTo(statistic);
    }

    @Test
    void shouldAddStatistics() {
        // given
        String superiorNameIsStatisticName = "<superior>";
        FederalMinistryStatistic statisticForSuperior = new FederalMinistryStatistic(superiorNameIsStatisticName,
                                                                                     12);
        String subordinateNameIsStatisticName = "<first-subordinate>";
        FederalMinistryStatistic statisticForSubordinate = new FederalMinistryStatistic(subordinateNameIsStatisticName,
                                                                                        12);
        Department department = Department.create(superiorNameIsStatisticName,
                                                  List.of(subordinateNameIsStatisticName, "<second-subordinate>"));

        // when
        Department result = department.addIfPartOfDepartment(List.of(statisticForSuperior, statisticForSubordinate));

        // then
        assertThat(result.superior()
                         .statistic()).isEqualTo(statisticForSuperior);
        assertThat(result.subordinates()
                         .get(subordinateNameIsStatisticName)).isEqualTo(statisticForSubordinate);
    }

    @Test
    void shouldNotAddStatisticIfNeitherSuperiorNameNorSubordinateNameIsStatisticName() {
        // given
        FederalMinistryStatistic statistic = new FederalMinistryStatistic("<name-is-not-matching>",
                                                                          12);
        Department department = Department.create("<superior>",
                                                  List.of("<first-subordinate>", "<second-subordinate>"));

        // when
        Department result = department.addIfPartOfDepartment(List.of(statistic));

        // then
        assertThat(result.superior()
                         .statistic()).extracting(FederalMinistryStatistic::numberOfPublishedDataSets)
                                      .isEqualTo(0);
        assertThat(result.subordinates()
                         .values())
                .extracting(FederalMinistryStatistic::numberOfPublishedDataSets)
                .allMatch(numberOfPublishedDataSets -> numberOfPublishedDataSets == 0);
    }
}