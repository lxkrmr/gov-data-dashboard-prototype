package lx.krmr.dashboard.adapter.in.web;

import lx.krmr.dashboard.application.port.in.LoadFederalMinistriesUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class DashboardController {

    private final LoadFederalMinistriesUseCase loadFederalMinistriesUseCase;

    private final DashboardConverter converter;

    public DashboardController(LoadFederalMinistriesUseCase loadFederalMinistriesUseCase, DashboardConverter converter) {
        this.loadFederalMinistriesUseCase = loadFederalMinistriesUseCase;
        this.converter = converter;
    }

    @GetMapping("/dashboard")
    public Mono<DashboardResponse> federalMinistries() {
        return loadFederalMinistriesUseCase.load()
                                           .map(converter::convert);
    }
}
