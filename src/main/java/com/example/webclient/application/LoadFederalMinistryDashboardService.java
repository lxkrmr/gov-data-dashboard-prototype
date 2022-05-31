package com.example.webclient.application;

import com.example.webclient.application.in.ShowFederalMinistryDashboardUseCase;
import com.example.webclient.application.out.LoadOrganizationsPort;
import com.example.webclient.domain.FederalMinistryDashboard;
import com.example.webclient.domain.FederalMinistryStatistic;
import com.example.webclient.domain.FederalMinistryWhitelistConfig;
import com.example.webclient.domain.Organizations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;

@Service
public class LoadFederalMinistryDashboardService implements ShowFederalMinistryDashboardUseCase {

    private final LoadOrganizationsPort loadOrganizationsPort;

    private final FederalMinistryWhitelistConfig federalMinistryWhitelistConfig;

    public LoadFederalMinistryDashboardService(LoadOrganizationsPort loadOrganizationsPort, FederalMinistryWhitelistConfig federalMinistryWhitelistConfig) {
        this.loadOrganizationsPort = loadOrganizationsPort;
        this.federalMinistryWhitelistConfig = federalMinistryWhitelistConfig;
    }

    @Override
    public Mono<FederalMinistryDashboard> show() {
        // load the domain object
        // load federal ministries instead of organisations
        Mono<Organizations> organizationsMono = loadOrganizationsPort.load();

        // perform the command
        // persist the changes
        // TODO group federal ministries as in departments
        return organizationsMono.map(organizations -> {
                                         List<FederalMinistryStatistic> federalMinistryStatistics = organizations.organizations()
                                                                                                                 .stream()
                                                                                                                 .filter(federalMinistryWhitelistConfig::isWhitelisted)
                                                                                                                 .map(FederalMinistryStatistic::new)
                                                                                                                 // sort should be done in the controller
                                                                                                                 .sorted(Comparator.comparingInt(one -> one.numberOfPublishedDataSets()
                                                                                                                                                           .value()))
                                                                                                                 .toList();
                                         return new FederalMinistryDashboard(federalMinistryStatistics);
                                     }
        );
    }
}
