package com.example.webclient.adapter.in.web;

import com.example.webclient.adapter.out.govdata.GovDataRepository;
import com.example.webclient.domain.Organizations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class OrganizationsController {

    private GovDataRepository govDataRepository;

    public OrganizationsController(GovDataRepository govDataRepository) {
        this.govDataRepository = govDataRepository;
    }

    @GetMapping("/api/v1/organizations")
    public Mono<Organizations> organizations() {
        return govDataRepository.load();
    }
}
