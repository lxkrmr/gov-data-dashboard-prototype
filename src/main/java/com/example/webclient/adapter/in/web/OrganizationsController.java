package com.example.webclient.adapter.in.web;

import com.example.webclient.adapter.out.govdata.GovDataClient;
import com.example.webclient.adapter.out.govdata.GovDataResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class OrganizationsController {

    private GovDataClient govDataClient;

    public OrganizationsController(GovDataClient govDataClient) {
        this.govDataClient = govDataClient;
    }

    @GetMapping("/api/v1/organizations")
    public Mono<GovDataResponse> organizations() {
        return govDataClient.getOrganizations();
    }
}
