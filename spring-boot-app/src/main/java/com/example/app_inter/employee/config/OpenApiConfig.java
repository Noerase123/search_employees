package com.example.app_inter.employee.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.*;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI employeeSearchOpenAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("Employee Search API")
            .description("Search employees by ID, name, project, skills, and hobbies.")
            .version("v1.0.0")
            .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0")))
        .servers(List.of(
            new Server().url("http://localhost:8080").description("Local")
        ));
  }
}
