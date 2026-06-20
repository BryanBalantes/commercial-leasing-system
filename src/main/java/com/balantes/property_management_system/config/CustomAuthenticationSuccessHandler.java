package com.balantes.property_management_system.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        for (GrantedAuthority authority : authentication.getAuthorities()) {

            if (authority.getAuthority().equals("ROLE_ADMIN")) {
                response.sendRedirect("/homepage/");
                return;
            }

            if (authority.getAuthority().equals("ROLE_GENERAL")) {
                response.sendRedirect("/tenant");
                return;
            }
        }

        response.sendRedirect("/");
    }
}