package com.example.webclient.adapter.in.web;

import com.example.webclient.application.port.in.LoadFederalMinistriesUseCase;
import com.example.webclient.domain.FederalMinistries;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = DashboardController.class)
class DashboardControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private LoadFederalMinistriesUseCase loadFederalMinistriesUseCase;

    @MockBean
    private DashboardConverter converter;

    @Test
    void shouldReturnDashboardResponse() {
        // given
        FederalMinistries federalMinistries = FederalMinistries.create(Collections.emptyList());
        DashboardResponse dashboardResponse = new DashboardResponse("title",
                                                                    List.of("label1", "label2"),
                                                                    List.of(21, 42));
        given(loadFederalMinistriesUseCase.load()).willReturn(Mono.just(federalMinistries));
        given(converter.convert(federalMinistries)).willReturn(dashboardResponse);

        // when
        WebTestClient.ResponseSpec result = webTestClient.get()
                                                         .uri("/dashboard")
                                                         .exchange();

        // then
        result.expectStatus()
              .isOk()
              .expectBody()
              .jsonPath("$.title")
              .isEqualTo("title")
              .jsonPath("$.labels[0]")
              .isEqualTo("label1")
              .jsonPath("$.labels[1]")
              .isEqualTo("label2")
              .jsonPath("$.data[0]")
              .isEqualTo(21)
              .jsonPath("$.data[1]")
              .isEqualTo(42);
    }
}