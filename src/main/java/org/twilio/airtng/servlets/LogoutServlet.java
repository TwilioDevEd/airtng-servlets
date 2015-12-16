package org.twilio.airtng.servlets;

import org.twilio.airtng.lib.servlets.WebAppServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutServlet extends WebAppServlet {

    public LogoutServlet() {
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        sessionManager.get().logOut(request);
        response.sendRedirect("/login");
    }
}