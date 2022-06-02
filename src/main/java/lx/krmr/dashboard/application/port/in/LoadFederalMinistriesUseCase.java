package lx.krmr.dashboard.application.port.in;

import lx.krmr.dashboard.domain.FederalMinistries;
import reactor.core.publisher.Mono;

public interface LoadFederalMinistriesUseCase {

    Mono<FederalMinistries> load();
}
