package com.ndrewcoding.apigateway.configuration;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class OpenApiConfiguration {

    public List<GroupedOpenApi> apis() {
        return new ArrayList<>();
    }

}
