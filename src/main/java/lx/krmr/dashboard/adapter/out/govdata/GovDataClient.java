package lx.krmr.dashboard.adapter.out.govdata;

import lx.krmr.dashboard.adapter.out.govdata.model.types.GovDataResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class GovDataClient {

    private final WebClient webClient;

    public GovDataClient(WebClient.Builder webClientBuilder,
                         @Value("${govdata.baseurl}") String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl)
                                         .build();
    }

    public Mono<GovDataResponse> getOrganizations() {
        return webClient.get()
                        .uri(uriBuilder -> uriBuilder.path("/organization_list")
                                                     .queryParam("all_fields", "true")
                                                     .queryParam("include_dataset", "true")
                                                     .build()
                        )
                        .retrieve()
                        .bodyToMono(GovDataResponse.class);
    }
}