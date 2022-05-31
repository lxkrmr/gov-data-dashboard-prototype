package com.example.webclient.domain;

import com.example.webclient.util.JsonPropertySourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@ConstructorBinding
@PropertySource(value = "classpath:departments.json", factory = JsonPropertySourceFactory.class)
@ConfigurationProperties
public record Departments(List<Department> departments) {

    public boolean isWhitelisted(Organization organization) {
        return departments().stream()
                            .anyMatch(department -> department.name()
                                                              .equalsIgnoreCase(organization.name())
                                       || department.subordinates()
                                                    .stream()
                                                    .map(Department::name)
                                                    .toList()
                                                    .contains(organization.name()));
    }
}
