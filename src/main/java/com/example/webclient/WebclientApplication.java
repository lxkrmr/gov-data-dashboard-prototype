package com.example.webclient;

import com.example.webclient.adapter.out.govdata.GovDataOrganisationWhitelistConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(GovDataOrganisationWhitelistConfig.class)
@SpringBootApplication
public class WebclientApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebclientApplication.class, args);
	}

}
