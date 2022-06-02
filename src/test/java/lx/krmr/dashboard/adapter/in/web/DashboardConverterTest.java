package lx.krmr.dashboard.adapter.in.web;

import lx.krmr.dashboard.domain.Department;
import lx.krmr.dashboard.domain.FederalMinistries;
import lx.krmr.dashboard.domain.FederalMinistryStatistic;
import lx.krmr.dashboard.domain.Superior;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static lx.krmr.dashboard.adapter.in.web.DashboardConverter.DASHBOARD_TITLE;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings({"SameParameterValue", "OptionalUsedAsFieldOrParameterType"})
class DashboardConverterTest {

    private final DashboardConverter converter = new DashboardConverter();

    @Test
    void shouldReturnTotalOfPublishedDataSetsFromSuperiorAndSubordinates() {
        // given
        Superior superior = givenSuperiorWithNumberOfPublishedDataSets(11);
        Map<String, Optional<FederalMinistryStatistic>> subordinates = givenSubordinateWithNumberOfPublishedDataSets(12);

        // when
        DashboardResponse result = converter.convert(givenFederalMinistries(superior, subordinates));

        // then
        assertThat(result.data()).containsExactly(23);
    }

    @Test
    void shouldUseDisplayNameOfSuperiorFederalMinistryStatisticAsLabel() {
        // given
        FederalMinistryStatistic federalMinistryStatistic = new FederalMinistryStatistic("<superior-display-name>",
                                                                                         "<superior-name>",
                                                                                         0);
        Superior superior = givenSuperior("<superior-name>", Optional.of(federalMinistryStatistic));

        // when
        DashboardResponse result = converter.convert(givenFederalMinistries(superior, Map.of()));

        // then
        assertThat(result.labels()).containsExactly("<superior-display-name>");
    }

    @Test
    void shouldUseSuperiorNameAsFallbackLabelIfFederalMinistryStatisticIsMissing() {
        // given
        Superior superior = givenSuperior("<superior-name>",Optional.empty());

        // when
        DashboardResponse result = converter.convert(givenFederalMinistries(superior, Map.of()));

        // then
        assertThat(result.labels()).containsExactly("<superior-name>");
    }

    @Test
    void shouldSetTitelOfDashboard() {
        // when
        DashboardResponse result = converter.convert(new FederalMinistries(List.of()));

        // then
        assertThat(result.title()).isEqualTo(DASHBOARD_TITLE);
    }

    private Superior givenSuperiorWithNumberOfPublishedDataSets(int numberOfPublishedDataSets) {
        return givenSuperior("<superior-name>", Optional.of(new FederalMinistryStatistic("<superior-display-name>",
                                                                          "<superior-name>",
                                                                          numberOfPublishedDataSets)));
    }

    private Superior givenSuperior(String superiorName, Optional<FederalMinistryStatistic> maybeFederalMinistryStatistic) {
        Superior superior = new Superior(superiorName);
        superior.maybeStatistics(maybeFederalMinistryStatistic);
        return superior;
    }

    private Map<String, Optional<FederalMinistryStatistic>> givenSubordinateWithNumberOfPublishedDataSets(int numberOfPublishedDataSets) {
        return Map.of("<subordinate-name>", Optional.of(new FederalMinistryStatistic("<subordinate-display-name>",
                                                                                     "<subordinate-name>",
                                                                                     numberOfPublishedDataSets)));
    }

    private FederalMinistries givenFederalMinistries(Superior superior, Map<String, Optional<FederalMinistryStatistic>> subordinates) {
        return new FederalMinistries(List.of(new Department(superior, subordinates)));
    }
}