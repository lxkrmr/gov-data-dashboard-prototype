package com.example.webclient.application.out;

import com.example.webclient.domain.Organizations;
import reactor.core.publisher.Mono;

public interface LoadOrganizationsPort {

    Mono<Organizations> load();
}
