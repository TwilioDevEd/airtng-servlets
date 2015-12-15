package org.twilio.airtng.lib.servlets;

import org.twilio.airtng.lib.auth.SessionManager;
import org.twilio.airtng.lib.utilities.Lazy;
import org.twilio.airtng.lib.web.request.validators.RequestParametersValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WebAppServlet extends HttpServlet {

    private boolean isSecureServlet = false;
    protected Lazy<SessionManager> sessionManager;
    protected Lazy<RequestParametersValidator> requestValidator;

    protected WebAppServlet() {
        this(true);
    }

    protected WebAppServlet(boolean isSecureServlet) {
        this.isSecureServlet = isSecureServlet;
        sessionManager = new Lazy<>(SessionManager::new);
        requestValidator = new Lazy<>(RequestParametersValidator::new);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        requestValidator.get().setRequest(request);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (isSecureServlet)
            secureRequest(request, response);
    }

    protected boolean isValidRequest() {
        return isValidRequest(requestValidator.get());
    }

    protected boolean isValidRequest(RequestParametersValidator validator) {

        return true;
    }

    protected void secureRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!sessionManager.get().isAuthenticated(request))
            response.sendRedirect("/login");
    }
}
