package org.twilio.airtng.lib;

public class Config {

    public static String getAccountSid() {
        return System.getenv("TWILIO_ACCOUNT_SID");
    }

    public static String getAuthToken() {
        return System.getenv("TWILIO_AUTH_TOKEN");
    }

    public static String getPhoneNumber() {
        return System.getenv("TWILIO_PHONE_NUMBER");
    }
}