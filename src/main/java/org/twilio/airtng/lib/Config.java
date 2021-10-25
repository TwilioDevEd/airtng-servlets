package org.twilio.airtng.lib;

import io.github.cdimascio.dotenv.Dotenv;

public class Config {

    private static final Dotenv dotenv = Dotenv.load();

    public static String getAccountSid() {
        return dotenv.get("TWILIO_ACCOUNT_SID");
    }

    public static String getAuthToken() {
        return dotenv.get("TWILIO_AUTH_TOKEN");
    }

    public static String getPhoneNumber() {
        return dotenv.get("TWILIO_PHONE_NUMBER");
    }

    public static Dotenv getDotenv() {
        return dotenv;
    }
}
