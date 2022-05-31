package com.example.webclient.domain;

public record FederalMinistryStatistic(FederalMinistry federalMinistry,
                                       NumberOfPublishedDataSets numberOfPublishedDataSets) {
    public FederalMinistryStatistic(Organization organization) {
        this(new FederalMinistry(organization.displayName()),
             new NumberOfPublishedDataSets(organization.packageCount()));
    }

}
