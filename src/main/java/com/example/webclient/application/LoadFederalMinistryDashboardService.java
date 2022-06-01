package com.example.webclient.application;

import com.example.webclient.application.port.in.ShowFederalMinistryDashboardUseCase;
import com.example.webclient.application.port.out.LoadFederalMinistryStatisticPort;
import com.example.webclient.domain.FederalMinistryDepartments;
import com.example.webclient.domain.FederalMinistryStatistic;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class LoadFederalMinistryDashboardService implements ShowFederalMinistryDashboardUseCase {

    private final LoadFederalMinistryStatisticPort loadFederalMinistryStatisticPort;


    public LoadFederalMinistryDashboardService(LoadFederalMinistryStatisticPort loadFederalMinistryStatisticPort) {
        this.loadFederalMinistryStatisticPort = loadFederalMinistryStatisticPort;
    }

    @Override
    public Mono<FederalMinistryDepartments> show() {
        // get the domain object
        Mono<List<FederalMinistryStatistic>> federalMinistryStatisticsMono = loadFederalMinistryStatisticPort.load();

        // perform the command
        return federalMinistryStatisticsMono.map(FederalMinistryDepartments::new);
        // persist the changes - not needed here
    }
}
