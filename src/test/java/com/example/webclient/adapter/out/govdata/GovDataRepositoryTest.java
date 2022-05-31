package com.example.webclient.adapter.out.govdata;

import com.example.webclient.domain.Organization;
import com.example.webclient.domain.Organizations;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class GovDataRepositoryTest {

    private static MockWebServer mockWebServer;
    private GovDataRepository govDataRepository;

    @BeforeAll
    static void beforeAll() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    static void afterAll() throws IOException {
        mockWebServer.shutdown();
    }

    @BeforeEach
    void setUp() {
        govDataRepository = new GovDataRepository(WebClient.builder(),
                                                  mockWebServer.url("/")
                                                               .url()
                                                               .toString());
    }

    @Test
    void makesTheCorrectRequest() throws InterruptedException {
        // given
        mockWebServer.enqueue(new MockResponse().setResponseCode(200)
                                                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                                .setBody(givenExampleResponse()));

        // when
        govDataRepository.load()
                         .block();
        RecordedRequest result = mockWebServer.takeRequest();

        // then
        assertThat(result.getMethod()).isEqualTo("GET");
        assertThat(result.getPath()).isEqualTo("/organization_list?all_fields=true&include_dataset=true");
    }

    @Test
    void handlesTheResponse() {
        // given
        mockWebServer.enqueue(new MockResponse().setResponseCode(200)
                                                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                                .setBody(givenExampleResponse()));

        // when
        Mono<Organizations> result = govDataRepository.load();

        // then
        StepVerifier.create(result)
                    .assertNext(o -> assertThat(o.organizations()).containsExactly(new Organization("Auswärtiges Amt",
                                                                                                    "auswaertiges-amt",
                                                                                                    7),
                                                                                   new Organization("Berlin Open Data",
                                                                                                    "berlin-open-data",
                                                                                                    1291
                                                                                   )))
                    .verifyComplete();
    }

    @Test
    void exchangeClientError() {
        // given
        mockWebServer.enqueue(new MockResponse().setResponseCode(400));

        // when
        Mono<Organizations> result = govDataRepository.load();

        // then
        StepVerifier.create(result)
                    .expectErrorSatisfies(throwable -> assertThat(throwable).isExactlyInstanceOf(WebClientResponseException.BadRequest.class)
                                                                            .hasMessageStartingWith("400 Bad Request"))
                    .verify();

    }

    @Test
    void exchangeServerError() {
        // given
        mockWebServer.enqueue(new MockResponse().setResponseCode(500));

        // when
        Mono<Organizations> result = govDataRepository.load();

        // then
        StepVerifier.create(result)
                    .expectErrorSatisfies(throwable -> assertThat(throwable).isExactlyInstanceOf(WebClientResponseException.InternalServerError.class)
                                                                            .hasMessageStartingWith("500 Internal Server Error"))
                    .verify();

    }

    private String givenExampleResponse() {
        return """
                {
                  "help": "https://ckan.govdata.de/api/3/action/help_show?name=organization_list",
                  "success": true,
                  "result": [
                    {
                      "approval_status": "approved",
                      "created": "2020-06-11T10:43:29.894113",
                      "description": "",
                      "display_name": "Auswärtiges Amt",
                      "id": "c6f6f6ba-93ab-40ed-8dcf-62d1b678260f",
                      "image_display_url": "",
                      "image_url": "",
                      "is_organization": true,
                      "name": "auswaertiges-amt",
                      "num_followers": 0,
                      "package_count": 7,
                      "state": "active",
                      "title": "Auswärtiges Amt",
                      "type": "organization"
                    },
                    {
                      "approval_status": "approved",
                      "created": "2016-01-11T09:32:40.609980",
                      "description": "Berlin Open Data",
                      "display_name": "Berlin Open Data",
                      "id": "23d695da-6d4e-497f-b36b-3a388949c729",
                      "image_display_url": "",
                      "image_url": "",
                      "is_organization": true,
                      "name": "berlin-open-data",
                      "num_followers": 0,
                      "package_count": 1291,
                      "state": "active",
                      "title": "Berlin Open Data",
                      "type": "organization"
                    }
                  ]
                }
                """;
    }
}