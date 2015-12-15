package org.twilio.airtng.lib.web.request.validators;

import javax.servlet.http.HttpServletRequest;

public class RequestParametersValidator {

    private HttpServletRequest request;

    public RequestParametersValidator(){
    }

    public RequestParametersValidator(HttpServletRequest request) {
        this.request = request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public boolean validatePresence(String... parameters) {

        Byte invalidParams = 0;
        for(String parameterName : parameters) {
            String parameterValue = request.getParameter(parameterName);
            if (parameterValue.isEmpty()) {
                request.setAttribute(
                        String.format("%sError", parameterName),
                        String.format("%s can't be blank", capitalize(splitCamelCase(parameterName))));

                invalidParams++;
            }
        }

        return invalidParams == 0;
    }

    public boolean validateEmail(String parameter) {

        String email = request.getParameter(parameter);

        EmailValidator emailValidator = new EmailValidator();

        if (!email.isEmpty() && !emailValidator.validate(email)) {
            request.setAttribute(
                    String.format("%sInvalidError", parameter),
                    String.format("%s is invalid", capitalize(splitCamelCase(parameter))));

            return false;
        }

        return true;
    }

    private static String capitalize(final String word) {
        return Character.toUpperCase(word.charAt(0)) + word.substring(1);
    }

    private static String splitCamelCase(String s) {
        return s.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                " "
        );
    }
}