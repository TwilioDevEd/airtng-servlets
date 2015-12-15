package org.twilio.airtng.servlets;

import org.twilio.airtng.lib.servlets.WebAppServlet;
import org.twilio.airtng.repositories.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HomeServlet extends WebAppServlet {

    private final UserRepository userRepository;

    @SuppressWarnings("unused")
    public HomeServlet() {
        this(new UserRepository());
    }

    public HomeServlet(UserRepository userService) {
        this.userRepository = userService;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        super.doGet(request, response);

        request.getRequestDispatcher("/home.jsp").forward(request, response);
    }
}
