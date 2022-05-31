package com.example.webclient.adapter.out.govdata;

import com.example.webclient.application.out.LoadOrganizationsPort;
import com.example.webclient.domain.Organizations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

// TODO: This should be split into a repo and a client
// where the repo should convert the clients response
@Service
public class GovDataRepository implements LoadOrganizationsPort {

    private final WebClient webClient;

    public GovDataRepository(WebClient.Builder webClientBuilder,
                             @Value("${govdata.baseurl}") String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl)
                                         .build();
    }

    @Override
    public Mono<Organizations> load() {
        return webClient.get()
                        .uri(uriBuilder -> uriBuilder.path("/organization_list")
                                                     .queryParam("all_fields", "true")
                                                     .queryParam("include_dataset", "true")
                                                     .build()
                        )
                        .retrieve()
                        .bodyToMono(Organizations.class);
    }
}