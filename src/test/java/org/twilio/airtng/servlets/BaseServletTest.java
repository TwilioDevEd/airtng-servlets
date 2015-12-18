package org.twilio.airtng.servlets;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.twilio.airtng.lib.auth.SessionManager;
import org.twilio.airtng.lib.servlets.WebAppServlet;
import org.twilio.airtng.servlets.filters.AuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.StringReader;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public abstract class BaseServletTest {

    protected static long USER_ID = 1101;

    protected Document getDocument(String content) throws JDOMException, IOException {
        return new SAXBuilder().build(new StringReader(content));
    }

    protected Element getElement(Document document, String path) {
        String[] pathComponents = path.split("/");

        switch (pathComponents.length) {
            case 1:
                return document.getRootElement().getChild(pathComponents[0]);
            case 2:
                return document.getRootElement()
                        .getChild(pathComponents[0])
                        .getChild(pathComponents[1]);
            case 3:
                return document.getRootElement()
                        .getChild(pathComponents[0])
                        .getChild(pathComponents[1])
                        .getChild(pathComponents[2]);
            default:
                return null;
        }
    }

    protected String getAttributeValue(Document document, String path, String attrName) {
        Element element = getElement(document, path);
        return element.getAttributeValue(attrName);
    }

    protected void assertThatContentTypeIsXML(HttpServletResponse response) {
        verify(response).setContentType("text/xml");
    }

    protected <S extends WebAppServlet> void verifyGetAuthenticatedRequestRedirectsToView(S servlet,
                                                                                          String viewName,
                                                                                          HttpServletRequest request,
                                                                                          HttpServletResponse response,
                                                                                          HttpSession session,
                                                                                          FilterChain filterChain,
                                                                                          RequestDispatcher requestDispatcher)
            throws IOException, ServletException {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);

        SessionManager sessionManager = new SessionManager();
        sessionManager.logIn(request, USER_ID);

        AuthenticationFilter authFilter = new AuthenticationFilter();
        authFilter.doFilter(request, response, filterChain);
        servlet.doGet(request, response);

        verify(request).getRequestDispatcher(String.format("/%s.jsp", viewName));
    }

    protected <S extends WebAppServlet> void verifyGetRequestRedirectsToView(S servlet,
                                                                             String viewName,
                                                                             HttpServletRequest request,
                                                                             HttpServletResponse response,
                                                                             RequestDispatcher requestDispatcher)
            throws IOException, ServletException {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        servlet.doGet(request, response);

        verify(request).getRequestDispatcher(String.format("/%s.jsp", viewName));
    }

    protected <S extends WebAppServlet> void verifyGetUnauthenticatedRequestRedirectsToLogin(S servlet,
                                                                                             HttpServletRequest request,
                                                                                             HttpServletResponse response,
                                                                                             HttpSession session,
                                                                                             FilterChain filterChain,
                                                                                             RequestDispatcher requestDispatcher)
            throws IOException, ServletException {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);

        AuthenticationFilter authFilter = new AuthenticationFilter();
        authFilter.doFilter(request, response, filterChain);
        servlet.doGet(request, response);

        verify(response).sendRedirect("/login");
    }
}