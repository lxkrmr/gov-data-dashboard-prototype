package lx.krmr.dashboard.adapter.out.govdata;

import lx.krmr.dashboard.application.port.out.LoadFederalMinistryStatisticPort;
import lx.krmr.dashboard.domain.FederalMinistryStatistic;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public class GovDataRepository implements LoadFederalMinistryStatisticPort {

    private final GovDataClient govDataClient;

    private final GovDataOrganisationWhitelistConfig govDataOrganisationWhitelistConfig;

    public GovDataRepository(GovDataClient govDataClient, GovDataOrganisationWhitelistConfig govDataOrganisationWhitelistConfig) {
        this.govDataClient = govDataClient;
        this.govDataOrganisationWhitelistConfig = govDataOrganisationWhitelistConfig;
    }

    @Override
    public Mono<List<FederalMinistryStatistic>> load() {
        return govDataClient.getOrganizations()
                            .map(response -> response.govDataOrganizations()
                                                     .stream()
                                                     .filter(govDataOrganisationWhitelistConfig::isWhitelisted)
                                                     .map(organization -> new FederalMinistryStatistic(organization.displayName(),
                                                                                                       organization.name(),
                                                                                                       organization.packageCount())
                                                     )
                                                     .toList()
                            );
    }
}
