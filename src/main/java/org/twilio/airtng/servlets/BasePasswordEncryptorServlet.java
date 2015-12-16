package org.twilio.airtng.servlets;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.twilio.airtng.lib.servlets.WebAppServlet;
import org.twilio.airtng.lib.utilities.Lazy;


public class BasePasswordEncryptorServlet extends WebAppServlet {

    protected Lazy<StrongPasswordEncryptor> passwordEncryptor;

    public BasePasswordEncryptorServlet() {
        this(true);
    }

    public BasePasswordEncryptorServlet(boolean isSecureServlet) {
        super();
        this.passwordEncryptor = new Lazy<>(StrongPasswordEncryptor::new);
    }
}