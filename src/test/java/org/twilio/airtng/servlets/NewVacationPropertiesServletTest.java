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

public class NewVacationPropertiesServletTest extends BaseServletTest {

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

    private NewVacationPropertiesServlet getVacationPropertiesServlet() {
        return new NewVacationPropertiesServlet(vacationPropertiesRepository,
                userRepository);
    }

    @Test
    public void getMethod_RendersDefaultViewWhenAuthenticated() throws IOException, ServletException {
        NewVacationPropertiesServlet newVacationPropertiesServlet = getVacationPropertiesServlet();

        verifyGetAuthenticatedRequestRedirectsToView(newVacationPropertiesServlet, "newVacationProperties", request, response,
                session, filterChain, requestDispatcher);
    }


    @Test
    public void getMethod_RedirectsToLoginViewWhenNotAuthenticated() throws IOException, ServletException {
        NewVacationPropertiesServlet newVacationPropertiesServlet = getVacationPropertiesServlet();

        verifyGetUnauthenticatedRequestRedirectsToLogin(newVacationPropertiesServlet, request, response,
                session, filterChain, requestDispatcher);
    }

    @Test
    public void postMethodWithMissingDescription_RendersViewWithErrors() throws IOException, ServletException {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getParameter("description")).thenReturn("");
        when(request.getParameter("imageUrl")).thenReturn("");


        NewVacationPropertiesServlet newVacationPropertiesServlet = getVacationPropertiesServlet();
        newVacationPropertiesServlet.doPost(request, response);

        verify(request).getRequestDispatcher("/newVacationProperties.jsp");
        verify(request).setAttribute("descriptionError", "Description can't be blank");
    }

    @Test
    public void postMethodWithMissingImageUrl_RendersViewWithErrors() throws IOException, ServletException {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getParameter("description")).thenReturn("Description");
        when(request.getParameter("imageUrl")).thenReturn("");


        NewVacationPropertiesServlet newVacationPropertiesServlet = getVacationPropertiesServlet();
        newVacationPropertiesServlet.doPost(request, response);

        verify(request).getRequestDispatcher("/newVacationProperties.jsp");
        verify(request).setAttribute("imageUrlError", "Image Url can't be blank");
    }

}