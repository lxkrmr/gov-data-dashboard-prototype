package lx.krmr.dashboard.adapter.out.govdata.model.types;

import com.fasterxml.jackson.annotation.JsonAlias;

public record GovDataOrganization(String name,
        @JsonAlias("package_count") Integer packageCount
) { }
