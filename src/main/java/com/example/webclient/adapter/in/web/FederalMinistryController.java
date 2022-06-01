package com.example.webclient.adapter.in.web;

import com.example.webclient.application.port.in.ShowFederalMinistryDashboardUseCase;
import com.example.webclient.domain.FederalMinistryDepartments;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FederalMinistryController {

    private final ShowFederalMinistryDashboardUseCase showFederalMinistryDashboardUseCase;

    public FederalMinistryController(ShowFederalMinistryDashboardUseCase showFederalMinistryDashboardUseCase) {
        this.showFederalMinistryDashboardUseCase = showFederalMinistryDashboardUseCase;
    }

    @GetMapping("/api/v1/federal-ministry-dashboard")
    public Mono<FederalMinistryDepartments> loadDashboard() {
        return showFederalMinistryDashboardUseCase.show();
    }
}
