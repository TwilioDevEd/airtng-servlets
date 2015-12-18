package org.twilio.airtng.servlets.filters;

import org.twilio.airtng.lib.auth.SessionManager;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (isAuthorized(request)) {
            chain.doFilter(servletRequest, servletResponse);
        } else {
            response.sendRedirect("/login");
        }
    }

    @Override
    public void destroy() {
    }

    private boolean isAuthorized(HttpServletRequest request) {
        return new SessionManager().isAuthenticated(request);
    }
}