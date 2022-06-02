package lx.krmr.dashboard.adapter.in.web;

import lx.krmr.dashboard.domain.FederalMinistries;
import lx.krmr.dashboard.domain.FederalMinistryStatistic;
import lx.krmr.dashboard.domain.Superior;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class DashboardConverter {

    public static final String DASHBOARD_TITLE = "Number of published data sets on govdata.de by federal ministry";

    public DashboardResponse convert(FederalMinistries federalMinistries) {
        List<DashboardLabelValuePair> dashboardLabelValuePairs = federalMinistries.departments()
                                                                                  .stream()
                                                                                  .map(department -> {
                                                                                      String label = toLabel(department.superior());
                                                                                      int value = toValue(department.superior(), department.subordinates());
                                                                                      return new DashboardLabelValuePair(label, value);
                                                                                  })
                                                                                  .sorted(Comparator.comparingInt(DashboardLabelValuePair::value))
                                                                                  .toList();
        return new DashboardResponse(DASHBOARD_TITLE,
                                     dashboardLabelValuePairs.stream()
                                                             .map(DashboardLabelValuePair::label)
                                                             .toList(),
                                     dashboardLabelValuePairs.stream()
                                                             .map(DashboardLabelValuePair::value)
                                                             .toList());
    }

    private String toLabel(Superior superior) {
        return superior.maybeStatistics()
                       .map(FederalMinistryStatistic::displayName)
                       .orElse(superior.name());
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
