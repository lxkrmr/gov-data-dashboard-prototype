package com.example.webclient.adapter.out.govdata;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * FYI:
 * It seems that because I am using a application.yaml that this aaproach does not work
 * https://www.baeldung.com/spring-boot-testing-configurationproperties
 * Instead I followed this advice by using @SpringBootTest
 * https://stackoverflow.com/questions/70807604/testing-configurationproperties-annotation-without-loading-spring-context
 */
@SpringBootTest
class GovDataOrganisationWhitelistConfigApplicationTest {

    @Autowired
    private GovDataOrganisationWhitelistConfig config;

    @Test
    void shouldLoadWhitelist() {
        assertThat(config.whitelist()).containsExactlyInAnyOrder("auswaertiges-amt",
                                                                 "badv",
                                                                 "bafa",
                                                                 "bam",
                                                                 "baua",
                                                                 "bbsr",
                                                                 "bmas",
                                                                 "bmel",
                                                                 "bmf",
                                                                 "bmfsfj",
                                                                 "bmi",
                                                                 "bmvg",
                                                                 "bmwi",
                                                                 "bmz",
                                                                 "bsa",
                                                                 "bundesamt-fur-justiz",
                                                                 "bundesministerium-fur-bildung-und-forschung",
                                                                 "bva",
                                                                 "bvl",
                                                                 "bzst",
                                                                 "dpma",
                                                                 "itzbund",
                                                                 "mcloud",
                                                                 "mri",
                                                                 "statistisches-bundesamt",
                                                                 "zoll");
    }
}