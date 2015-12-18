package org.twilio.airtng.servlets;

import org.twilio.airtng.lib.servlets.WebAppServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HomeServlet extends WebAppServlet {

    @SuppressWarnings("unused")
    public HomeServlet() {
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        super.doGet(request, response);

        request.getRequestDispatcher("/home.jsp").forward(request, response);
    }
}
