package com.example.webclient.application.in;

import com.example.webclient.domain.FederalMinistryDashboard;
import reactor.core.publisher.Mono;

public interface ShowFederalMinistryDashboardUseCase {

    Mono<FederalMinistryDashboard> show();
}
