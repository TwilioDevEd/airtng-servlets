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

    private final TwilioRestClient client;

    public Purchaser() {
        client = new TwilioRestClient(Config.getAccountSid(), Config.getAuthToken());
    }

    public Purchaser(TwilioRestClient client) {
        this.client = client;
    }

    public String buyNumber(String areaCode) {
        Account account = client.getAccount();
        IncomingPhoneNumberFactory phoneNumberFactory = account.getIncomingPhoneNumberFactory();

        Map<String, String> searchParams = new HashMap<>();
        searchParams.put("AreaCode", areaCode);
        searchParams.put("SmsEnabled", String.valueOf(true));
        searchParams.put("VoiceEnabled", String.valueOf(true));

        List<AvailablePhoneNumber> availableNumbersForGivenArea = getAvailablePhoneNumbers(account, searchParams);

        if (availableNumbersForGivenArea.size() > 0) {
            String number = buyNumber(account, phoneNumberFactory, availableNumbersForGivenArea.get(0).getPhoneNumber());
            if (number != null)
                return number;
        } else {
            searchParams.remove("AreaCode");
            List<AvailablePhoneNumber> generalAvailableNumbers = getAvailablePhoneNumbers(account, searchParams);

            if (generalAvailableNumbers.size() > 0) {
                String number = buyNumber(account, phoneNumberFactory, generalAvailableNumbers.get(0).getPhoneNumber());
                if (number != null)
                    return number;
            }
        }
        return null;
    }

    private List<AvailablePhoneNumber> getAvailablePhoneNumbers(Account account, Map<String, String> searchParams) {
        AvailablePhoneNumberList phoneNumbers = account.getAvailablePhoneNumbers(searchParams, "US", "Local");
        return phoneNumbers.getPageData();
    }

    private String buyNumber(Account account, IncomingPhoneNumberFactory phoneNumberFactory, String phoneNumber) {
        try {
            Map<String, String> buyParams = new HashMap<>();
            buyParams.put("PhoneNumber", phoneNumber);
            buyParams.put("SmsApplicationSid", Config.getApplicationSid());
            buyParams.put("VoiceApplicationSid", Config.getApplicationSid());
            account.getIncomingPhoneNumberFactory().create(buyParams);

            IncomingPhoneNumber number = phoneNumberFactory.create(buyParams);

            return number.getPhoneNumber();

        } catch (TwilioRestException e) {
            e.printStackTrace();
        }
        return null;
    }
}