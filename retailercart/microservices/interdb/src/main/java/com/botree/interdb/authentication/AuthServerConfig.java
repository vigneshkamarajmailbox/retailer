package com.botree.interdb.authentication;

import com.botree.common.constants.StringConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This package will contain controller classes.
 * @author vinodkumar.a
 */
@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    /** LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(AuthServerConfig.class);

    /** authenticationManagerBean. */
    private final AuthenticationManager authenticationManager;

    /** tokenStore. */
    private final TokenStore tokenStore;

    public AuthServerConfig(final @Qualifier("authenticationManagerBean") AuthenticationManager authenticationManagerIn,
                            final @Qualifier("jdbcTokenStore") TokenStore tokenStoreIn) {
        this.authenticationManager = authenticationManagerIn;
        this.tokenStore = tokenStoreIn;
    }

    /** Spring Authentication  Configuration . */
    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("SampleClientId")
                .secret(passwordEncoder().encode("secret"))
                .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit",
                        "client_credentials")
                .scopes("user_info")
                .autoApprove(true)
                .redirectUris("https://www.getpostman.com/oauth2/callback");
    }

    /**
     * Spring Authentication password encode method.
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(final CharSequence charSequence) {
                return getMd5(charSequence.toString());
            }

            @Override
            public boolean matches(final CharSequence charSequence, final String s) {
                return s.equals(getMd5(charSequence.toString()));
            }
        };
    }

    /** Spring Authentication  Configuration . */
    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore);
    }

    /**
     * This method is used to generate Md5 hash.
     * @param input Raw String
     * @return MD5 hash for the input string
     */
    public static String getMd5(final String input) {
        try {
            // Static getInstance method is called with hashing SHA
            var md = MessageDigest.getInstance("MD5");

            // digest() method called
            // to calculate message digest of an input
            // and return array of byte
            var messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            var no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            var hashText = new StringBuilder();
            hashText.append(no.toString(StringConstants.CONST_INT_16));

            while (hashText.length() < StringConstants.CONST_INT_32) {
                hashText.append("0").append(hashText);
            }

            return hashText.toString();
        } catch (NoSuchAlgorithmException e) {
            LOG.error("Exception while generating Md5 hash", e);
            return null;
        }
    }
}
