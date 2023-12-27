package com.botree.sfaweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring Boot main class.
 * @author vinodkumar.a
 */
@SpringBootApplication(scanBasePackages = "com.botree")
@EnableScheduling
public class SFAWebApplication {

    /**
     * Main method.
     * @param args arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(SFAWebApplication.class, args);
    }

    /**
     * This method is used to configure CORS.
     * @return WebMvcConfigurer
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new MyWebMvcConfigurer();
    }

    /**
     * Web configuration class.
     */
    private static class MyWebMvcConfigurer implements WebMvcConfigurer {
        @Override
        public void addCorsMappings(final CorsRegistry registry) {
            registry.addMapping("/sfaweb/**").allowedOrigins("*").allowedMethods("POST", "GET", "OPTIONS")
                    .allowedHeaders("Content-Type", "X-Auth-Token", "X-Login-Code", "Cache-Control", "Pragma",
                            "Expires")
                    .exposedHeaders("Content-Type", "X-Auth-Token", "X-Login-Code", "Cache-Control", "Pragma",
                            "Expires");
        }
    }
}
