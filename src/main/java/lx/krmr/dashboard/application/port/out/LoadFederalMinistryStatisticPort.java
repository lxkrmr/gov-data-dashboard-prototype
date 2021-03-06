package lx.krmr.dashboard.application.port.out;

import lx.krmr.dashboard.domain.model.types.FederalMinistryStatistic;
import reactor.core.publisher.Mono;

import java.util.List;

public interface LoadFederalMinistryStatisticPort {

    Mono<List<FederalMinistryStatistic>> load();
}
