package com.gitlab.andrepenteado.controle.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LogoutConfig extends SimpleUrlLogoutSuccessHandler {

    @Autowired
    private OAuth2ProtectedResourceDetails resource;

    private String path;

    public LogoutConfig() {
        this.setAlwaysUseDefaultTargetUrl(true);
    }

    @PostConstruct
    public void init() {
        String uri = resource.getAccessTokenUri();
        path = uri.replaceFirst("/[^/]+$", "/revoke-token");
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication == null) {
            super.onLogoutSuccess(request, response, authentication);
            return;
        }
        OAuth2RestOperations restTemplate = new OAuth2RestTemplate(resource);
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)authentication.getDetails();
        DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(details.getTokenValue());
        token.setTokenType(details.getTokenType());
        restTemplate.getOAuth2ClientContext().setAccessToken(token);
        restTemplate.getForEntity(path + "/" + details.getTokenValue(), String.class);
        super.onLogoutSuccess(request, response, authentication);
    }

}
