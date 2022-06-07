package lx.krmr.dashboard.adapter.in.web;

import lx.krmr.dashboard.adapter.out.govdata.GovDataClient;
import lx.krmr.dashboard.adapter.out.govdata.model.types.GovDataResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public record OrganizationsController(GovDataClient govDataClient) {

    @GetMapping("/api/v1/organizations")
    public Mono<GovDataResponse> organizations() {
        return govDataClient.getOrganizations();
    }
}
