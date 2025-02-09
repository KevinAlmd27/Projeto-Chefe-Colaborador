package com.dev.kevin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    // Classe de configuração CORS
    @Configuration
    public static class WebConfig implements WebMvcConfigurer {

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            // Configurando a permissão para o frontend (localhost:3000)
            registry.addMapping("/**")
                    .allowedOrigins("http://localhost:8080") // Altere se o seu frontend estiver em outra URL
                    .allowedMethods("GET", "POST", "PUT", "DELETE"); // Métodos permitidos
        }
    }
}
