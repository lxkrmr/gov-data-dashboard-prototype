package lx.krmr.dashboard.adapter.in.web;

import lx.krmr.dashboard.application.port.in.LoadFederalMinistriesUseCase;
import lx.krmr.dashboard.domain.FederalMinistries;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public record FederalMinistriesController(LoadFederalMinistriesUseCase loadFederalMinistriesUseCase) {

    @GetMapping("/api/v1/federal-ministries")
    public Mono<FederalMinistries> federalMinistries() {
        return loadFederalMinistriesUseCase.load();
    }
}
