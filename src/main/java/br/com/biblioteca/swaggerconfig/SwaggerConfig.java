package br.com.biblioteca.swaggerconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

import java.util.Arrays;

@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API Biblioteca")
                .version("1.0")
                .description("Documentação da API de gerenciamento de livros e reservas"))
            .servers(Arrays.asList(
                new Server().url("http://localhost:8080")
            ));
    }
}
