package com.example.webclient.adapter.in.web;

import com.example.webclient.application.port.in.LoadFederalMinistriesUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class DashboardController {

    private final LoadFederalMinistriesUseCase loadFederalMinistriesUseCase;

    public DashboardController(LoadFederalMinistriesUseCase loadFederalMinistriesUseCase) {
        this.loadFederalMinistriesUseCase = loadFederalMinistriesUseCase;
    }

    @GetMapping("/dashboard")
    public Mono<DashboardResponse> federalMinistries() {
        return loadFederalMinistriesUseCase.load()
                .map(DashboardResponse::create);
    }
}
