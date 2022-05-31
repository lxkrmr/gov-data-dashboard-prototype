package com.example.webclient.adapter.in.web;

import com.example.webclient.application.in.ShowFederalMinistryDashboardUseCase;
import com.example.webclient.domain.FederalMinistryDashboard;
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
    public Mono<FederalMinistryDashboard> loadDashboard() {
        return showFederalMinistryDashboardUseCase.show();
    }
}
