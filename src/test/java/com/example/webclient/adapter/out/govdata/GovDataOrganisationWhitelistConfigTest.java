package com.example.webclient.adapter.out.govdata;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GovDataOrganisationWhitelistConfigTest {

    @Test
    void shouldRecognizeOrganisationAsWhitelisted() {
        // given
        String organizationName = "<whitelisted-organization>";
        GovDataOrganisationWhitelistConfig config = new GovDataOrganisationWhitelistConfig(List.of(organizationName));
        GovDataOrganization organization = new GovDataOrganization("<any>",
                                                                   organizationName,
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
        GovDataOrganization organization = new GovDataOrganization("<any>",
                                                                   "<not-whitelisted-organization>",
                                                                   0);

        // when
        boolean result = config.isWhitelisted(organization);

        // then
        assertThat(result).isFalse();
    }
}