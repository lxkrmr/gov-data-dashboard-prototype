package lx.krmr.dashboard.domain;

import lx.krmr.dashboard.domain.model.types.Department;
import lx.krmr.dashboard.domain.model.types.FederalMinistryStatistic;
import lx.krmr.dashboard.domain.model.types.Superior;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FederalMinistriesTest {

    @Test
    void shouldInitializeSuperiors() {
        // when
        FederalMinistries result = FederalMinistries.create(List.of());

        // then
        assertThat(result.departments())
                .extracting(Department::superior)
                .extracting(Superior::name)
                .containsExactlyInAnyOrder("auswaertiges-amt",
                                           "bmj",
                                           "bmf",
                                           "bmvg",
                                           "bmi",
                                           "bmas",
                                           "bundesministerium-fur-bildung-und-forschung",
                                           "bmfsfj",
                                           "bmz",
                                           "bmwi",
                                           "bmel",
                                           "bmg",
                                           "bmdv");
        // and
        assertThat(result.departments()).extracting(Department::superior)
                                        .extracting(Superior::statistic)
                                        .extracting(FederalMinistryStatistic::numberOfPublishedDataSets)
                                        .allMatch(numberOfPublishedDataSets -> numberOfPublishedDataSets == 0);
    }

    @Test
    void shouldInitializeSubordinates() {
        // when
        FederalMinistries result = FederalMinistries.create(List.of());

        // then
        List<String> subordinateNames = result.departments()
                                              .stream()
                                              .map(Department::subordinates)
                                              .flatMap(map -> map.keySet()
                                                                 .stream())
                                              .toList();
        assertThat(subordinateNames).containsExactlyInAnyOrder("bundesamt-fur-justiz",
                                                               "dpma",
                                                               "bzst",
                                                               "zoll",
                                                               "itzbund",
                                                               "bbsr",
                                                               "statistisches-bundesamt",
                                                               "bva",
                                                               "badv",
                                                               "baua",
                                                               "bafa",
                                                               "bam",
                                                               "bsa",
                                                               "mri",
                                                               "bvl",
                                                               "bas",
                                                               "mcloud");
        // and
        List<Integer> subordinateNumberOfPublishedDataSets = result.departments()
                                                                   .stream()
                                                                   .map(Department::subordinates)
                                                                   .flatMap(map -> map.values()
                                                                                      .stream())
                                                                   .map(FederalMinistryStatistic::numberOfPublishedDataSets)
                                                                   .toList();
        assertThat(subordinateNumberOfPublishedDataSets).allMatch(numberOfPublishedDataSets -> numberOfPublishedDataSets == 0);
    }

    @Test
    void shouldUpdateStatistics() {
        // given
        FederalMinistryStatistic federalMinistryStatistic = new FederalMinistryStatistic("auswaertiges-amt",
                                                                                         42);

        // when
        FederalMinistries result = FederalMinistries.create(List.of(federalMinistryStatistic));

        // then
        assertThat(result.departments()).first()
                                        .extracting(Department::superior)
                                        .extracting(Superior::statistic)
                                        .isEqualTo(federalMinistryStatistic);
    }
}