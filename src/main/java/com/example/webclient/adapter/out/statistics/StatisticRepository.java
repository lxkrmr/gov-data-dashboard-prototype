package com.example.webclient.adapter.out.statistics;

import com.example.webclient.adapter.out.govdata.GovDataClient;
import com.example.webclient.application.out.LoadStatisticsPort;
import com.example.webclient.domain.Statistic;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public class StatisticRepository implements LoadStatisticsPort {

    private final GovDataClient govDataClient;

    public StatisticRepository(GovDataClient govDataClient) {
        this.govDataClient = govDataClient;
    }

    @Override
    public Mono<List<Statistic>> load() {
        return govDataClient.getOrganizations()
                            .map(response -> response.organizations()
                                                     .stream()
                                                     .map(organization -> new Statistic(organization.displayName(),
                                                                                        organization.name(),
                                                                                        organization.packageCount())
                                                     )
                                                     .toList()
                            );
    }
}
