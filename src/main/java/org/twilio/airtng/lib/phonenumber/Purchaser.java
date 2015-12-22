package org.twilio.airtng.lib.phonenumber;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.IncomingPhoneNumberFactory;
import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.resource.instance.AvailablePhoneNumber;
import com.twilio.sdk.resource.instance.IncomingPhoneNumber;
import com.twilio.sdk.resource.list.AvailablePhoneNumberList;
import org.twilio.airtng.lib.Config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Purchaser {

    public String buyNumber(String areaCode) {

        // Instantiate a new Twilio Rest Client
        TwilioRestClient client = new TwilioRestClient(Config.getAccountSid(), Config.getAuthToken());

        // Get the account and phone number factory class
        Account account = client.getAccount();
        IncomingPhoneNumberFactory phoneNumberFactory =
                account.getIncomingPhoneNumberFactory();

        // Find a number with the given area code!
        // See: http://www.twilio.com/docs/api/rest/available-phone-numbers
        Map<String, String> searchParams = new HashMap<>();
        searchParams.put("AreaCode", areaCode);

        AvailablePhoneNumberList phoneNumbers = account.getAvailablePhoneNumbers(searchParams);
        List<AvailablePhoneNumber> list = phoneNumbers.getPageData();

        if (list.size() > 0) {
            /* POST the Phone Number we want to buy to the IncomingPhoneNumbers resource */
            try {
                // Buy the first number returned
                Map<String, String> buyParams = new HashMap<>();
                buyParams.put("PhoneNumber", list.get(0).getPhoneNumber());
                buyParams.put("VoiceUrl", "http://demo.twilio.com/welcome/voice/");
                account.getIncomingPhoneNumberFactory().create(buyParams);

                IncomingPhoneNumber number = phoneNumberFactory.create(buyParams);

                return number.getPhoneNumber();

            } catch (TwilioRestException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public String buyNumber() {
        return buyNumber("US");
    }
}