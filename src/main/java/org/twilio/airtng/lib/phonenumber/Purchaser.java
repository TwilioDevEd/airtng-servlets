package org.twilio.airtng.lib.phonenumber;

import com.twilio.base.ResourceSet;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.api.v2010.account.IncomingPhoneNumberCreator;
import com.twilio.rest.api.v2010.account.availablephonenumbercountry.Local;
import com.twilio.type.PhoneNumber;
import org.twilio.airtng.lib.Config;

public class Purchaser {

    private final TwilioRestClient client;

    public Purchaser() {
        client = new TwilioRestClient.Builder(Config.getAccountSid(), Config.getAuthToken()).build();
    }

    public Purchaser(TwilioRestClient client) {
        this.client = client;
    }

    public String buyNumber(Integer areaCode) {
        ResourceSet<Local> availableNumbersForGivenArea = Local.reader("US")
                .setAreaCode(areaCode)
                .setSmsEnabled(true)
                .setVoiceEnabled(true)
                .read();

        if (availableNumbersForGivenArea.iterator().hasNext()) {
            PhoneNumber availableNumber = createBuyNumber(
                    availableNumbersForGivenArea.iterator().next().getPhoneNumber()
            );

            return availableNumber.toString();
        } else {
            ResourceSet<Local> generalAvailableNumbers = Local.reader("US")
                    .setSmsEnabled(true)
                    .setVoiceEnabled(true)
                    .read();
            if (generalAvailableNumbers.iterator().hasNext()) {
                PhoneNumber availableNumber = createBuyNumber(
                        generalAvailableNumbers.iterator().next().getPhoneNumber()
                );
                return availableNumber.toString();
            } else {
                return null;
            }
        }
    }

    private PhoneNumber createBuyNumber(PhoneNumber phoneNumber) {
        return new IncomingPhoneNumberCreator(phoneNumber)
                .setSmsApplicationSid(Config.getApplicationSid())
                .setVoiceApplicationSid(Config.getApplicationSid())
                .create(client).getPhoneNumber();
    }
}