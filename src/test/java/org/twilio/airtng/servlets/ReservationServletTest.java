package org.twilio.airtng.servlets;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.twilio.airtng.lib.notifications.SmsNotifier;
import org.twilio.airtng.repositories.ReservationRepository;
import org.twilio.airtng.repositories.UserRepository;
import org.twilio.airtng.repositories.VacationPropertiesRepository;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class ReservationServletTest extends BaseServletTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    RequestDispatcher requestDispatcher;

    @Mock
    HttpSession session;

    @Mock
    FilterChain filterChain;

    @Mock
    VacationPropertiesRepository vacationPropertiesRepository;

    @Mock
    ReservationRepository reservationRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    SmsNotifier smsNotifier;

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
    }

    private ReservationServlet getReservationServlet() {
        return new ReservationServlet(vacationPropertiesRepository,
                reservationRepository,
                userRepository,
                smsNotifier);
    }

    @Test
    public void getMethod_RendersDefaultViewWhenAuthenticated() throws IOException, ServletException {
        ReservationServlet reservationServlet = getReservationServlet();
        when(request.getParameter("id")).thenReturn(String.valueOf(USER_ID));

        verifyGetAuthenticatedRequestRedirectsToView(reservationServlet, "reservation", request, response,
                session, filterChain, requestDispatcher);
    }


    @Test
    public void getMethod_RedirectsToLoginViewWhenNotAuthenticated() throws IOException, ServletException {
        ReservationServlet reservationServlet = getReservationServlet();
        when(request.getParameter("id")).thenReturn(String.valueOf(USER_ID));

        verifyGetUnauthenticatedRequestRedirectsToLogin(reservationServlet, request, response,
                session, filterChain, requestDispatcher);
    }

    @Test
    public void postMethodWithMissingMessage_RendersViewWithErrors() throws IOException, ServletException {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getParameter("id")).thenReturn(String.valueOf(USER_ID));
        when(request.getParameter("message")).thenReturn("");

        ReservationServlet reservationServlet = getReservationServlet();
        reservationServlet.doPost(request, response);

        verify(request).getRequestDispatcher("/reservation.jsp");
        verify(request).setAttribute("messageError", "Message can't be blank");
    }
}