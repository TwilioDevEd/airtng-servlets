package org.twilio.airtng.servlets;

import org.twilio.airtng.lib.servlets.WebAppServlet;
import org.twilio.airtng.lib.web.request.validators.RequestParametersValidator;
import org.twilio.airtng.models.User;
import org.twilio.airtng.models.VacationProperty;
import org.twilio.airtng.repositories.UserRepository;
import org.twilio.airtng.repositories.VacationPropertiesRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NewVacationPropertiesServlet extends WebAppServlet {

    private final VacationPropertiesRepository vacationPropertiesRepository;
    private final UserRepository userRepository;

    @SuppressWarnings("unused")
    public NewVacationPropertiesServlet() {
        this(new VacationPropertiesRepository(), new UserRepository());
    }

    public NewVacationPropertiesServlet(VacationPropertiesRepository vacationPropertiesRepository, UserRepository userRepository) {
        this.vacationPropertiesRepository = vacationPropertiesRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        super.doGet(request, response);

        request.getRequestDispatcher("/newVacationProperties.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        super.doPost(request, response);

        String description = request.getParameter("description");
        String imageUrl = request.getParameter("imageUrl");

        if (isValidRequest()) {
            User currentUser = userRepository.find(sessionManager.get().getLoggedUserId(request));
            vacationPropertiesRepository.create(new VacationProperty(description, imageUrl, currentUser));
            response.sendRedirect("/properties");
        }
        preserveStatusRequest(request, description, imageUrl);
        request.getRequestDispatcher("/newVacationProperties.jsp").forward(request, response);
    }

    @Override
    protected boolean isValidRequest(RequestParametersValidator validator) {

        return validator.validatePresence("description", "imageUrl");
    }

    private void preserveStatusRequest(
            HttpServletRequest request,
            String description,
            String imageUrl) {
        request.setAttribute("description", description);
        request.setAttribute("imageUrl", imageUrl);
    }
}
