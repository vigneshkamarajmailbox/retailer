package com.botree.interdb.authentication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * This package will contain controller classes.
 * @author vinodkumar.a
 */
@Configuration
@EnableAuthorizationServer
@Order(-1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * CustomAuthenticationProvider.
     */
    private final CustomAuthenticationProvider authProvider;

    /**
     * Constructor Method.
     * @param authProviderIn authProviderIn
     */
    public SecurityConfig(final CustomAuthenticationProvider authProviderIn) {
        this.authProvider = authProviderIn;
    }

    /**
     * Apply cros orgin filter.
     * @param http login info
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable().requestMatchers()
                .antMatchers("/login", "/oauth/authorize")
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .permitAll();
    }

    /**
     * Apply cros orgin filter.
     * @param web login info
     */
    @Override
    public void configure(final WebSecurity web) {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/oauth/token");
        web.ignoring().antMatchers("/interdb/transaction/index");
    }

    /**
     * Get header value.
     * @param auth authentication builder
     */
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider);
    }

    /**
     * get AuthenticationManager bean.
     * @return AuthenticationManager
     * @throws Exception if any
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
