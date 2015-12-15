package org.twilio.airtng.servlets;

import org.twilio.airtng.lib.auth.SessionManager;
import org.twilio.airtng.lib.servlets.WebAppServlet;
import org.twilio.airtng.repositories.VacationPropertiesRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class VacationPropertiesServlet extends WebAppServlet {

    private final SessionManager sessionManager;
    private final VacationPropertiesRepository vacationPropertiesRepository;

    @SuppressWarnings("unused")
    public VacationPropertiesServlet() {
        this(new SessionManager(), new VacationPropertiesRepository());
    }

    public VacationPropertiesServlet(SessionManager sessionManager, VacationPropertiesRepository vacationPropertiesRepository) {
        this.sessionManager = sessionManager;
        this.vacationPropertiesRepository = vacationPropertiesRepository;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        super.doGet(request, response);

        if (!sessionManager.isAuthenticated(request))
            response.sendRedirect("/login");
        request.getRequestDispatcher("/properties.jsp").forward(request, response);
    }
}
