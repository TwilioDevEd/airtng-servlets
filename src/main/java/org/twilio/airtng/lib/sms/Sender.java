package org.twilio.airtng.lib.sms;

import com.twilio.http.TwilioRestClient;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.twilio.airtng.lib.Config;

public class Sender {

    private final TwilioRestClient client;

    public Sender() {
        client = new TwilioRestClient.Builder(Config.getAccountSid(), Config.getAuthToken()).build();
    }

    public Sender(TwilioRestClient client) {
        this.client = client;
    }

    public void send(String to, String message) {
        new MessageCreator(
                new PhoneNumber(to),
                new PhoneNumber(Config.getPhoneNumber()),
                message
        ).create(client);
    }

}