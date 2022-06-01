package com.example.webclient.application.port.in;

import com.example.webclient.domain.FederalMinistryDepartments;
import reactor.core.publisher.Mono;

public interface ShowFederalMinistryDashboardUseCase {

    Mono<FederalMinistryDepartments> show();
}
