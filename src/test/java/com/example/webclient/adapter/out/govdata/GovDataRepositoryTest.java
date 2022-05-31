package com.example.webclient.adapter.out.govdata;

import com.example.webclient.domain.Organization;
import com.example.webclient.domain.Organizations;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GovDataRepositoryTest {

    private static ObjectMapper objectMapper;
    private static MockWebServer mockWebServer;
    private GovDataRepository govDataRepository;

    @BeforeAll
    static void beforeAll() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        objectMapper = new ObjectMapper();
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
    void makesTheCorrectRequest() throws JsonProcessingException, InterruptedException {
        // given
        Organizations organizations = new Organizations(Collections.emptyList());

        mockWebServer.enqueue(new MockResponse().setResponseCode(200)
                                                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                                .setBody(objectMapper.writeValueAsString(organizations)));

        // when
        govDataRepository.load().block();
        RecordedRequest result = mockWebServer.takeRequest();

        // then
        assertThat(result.getMethod()).isEqualTo("GET");
        assertThat(result.getPath()).isEqualTo("/organization_list?all_fields=true&include_dataset=true");
    }

    @Test
    void handlesTheResponse() throws JsonProcessingException {
        // given
        Organization organization = new Organization("<display-name>",
                                                     "<name>",
                                                     42);
        Organizations organizations = new Organizations(List.of(organization));

        mockWebServer.enqueue(new MockResponse().setResponseCode(200)
                                                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                                .setBody(objectMapper.writeValueAsString(organizations)));

        // when
        Mono<Organizations> result = govDataRepository.load();

        // then
        StepVerifier.create(result)
                    .assertNext(o -> assertThat(o.organizations()).containsExactly(organization))
                    .verifyComplete();
    }
}