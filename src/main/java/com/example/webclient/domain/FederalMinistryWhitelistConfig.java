package com.example.webclient.domain;

import com.example.webclient.adapter.out.govdata.Organization;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.List;

@ConstructorBinding
@ConfigurationProperties(prefix = "federal-ministries")
public record FederalMinistryWhitelistConfig(List<String> whitelist) {
    public boolean isWhitelisted(Statistic statistic) {
        return whitelist.contains(statistic.name());
    }
}
