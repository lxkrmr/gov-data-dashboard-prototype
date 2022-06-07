package lx.krmr.dashboard.application;

import lx.krmr.dashboard.application.port.in.LoadFederalMinistriesUseCase;
import lx.krmr.dashboard.domain.FederalMinistries;
import lx.krmr.dashboard.application.port.out.LoadFederalMinistryStatisticPort;
import lx.krmr.dashboard.domain.model.types.FederalMinistryStatistic;
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
