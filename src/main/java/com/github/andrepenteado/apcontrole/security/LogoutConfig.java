package com.github.andrepenteado.apcontrole.security;

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
        this.setDefaultTargetUrl("/home");
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
                    throws IOException, ServletException {
        super.onLogoutSuccess(request, response, authentication);
    }

}
