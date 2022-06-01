package com.example.webclient.adapter.out.govdata;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.List;

@ConstructorBinding
@ConfigurationProperties(prefix = "govdata")
public record GovDataOrganisationWhitelistConfig(List<String> whitelist) {
    public boolean isWhitelisted(GovDataOrganization organization) {
        return whitelist.contains(organization.name());
    }
}
