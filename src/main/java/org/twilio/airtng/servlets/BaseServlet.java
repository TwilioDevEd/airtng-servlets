package org.twilio.airtng.servlets;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.twilio.airtng.lib.servlets.RequestValidableServlet;

import java.util.function.Supplier;


public class BaseServlet extends RequestValidableServlet {

    private Supplier<StrongPasswordEncryptor> passwordEncryptorSupplier = () -> {
        StrongPasswordEncryptor val = new StrongPasswordEncryptor();
        passwordEncryptorSupplier = () -> val;
        return val;
    };

    protected StrongPasswordEncryptor getPassWordEncryptor() {
        return passwordEncryptorSupplier.get();
    }
}