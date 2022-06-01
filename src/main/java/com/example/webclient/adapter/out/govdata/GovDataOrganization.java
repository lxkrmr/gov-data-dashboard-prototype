package com.example.webclient.adapter.out.govdata;

import com.fasterxml.jackson.annotation.JsonAlias;

public record GovDataOrganization(
        @JsonAlias("display_name") String displayName,
        String name,
        @JsonAlias("package_count") Integer packageCount
) { }
