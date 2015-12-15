package org.twilio.airtng.servlets;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.twilio.airtng.lib.auth.SessionManager;
import org.twilio.airtng.lib.web.request.validators.RequestParametersValidator;
import org.twilio.airtng.models.User;
import org.twilio.airtng.repositories.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends BaseServlet {

    private final SessionManager sessionManager;
    private final UserRepository userRepository;

    @SuppressWarnings("unused")
    public LoginServlet() {
        this(new SessionManager(), new UserRepository());
    }

    public LoginServlet(SessionManager sessionManager, UserRepository userService) {
        this.sessionManager = sessionManager;
        this.userRepository = userService;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (sessionManager.isAuthenticated(request))
            request.getRequestDispatcher("/home.jsp").forward(request, response);
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        super.doPost(request, response);

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (isValidRequest()) {


            StrongPasswordEncryptor passwordEncryptor = this.getPassWordEncryptor();

            User user = userRepository.findByEmail(email);

            if (user != null && passwordEncryptor.checkPassword(password,user.getPassword()))
                request.getRequestDispatcher("/home.jsp").forward(request, response);
            else
                request.setAttribute("loginError", "Invalid credentials");
        }

        preserveStatusRequest(request, email, password);
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected boolean isValidRequest(RequestParametersValidator validator) {

        return validator.validatePresence("email", "password") && validator.validateEmail("email");
    }

    private void preserveStatusRequest(
            HttpServletRequest request,
            String email,
            String password) {
        request.setAttribute("email", email);
        request.setAttribute("password", password);
    }
}
