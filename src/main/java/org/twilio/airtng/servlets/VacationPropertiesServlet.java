package org.twilio.airtng.servlets;

import org.twilio.airtng.lib.servlets.WebAppServlet;
import org.twilio.airtng.models.VacationProperty;
import org.twilio.airtng.repositories.VacationPropertiesRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class VacationPropertiesServlet extends WebAppServlet {

    private final VacationPropertiesRepository vacationPropertiesRepository;

    @SuppressWarnings("unused")
    public VacationPropertiesServlet() {
        this(new VacationPropertiesRepository());
    }

    public VacationPropertiesServlet(VacationPropertiesRepository vacationPropertiesRepository) {
        this.vacationPropertiesRepository = vacationPropertiesRepository;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        super.doGet(request, response);
        List<VacationProperty> properties = (List<VacationProperty>) vacationPropertiesRepository.findAll();
        request.setAttribute("vacationProperties", properties);
        request.getRequestDispatcher("/vacationProperties.jsp").forward(request, response);
    }
}
