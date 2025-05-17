package com.postech.saboresconectados.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class OpenApiConfig {

    @Bean
    public OpenAPI saboresConectados() {
        return new OpenAPI()
                .info(
                        new Info()
                        .title("Sabores Conectados API")
                        .description("Documentação dos endpoints desenvolvidos para o Tech Challenge.")
                        .version("v0.0.1")
                        .license(new License().url("https://github.com/joao-pedro213/sabores-conectados")));
    }

}
