package com.example.webclient.application.port.in;

import com.example.webclient.domain.FederalMinistries;
import reactor.core.publisher.Mono;

public interface LoadFederalMinistriesUseCase {

    Mono<FederalMinistries> show();
}
