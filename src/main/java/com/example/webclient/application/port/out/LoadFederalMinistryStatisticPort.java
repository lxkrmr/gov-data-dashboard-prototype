package com.example.webclient.application.port.out;

import com.example.webclient.domain.FederalMinistryStatistic;
import reactor.core.publisher.Mono;

import java.util.List;

public interface LoadFederalMinistryStatisticPort {

    Mono<List<FederalMinistryStatistic>> load();
}
