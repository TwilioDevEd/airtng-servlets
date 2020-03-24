package org.twilio.airtng.lib.helpers;


import com.twilio.twiml.messaging.Body;
import com.twilio.twiml.messaging.Message;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.TwiMLException;

public class TwiMLHelper {

    public static MessagingResponse buildSmsRespond(String messageText) throws TwiMLException {
        return new MessagingResponse.Builder()
                .message(new Message.Builder()
                        .body(new Body.Builder(messageText).build())
                        .build()
                )
                .build();
    }
}
