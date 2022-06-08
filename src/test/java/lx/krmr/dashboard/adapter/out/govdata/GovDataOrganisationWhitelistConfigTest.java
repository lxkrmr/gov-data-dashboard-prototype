package lx.krmr.dashboard.adapter.out.govdata;

import lx.krmr.dashboard.adapter.out.govdata.model.types.GovDataOrganization;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GovDataOrganisationWhitelistConfigTest {

    @Test
    void shouldRecognizeOrganisationAsWhitelisted() {
        // given
        String organizationName = "<whitelisted-organization>";
        GovDataOrganisationWhitelistConfig config = new GovDataOrganisationWhitelistConfig(List.of(organizationName));
        GovDataOrganization organization = new GovDataOrganization(organizationName,
                                                                   0);

        // when
        boolean result = config.isWhitelisted(organization);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void shouldRecognizeOrganisationAsNotWhitelisted() {
        // given
        GovDataOrganisationWhitelistConfig config = new GovDataOrganisationWhitelistConfig(List.of("<whitelisted-organization>"));
        GovDataOrganization organization = new GovDataOrganization("<not-whitelisted-organization>",
                                                                   0);

        // when
        boolean result = config.isWhitelisted(organization);

        // then
        assertThat(result).isFalse();
    }
}