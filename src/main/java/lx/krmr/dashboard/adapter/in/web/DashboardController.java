package lx.krmr.dashboard.adapter.in.web;

import lx.krmr.dashboard.adapter.in.web.model.types.DashboardResponse;
import lx.krmr.dashboard.application.port.in.LoadFederalMinistriesUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Locale;

@RestController
public record DashboardController(LoadFederalMinistriesUseCase loadFederalMinistriesUseCase,
                                  DashboardConverter dashboardConverter) {

    @GetMapping("/dashboard")
    public Mono<DashboardResponse> federalMinistries(Locale locale) {
        return loadFederalMinistriesUseCase.load()
                                           .map(federalMinistries -> dashboardConverter.convert(federalMinistries, locale));
    }
}
