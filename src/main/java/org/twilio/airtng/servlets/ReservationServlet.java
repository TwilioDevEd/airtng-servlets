package org.twilio.airtng.servlets;

import org.twilio.airtng.lib.servlets.WebAppServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ReservationServlet extends WebAppServlet {

    public ReservationServlet() {
        super();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/reservation.jsp").forward(request, response);
    }
}
