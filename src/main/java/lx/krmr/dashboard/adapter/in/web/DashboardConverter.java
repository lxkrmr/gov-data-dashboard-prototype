package lx.krmr.dashboard.adapter.in.web;

import lx.krmr.dashboard.domain.FederalMinistries;
import lx.krmr.dashboard.domain.Superior;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DashboardConverter {

    public DashboardResponse convert(FederalMinistries federalMinistries) {
        federalMinistries.departments()
                         .stream()
                         .map(department -> {
                             String label = toLabel(department.superior());
                             return label;
                         });
        return new DashboardResponse("Title",
                                     List.of("foo", "bar"),
                                     List.of(21, 42));
    }

    private String toLabel(Superior superior) {

        return null;
    }
}
