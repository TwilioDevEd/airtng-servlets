package org.twilio.airtng.servlets;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.twilio.airtng.repositories.VacationPropertiesRepository;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class VacationPropertiesServletTest extends BaseServletTest {

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

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getMethod_RendersDefaultViewWhenAuthenticated() throws IOException, ServletException {
        verifyGetAuthenticatedRequestRedirectsToView(new VacationPropertiesServlet(vacationPropertiesRepository),
                "vacationProperties", request, response, session, filterChain, requestDispatcher);
    }

    @Test
    public void getMethod_RedirectsToLoginViewWhenNotAuthenticated() throws IOException, ServletException {
        verifyGetUnauthenticatedRequestRedirectsToLogin(new VacationPropertiesServlet(vacationPropertiesRepository),
                request, response, session, filterChain, requestDispatcher);
    }
}