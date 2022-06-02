package com.example.webclient.application;

import com.example.webclient.application.port.in.LoadFederalMinistriesUseCase;
import com.example.webclient.application.port.out.LoadFederalMinistryStatisticPort;
import com.example.webclient.domain.FederalMinistries;
import com.example.webclient.domain.FederalMinistryStatistic;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class LoadFederalMinistriesService implements LoadFederalMinistriesUseCase {

    private final LoadFederalMinistryStatisticPort loadFederalMinistryStatisticPort;

    public LoadFederalMinistriesService(LoadFederalMinistryStatisticPort loadFederalMinistryStatisticPort) {
        this.loadFederalMinistryStatisticPort = loadFederalMinistryStatisticPort;
    }

    @Override
    public Mono<FederalMinistries> load() {
        // get the domain object
        Mono<List<FederalMinistryStatistic>> federalMinistryStatisticsMono = loadFederalMinistryStatisticPort.load();

        // perform the command
        return federalMinistryStatisticsMono.map(FederalMinistries::create);
    }
}
