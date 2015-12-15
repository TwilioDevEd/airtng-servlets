package org.twilio.airtng.lib.servlets;

import org.twilio.airtng.lib.web.request.validators.RequestParametersValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.function.Supplier;

public class RequestValidableServlet extends HttpServlet {

    private Supplier<RequestParametersValidator> validatorSupplier = () -> {
        RequestParametersValidator val = new RequestParametersValidator();
        validatorSupplier = () -> val;
        return val;
    };

    protected RequestValidableServlet(){
    }

    protected RequestParametersValidator getValidator() {
        return validatorSupplier.get();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.getValidator().setRequest(request);
    }

    protected boolean isValidRequest() {

        return isValidRequest(getValidator());
    }

    protected boolean isValidRequest(RequestParametersValidator validator) {

        return true;
    }
}
