package com.botree.interdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;
import java.util.Objects;

/**
 * Spring Boot main class.
 * @author vinodkumar.a
 */
@SpringBootApplication(scanBasePackages = "com.botree")
@EnableResourceServer
@EnableCaching
public class InterDBApplication extends SpringBootServletInitializer {

    /**
     * Main method.
     * @param args arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(InterDBApplication.class, args);
    }

    /**
     * This method is used to create a JDBC token store.
     * @param dataSourceInput JDBC data source
     * @return returns {@link JdbcTokenStore}
     */
    @Bean("jdbcTokenStore")
    public TokenStore tokenStore(final DataSource dataSourceInput) {
        return new JdbcTokenStore(Objects.requireNonNull(dataSourceInput));
    }
}
