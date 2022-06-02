package lx.krmr.dashboard;

import lx.krmr.dashboard.adapter.out.govdata.GovDataOrganisationWhitelistConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(GovDataOrganisationWhitelistConfig.class)
@SpringBootApplication
public class FederalMinistryDashboard {

	public static void main(String[] args) {
		SpringApplication.run(FederalMinistryDashboard.class, args);
	}

}
