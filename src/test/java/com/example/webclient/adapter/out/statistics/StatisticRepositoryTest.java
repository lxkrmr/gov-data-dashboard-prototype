package com.example.webclient.adapter.out.statistics;

import com.example.webclient.adapter.out.govdata.GovDataClient;
import com.example.webclient.adapter.out.govdata.GovDataResponse;
import com.example.webclient.adapter.out.govdata.Organization;
import com.example.webclient.domain.Statistic;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class StatisticRepositoryTest {

    @Mock
    private GovDataClient govDataClientMock;

    @InjectMocks
    private StatisticRepository federalMinistryStatisticRepository;

    @Test
    void shouldLoadStatistics() {
        // given
        Organization firstOrganization = new Organization("<first-display-name>",
                                                          "<first-name>",
                                                          1);
        Organization secondOrganization = new Organization("<second-display-name>",
                                                           "<second-name>",
                                                           2);
        GovDataResponse govDataResponse = new GovDataResponse(List.of(firstOrganization, secondOrganization));
        given(govDataClientMock.getOrganizations()).willReturn(Mono.just(govDataResponse));

        // when
        Mono<List<Statistic>> result = federalMinistryStatisticRepository.load();

        // then
        Statistic firstStatistic = new Statistic(firstOrganization.displayName(),
                                                 firstOrganization.name(),
                                                 firstOrganization.packageCount());
        Statistic secondStatistic = new Statistic(secondOrganization.displayName(),
                                                  secondOrganization.name(),
                                                  secondOrganization.packageCount());
        StepVerifier.create(result)
                    .assertNext(statistics -> assertThat(statistics).containsExactly(firstStatistic,
                                                                                     secondStatistic))
                    .verifyComplete();
    }
}