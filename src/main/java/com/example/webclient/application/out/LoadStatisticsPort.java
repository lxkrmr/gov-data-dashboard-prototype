package com.example.webclient.application.out;

import com.example.webclient.domain.Statistic;
import reactor.core.publisher.Mono;

import java.util.List;

public interface LoadStatisticsPort {

    Mono<List<Statistic>> load();
}
