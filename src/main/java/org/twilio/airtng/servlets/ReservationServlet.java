package org.twilio.airtng.servlets;

import org.twilio.airtng.lib.servlets.WebAppServlet;
import org.twilio.airtng.lib.web.request.validators.RequestParametersValidator;
import org.twilio.airtng.models.Reservation;
import org.twilio.airtng.models.User;
import org.twilio.airtng.models.VacationProperty;
import org.twilio.airtng.repositories.ReservationRepository;
import org.twilio.airtng.repositories.UserRepository;
import org.twilio.airtng.repositories.VacationPropertiesRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ReservationServlet extends WebAppServlet {

    private final VacationPropertiesRepository vacationPropertiesRepository;
    private final ReservationRepository reservationRepository;
    private UserRepository userRepository;

    public ReservationServlet() {
        this(new VacationPropertiesRepository(), new ReservationRepository(), new UserRepository());
    }

    public ReservationServlet(VacationPropertiesRepository vacationPropertiesRepository, ReservationRepository reservationRepository, UserRepository userRepository) {
        super();
        this.vacationPropertiesRepository = vacationPropertiesRepository;
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        VacationProperty vacationProperty = vacationPropertiesRepository.find(Long.parseLong(request.getParameter("id")));
        request.setAttribute("vacationProperty", vacationProperty);
        request.getRequestDispatcher("/reservation.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        super.doPost(request, response);

        String message = request.getParameter("message");
        String propertyId = request.getParameter("propertyId");
        VacationProperty vacationProperty = vacationPropertiesRepository.find(Long.parseLong(propertyId));

        if (isValidRequest()) {
            User currentUser = userRepository.find(sessionManager.get().getLoggedUserId(request));
            reservationRepository.create(new Reservation(message, vacationProperty, currentUser));
            response.sendRedirect("/properties");
        }
        preserveStatusRequest(request, message, vacationProperty);
        request.getRequestDispatcher("/reservation.jsp").forward(request, response);
    }

    @Override
    protected boolean isValidRequest(RequestParametersValidator validator) {

        return validator.validatePresence("message");
    }

    private void preserveStatusRequest(
            HttpServletRequest request,
            String message, Object vacationProperty) {
        request.setAttribute("message", message);
        request.setAttribute("vacationProperty", vacationProperty);
    }
}
