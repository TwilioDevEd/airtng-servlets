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

    protected Lazy<SessionManager> sessionManager;
    protected Lazy<RequestParametersValidator> requestValidator;


    protected WebAppServlet() {
        sessionManager = new Lazy<>(SessionManager::new);
        requestValidator = new Lazy<>(RequestParametersValidator::new);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        requestValidator.get().setRequest(request);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    protected boolean isValidRequest() {
        return isValidRequest(requestValidator.get());
    }

    protected boolean isValidRequest(RequestParametersValidator validator) {

        return true;
    }
}
