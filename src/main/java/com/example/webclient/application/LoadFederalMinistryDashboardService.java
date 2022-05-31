package com.example.webclient.application;

import com.example.webclient.application.in.ShowFederalMinistryDashboardUseCase;
import com.example.webclient.application.out.LoadStatisticsPort;
import com.example.webclient.domain.FederalMinistryDashboard;
import com.example.webclient.domain.FederalMinistryWhitelistConfig;
import com.example.webclient.domain.Statistic;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class LoadFederalMinistryDashboardService implements ShowFederalMinistryDashboardUseCase {

    private final LoadStatisticsPort loadStatisticsPort;

    private final FederalMinistryWhitelistConfig federalMinistryWhitelistConfig;

    public LoadFederalMinistryDashboardService(LoadStatisticsPort loadStatisticsPort, FederalMinistryWhitelistConfig federalMinistryWhitelistConfig) {
        this.loadStatisticsPort = loadStatisticsPort;
        this.federalMinistryWhitelistConfig = federalMinistryWhitelistConfig;
    }

    @Override
    public Mono<FederalMinistryDashboard> show() {
        // get the domain object
        Mono<List<Statistic>> federalMinistryStatisticsMono = loadStatisticsPort.load();

        // perform the command
        return federalMinistryStatisticsMono.map(statistics -> {
            List<Statistic> federalMinistryStatistics = statistics.stream()
                                                                  .filter(federalMinistryWhitelistConfig::isWhitelisted)
                                                                  .toList();
            return new FederalMinistryDashboard(federalMinistryStatistics);
        });
        // persist the changes - not needed here
    }
}
