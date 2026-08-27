package br.com.avaliacao.api_telemetria.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Classe de configuração global de API de Documentação (Swagger)
 * */

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Telemetria de Motores Industriais")
                        .version("v1")
                        .description("REST API para monitoramento, ingestão de dados e alertas de motores fabris.")
                        .contact(new Contact()
                                .name("Suporte Técnico")
                                .email("suporte@ctw.com")));
    }
}
