package com.bcp.gestion_cuentas.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API REST - Gestión de Cuentas BCP")
                        .version("1.0.0")
                        .description("Sistema Backend de gestión de clientes, cuentas bancarias y transacciones financieras.")
                        .contact(new Contact()
                                .name("Christopher Neciosup")
                                .email("christopher@bcp.pe")));
    }
}
