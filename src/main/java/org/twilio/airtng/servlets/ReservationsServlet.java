package org.twilio.airtng.servlets;


import org.twilio.airtng.lib.auth.SessionManager;
import org.twilio.airtng.models.Reservation;
import org.twilio.airtng.models.User;
import org.twilio.airtng.models.VacationProperty;
import org.twilio.airtng.repositories.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReservationsServlet extends BasePasswordEncryptorServlet {

    private final UserRepository userRepository;
    private final SessionManager sessionManager;

    @SuppressWarnings("unused")
    public ReservationsServlet() {
        this(new UserRepository(), new SessionManager());
    }

    public ReservationsServlet(UserRepository userService, SessionManager sessionManager) {
        super(false);
        this.userRepository = userService;
        this.sessionManager = sessionManager;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        super.doGet(request, response);
        User user = userRepository.find(sessionManager.getLoggedUserId(request));

        List<Reservation> accumulatedReservationsAsHost = new ArrayList<>();
        user.getVacationProperties()
                .stream()
                .filter(vacationProperty -> vacationProperty.getReservations().size() > 0)
                .map(VacationProperty::getReservations)
                .forEach(reservations -> reservations
                        .forEach(reservation -> accumulatedReservationsAsHost.add(reservation)));

        request.setAttribute("reservationsAsGuest", user.getReservations());
        request.setAttribute("reservationsAsHost", accumulatedReservationsAsHost);

        request.getRequestDispatcher("/reservations.jsp").forward(request, response);
    }
}