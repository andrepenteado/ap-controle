package com.gitlab.andrepenteado.controle;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;

@EnableOAuth2Sso
@EnableWebSecurity
public class ModuloControleSecurity extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/",
                "/index.jsp",
                "/login**").permitAll()
            .anyRequest().authenticated()
        .and()
            .logout();
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
