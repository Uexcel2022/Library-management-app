package com.uexcel.library.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@AllArgsConstructor
public class SwaggerConfig {
    private final Environment env;
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Wisdom Spring Library")
                        .description("This is Wisdom Spring Library Management System.")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("SpringBoot uexcel Documentation")
                        .url("https://springboot.uexcel.github.org/docs"))
                .externalDocs(new ExternalDocumentation()
                .description("Logout")
                        .url(env.getProperty("LOGOUT_PATH")));

    }
}
