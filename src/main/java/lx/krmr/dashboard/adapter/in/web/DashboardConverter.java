package lx.krmr.dashboard.adapter.in.web;

import lx.krmr.dashboard.domain.FederalMinistries;
import lx.krmr.dashboard.domain.FederalMinistryStatistic;
import lx.krmr.dashboard.domain.Superior;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.*;

// FYI: Can't be converted to a record as Mockito isn't able to mock a static object
@Component
public class DashboardConverter {

    public static final String DASHBOARD_TITLE_KEY = "title";
    private final MessageSource messageSource;

    public DashboardConverter(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public DashboardResponse convert(FederalMinistries federalMinistries, Locale locale) {
        List<DashboardLabelValuePair> dashboardLabelValuePairs = federalMinistries.departments()
                                                                                  .stream()
                                                                                  .map(department -> {
                                                                                      String label = fromMessageSource(department.superior()
                                                                                                                                 .name(), locale);
                                                                                      int value = toValue(department.superior(), department.subordinates());
                                                                                      return new DashboardLabelValuePair(label, value);
                                                                                  })
                                                                                  .sorted(Comparator.comparingInt(DashboardLabelValuePair::value))
                                                                                  .toList();
        return new DashboardResponse(fromMessageSource(DASHBOARD_TITLE_KEY, locale),
                                     dashboardLabelValuePairs.stream()
                                                             .map(DashboardLabelValuePair::label)
                                                             .toList(),
                                     dashboardLabelValuePairs.stream()
                                                             .map(DashboardLabelValuePair::value)
                                                             .toList());
    }

    private String fromMessageSource(String key, Locale locale) {
        return messageSource.getMessage(key, null, locale);
    }

    private int toValue(Superior superior,
                        Map<String, Optional<FederalMinistryStatistic>> subordinates) {
        Integer superiorNumberOfPublishedDataSets = superior.maybeStatistics()
                                                            .map(FederalMinistryStatistic::numberOfPublishedDataSets)
                                                            .orElse(0);

        Integer subordinatesNumberOfPublishedDataSets = subordinates.values()
                                                                    .stream()
                                                                    .filter(Optional::isPresent)
                                                                    .map(Optional::get)
                                                                    .map(FederalMinistryStatistic::numberOfPublishedDataSets)
                                                                    .reduce(0, Integer::sum);
        return superiorNumberOfPublishedDataSets + subordinatesNumberOfPublishedDataSets;
    }
}
