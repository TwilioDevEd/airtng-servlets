package org.twilio.airtng.servlets;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.twilio.airtng.lib.auth.SessionManager;
import org.twilio.airtng.repositories.UserRepository;

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

public class LogoutServletTest extends BaseServletTest {

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
    UserRepository userRepository;

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void postMethod_LoggOffUser() throws IOException, ServletException {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);

        SessionManager sessionManager = new SessionManager();
        sessionManager.logIn(request, USER_ID);

        HomeServlet homeServlet = new HomeServlet();

        homeServlet.doGet(request, response);

        verify(request).getRequestDispatcher("/home.jsp");

        LogoutServlet logoutServlet = new LogoutServlet();
        logoutServlet.doPost(request, response);

        homeServlet.doGet(request, response);
        verify(response).sendRedirect("/login");
    }


}