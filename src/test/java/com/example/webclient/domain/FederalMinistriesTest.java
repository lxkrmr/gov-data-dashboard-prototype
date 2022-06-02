package com.example.webclient.domain;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

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
                                        .extracting(Superior::maybeStatistics)
                                        .allMatch(Optional::isEmpty);
    }

    @Test
    void shouldInitializeSubordinates() {
        // when
        FederalMinistries result = FederalMinistries.create(List.of());

        // then
        List<Map<String, Optional<FederalMinistryStatistic>>> notEmptySubordinates = result.departments()
                                                                                           .stream()
                                                                                           .map(Department::subordinates)
                                                                                           .filter(Predicate.not(Map::isEmpty))
                                                                                           .toList();
        List<String> subordinateNames = notEmptySubordinates.stream()
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
        List<Optional<FederalMinistryStatistic>> subordinateStatistics = notEmptySubordinates.stream()
                                                                                             .flatMap(map -> map.values()
                                                                                                                .stream())
                                                                                             .toList();
        assertThat(subordinateStatistics).allMatch(Optional::isEmpty);
    }

    @Test
    void shouldUpdateStatistics() {
        // given
        FederalMinistryStatistic federalMinistryStatistic = new FederalMinistryStatistic("Auswärtiges Amt",
                                                                                         "auswaertiges-amt",
                                                                                         42);

        // when
        FederalMinistries result = FederalMinistries.create(List.of(federalMinistryStatistic));

        // then
        assertThat(result.departments()).first()
                                        .extracting(Department::superior)
                                        .extracting(Superior::maybeStatistics)
                                        .isEqualTo(Optional.of(federalMinistryStatistic));
    }
}