package com.gitlab.andrepenteado.controle.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LogoutConfig extends SimpleUrlLogoutSuccessHandler {

    public LogoutConfig() {
        this.setAlwaysUseDefaultTargetUrl(true);
    }

    @Value("${apcontrole.logout}")
    public void setLogoutUrl(String logoutUrl) {
        this.setDefaultTargetUrl(logoutUrl);
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
                    throws IOException, ServletException {
        super.onLogoutSuccess(request, response, authentication);
    }

}
