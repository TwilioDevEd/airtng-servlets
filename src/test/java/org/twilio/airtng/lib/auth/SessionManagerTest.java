package org.twilio.airtng.lib.auth;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.twilio.airtng.servlets.BaseServletTest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class SessionManagerTest extends BaseServletTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpSession session;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void logInAddsAuthenticatedAttributeToSession() {
        when(request.getSession()).thenReturn(session);

        SessionManager sessionManager = new SessionManager();
        sessionManager.logIn(request, USER_ID);

        verify(session).setAttribute(SessionManager.AUTHENTICATED, true);
        verify(session).setAttribute(SessionManager.USER_ID, USER_ID);
        verify(session).removeAttribute(SessionManager.PARTIALLY_AUTHENTICATED);
    }

    @Test
    public void partialLogInAddsPartiallyAuthenticatedAttributeToSession() {
        when(request.getSession()).thenReturn(session);

        SessionManager sessionManager = new SessionManager();
        sessionManager.partialLogIn(request, USER_ID);

        verify(session).setAttribute(SessionManager.PARTIALLY_AUTHENTICATED, true);
        verify(session).setAttribute(SessionManager.USER_ID, USER_ID);
    }

    @Test
    public void logOutInvalidatesTheSession() {
        when(request.getSession(false)).thenReturn(session);

        SessionManager sessionManager = new SessionManager();
        sessionManager.logOut(request);

        verify(session, times(1)).invalidate();
    }

    @Test
    public void getLoggedUserId() {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute(SessionManager.USER_ID)).thenReturn(USER_ID);

        SessionManager sessionManager = new SessionManager();
        long result = sessionManager.getLoggedUserId(request);

        assertEquals(USER_ID, result);
    }

    @Test
    public void isAuthenticatedReturnsTrueWhenTheAttributeAuthenticatedIsTrue() {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute(SessionManager.AUTHENTICATED)).thenReturn(true);

        SessionManager sessionManager = new SessionManager();
        boolean result = sessionManager.isAuthenticated(request);

        assertTrue(result);
    }

    @Test
    public void isAuthorizedReturnsFalseWhenTheAttributeAuthenticatedIsNotAvailable() {
        when(request.getSession(false)).thenReturn(session);

        SessionManager sessionManager = new SessionManager();
        boolean result = sessionManager.isAuthenticated(request);

        assertFalse(result);
    }

    @Test
    public void isPartiallyAuthenticatedReturnsTrueWhenTheAttributePartiallyAuthenticatedIsTrue() {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute(SessionManager.PARTIALLY_AUTHENTICATED)).thenReturn(true);

        SessionManager sessionManager = new SessionManager();
        boolean result = sessionManager.isPartiallyAuthenticated(request);

        assertTrue(result);
    }

    @Test
    public void isPartiallyAuthenticatedReturnsTrueWhenTheAttributePartiallyAuthenticatedIsNotAvailable() {
        when(request.getSession(false)).thenReturn(session);

        SessionManager sessionManager = new SessionManager();
        boolean result = sessionManager.isPartiallyAuthenticated(request);

        assertFalse(result);
    }
}

