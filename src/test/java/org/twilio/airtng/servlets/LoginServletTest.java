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


public class LoginServletTest extends BaseServletTest {

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
    public void getMethod_RendersDefaultViewWhenNotAuthenticated() throws IOException, ServletException {
        verifyGetRequestRedirectsToView(new LoginServlet(userRepository), "login", request, response,
                requestDispatcher);
    }

    @Test
    public void getMethod_RedirectsToHomeWhenAuthenticated() throws IOException, ServletException {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute(SessionManager.AUTHENTICATED)).thenReturn(true);

        LoginServlet loginServlet = new LoginServlet(userRepository);
        loginServlet.doGet(request, response);

        verify(response).sendRedirect("/home");
    }

    @Test
    public void postMethodWithMissingEmail_RendersViewWithErrors() throws IOException, ServletException {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getParameter("email")).thenReturn("");
        when(request.getParameter("password")).thenReturn("");

        LoginServlet loginServlet = new LoginServlet(userRepository);
        loginServlet.doPost(request, response);

        verify(request).getRequestDispatcher("/login.jsp");
        verify(request).setAttribute("emailError", "Email can't be blank");
    }

    @Test
    public void postMethodWithMissingPassword_RendersViewWithErrors() throws IOException, ServletException {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getParameter("email")).thenReturn("user@em");
        when(request.getParameter("password")).thenReturn("");

        LoginServlet loginServlet = new LoginServlet(userRepository);
        loginServlet.doPost(request, response);

        verify(request).getRequestDispatcher("/login.jsp");
        verify(request).setAttribute("passwordError", "Password can't be blank");
    }

    @Test
    public void postMethodWithInvalidEmail_RendersViewWithErrors() throws IOException, ServletException {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getParameter("email")).thenReturn("user@em");
        when(request.getParameter("password")).thenReturn("password");

        LoginServlet loginServlet = new LoginServlet(userRepository);
        loginServlet.doPost(request, response);

        verify(request).getRequestDispatcher("/login.jsp");
        verify(request).setAttribute("emailInvalidError", "Email is invalid");
    }
}