package lx.krmr.dashboard.adapter.out.govdata.model.types;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public record GovDataResponse(@JsonAlias("result") List<GovDataOrganization> govDataOrganizations) {
}
