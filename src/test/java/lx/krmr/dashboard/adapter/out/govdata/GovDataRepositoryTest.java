package lx.krmr.dashboard.adapter.out.govdata;

import lx.krmr.dashboard.adapter.out.govdata.model.types.GovDataOrganization;
import lx.krmr.dashboard.adapter.out.govdata.model.types.GovDataResponse;
import lx.krmr.dashboard.domain.model.types.FederalMinistryStatistic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GovDataRepositoryTest {

    @Mock
    private GovDataClient govDataClientMock;

    private GovDataRepository govDataRepository;

    // FYI: Sadly I can't mock a record as it is final
    @BeforeEach
    void setUp() {
        GovDataOrganisationWhitelistConfig config = new GovDataOrganisationWhitelistConfig(List.of("<first-whitelisted-name>",
                                                                                                   "<second-whitelisted-name>"));
        govDataRepository = new GovDataRepository(govDataClientMock, config);
    }

    @Test
    void shouldLoadFederalMinistryStatistics() {
        // given
        GovDataOrganization firstGovDataOrganization = new GovDataOrganization("<first-whitelisted-name>",
                                                                               1);
        GovDataOrganization secondGovDataOrganization = new GovDataOrganization("<second-whitelisted-name>",
                                                                                2);
        GovDataResponse govDataResponse = new GovDataResponse(List.of(firstGovDataOrganization, secondGovDataOrganization));
        given(govDataClientMock.getOrganizations()).willReturn(Mono.just(govDataResponse));

        // when
        Mono<List<FederalMinistryStatistic>> result = govDataRepository.load();

        // then
        FederalMinistryStatistic firstFederalMinistryStatistic = new FederalMinistryStatistic(firstGovDataOrganization.name(),
                                                                                              firstGovDataOrganization.packageCount());
        FederalMinistryStatistic secondFederalMinistryStatistic = new FederalMinistryStatistic(secondGovDataOrganization.name(),
                                                                                               secondGovDataOrganization.packageCount());
        StepVerifier.create(result)
                    .assertNext(statistics -> assertThat(statistics).containsExactly(firstFederalMinistryStatistic,
                                                                                     secondFederalMinistryStatistic))
                    .verifyComplete();
    }

    @Test
    void shouldNotLoadFederalMinistryStatisticsIfGovDataOrganisationIsNotWhitelisted() {
        // given
        GovDataOrganization govDataOrganization = new GovDataOrganization("<not-whitelisted-name>",
                                                                          1);
        GovDataResponse govDataResponse = new GovDataResponse(List.of(govDataOrganization));
        given(govDataClientMock.getOrganizations()).willReturn(Mono.just(govDataResponse));

        // when
        Mono<List<FederalMinistryStatistic>> result = govDataRepository.load();

        // then
        StepVerifier.create(result)
                    .assertNext(statistics -> assertThat(statistics).isEmpty())
                    .verifyComplete();
    }
}