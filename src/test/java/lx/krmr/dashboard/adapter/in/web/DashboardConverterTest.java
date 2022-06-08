package lx.krmr.dashboard.adapter.in.web;

import lx.krmr.dashboard.adapter.in.web.model.types.DashboardResponse;
import lx.krmr.dashboard.domain.FederalMinistries;
import lx.krmr.dashboard.domain.model.types.Department;
import lx.krmr.dashboard.domain.model.types.FederalMinistryStatistic;
import lx.krmr.dashboard.domain.model.types.Superior;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import static lx.krmr.dashboard.adapter.in.web.DashboardConverter.DASHBOARD_TITLE_KEY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@SuppressWarnings({"SameParameterValue"})
@ExtendWith(MockitoExtension.class)
class DashboardConverterTest {

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private DashboardConverter converter;

    @Test
    void shouldReturnTotalOfPublishedDataSetsFromSuperiorAndSubordinates() {
        // given
        Superior superior = givenSuperiorWithNumberOfPublishedDataSets(11);
        Map<String, FederalMinistryStatistic> subordinates = givenSubordinateWithNumberOfPublishedDataSets(12);

        // when
        DashboardResponse result = converter.convert(givenFederalMinistries(superior, subordinates), Locale.GERMAN);

        // then
        assertThat(result.data()).containsExactly(23);
    }

    @Test
    void shouldGetLabelFromMessageSourceBySuperiorName() {
        // given
        String superiorName = "<superior-name>";
        Superior superior = new Superior(superiorName, new FederalMinistryStatistic(superiorName, 0));
        given(messageSource.getMessage(eq(superiorName), any(), any())).willReturn("<label-from-message-source>");

        // when
        DashboardResponse result = converter.convert(givenFederalMinistries(superior, Map.of()), Locale.GERMAN);

        // then
        assertThat(result.labels()).containsExactly("<label-from-message-source>");
    }

    @Test
    void shouldSetTitelOfDashboard() {
        // given
        given(messageSource.getMessage(eq(DASHBOARD_TITLE_KEY), any(), any())).willReturn("<title>");

        // when
        DashboardResponse result = converter.convert(new FederalMinistries(List.of()), Locale.GERMAN);

        // then
        assertThat(result.title()).isEqualTo("<title>");
    }

    private Superior givenSuperiorWithNumberOfPublishedDataSets(int numberOfPublishedDataSets) {
        return new Superior("<superior-name>", new FederalMinistryStatistic("<superior-name>",
                                                                            numberOfPublishedDataSets));
    }

    private Map<String, FederalMinistryStatistic> givenSubordinateWithNumberOfPublishedDataSets(int numberOfPublishedDataSets) {
        return Map.of("<subordinate-name>", new FederalMinistryStatistic("<subordinate-name>",
                                                                         numberOfPublishedDataSets));
    }

    private FederalMinistries givenFederalMinistries(Superior superior, Map<String, FederalMinistryStatistic> subordinates) {
        return new FederalMinistries(List.of(new Department(superior, subordinates)));
    }
}