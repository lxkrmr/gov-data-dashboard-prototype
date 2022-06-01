package com.example.webclient.adapter.in.web;

import com.example.webclient.application.port.in.LoadFederalMinistriesUseCase;
import com.example.webclient.domain.FederalMinistries;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FederalMinistriesController {

    private final LoadFederalMinistriesUseCase loadFederalMinistriesUseCase;

    public FederalMinistriesController(LoadFederalMinistriesUseCase loadFederalMinistriesUseCase) {
        this.loadFederalMinistriesUseCase = loadFederalMinistriesUseCase;
    }

    @GetMapping("/api/v1/federal-ministries")
    public Mono<FederalMinistries> federalMinistries() {
        return loadFederalMinistriesUseCase.show();
    }
}
