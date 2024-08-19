package br.com.biblioteca.webconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
		.allowedOrigins("http://localhost:4200") // Permite requisições do front-end Angular
		.allowedMethods("*") // Permite os todos os métodos HTTP.
		.allowedHeaders("*"); // Permite todos os cabeçalhos
	}
}
