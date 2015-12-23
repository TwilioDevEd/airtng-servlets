package org.twilio.airtng.lib.helpers;

import com.twilio.sdk.verbs.Message;
import com.twilio.sdk.verbs.TwiMLException;
import com.twilio.sdk.verbs.TwiMLResponse;

public class TwiMLHelper {

    public static TwiMLResponse buildSmsRespond(String messageText) throws TwiMLException {
        Message message = new Message(messageText);
        TwiMLResponse response = new TwiMLResponse();
        response.append(message);
        return response;
    }
}


