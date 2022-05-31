package com.example.webclient.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.List;

@ConstructorBinding
@ConfigurationProperties(prefix = "federal-ministries")
public record FederalMinistryWhitelistConfig(List<String> whitelist) {
    public boolean isWhitelisted(Organization organization) {
        return whitelist.contains(organization.name());
    }
}
