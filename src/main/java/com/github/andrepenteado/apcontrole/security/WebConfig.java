package com.gitlab.andrepenteado.apcontrole.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableOAuth2Sso
@EnableWebSecurity
public class WebConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    LogoutConfig logoutConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.authorizeRequests()
            .antMatchers("/",
                "/index.jsp",
                "/assets/**",
                "/dandelion-assets/**",
                "/layouts/**",
                "/webjars/**",
                "/home",
                "/erro",
                "/acesso-negado",
                "/login**").permitAll()
            .anyRequest().authenticated()
        .and()
            .exceptionHandling().accessDeniedPage("/acesso-negado")
        .and()
            .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessHandler(logoutConfig);
        // @formatter:on
    }

    @Bean
    public ClientCredentialsResourceDetails clientCredentialsResourceDetails(Environment env) {
        ClientCredentialsResourceDetails resource = new ClientCredentialsResourceDetails();
        resource.setAccessTokenUri(env.getProperty("security.oauth2.client.accessTokenUri"));
        resource.setClientId(env.getProperty("security.oauth2.client.clientId"));
        resource.setClientSecret(env.getProperty("security.oauth2.client.clientSecret"));
        return resource;
    }

}
