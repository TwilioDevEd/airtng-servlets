package org.twilio.airtng.servlets;

import org.twilio.airtng.lib.servlets.WebAppServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExchangeServlet extends WebAppServlet {

    @SuppressWarnings("unused")
    public ExchangeServlet() {
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        super.doGet(request, response);

        request.getRequestDispatcher("/exchange.jsp").forward(request, response);
    }
}
