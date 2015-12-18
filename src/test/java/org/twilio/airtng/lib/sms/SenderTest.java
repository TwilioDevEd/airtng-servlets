package org.twilio.airtng.lib.sms;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Account;
import org.apache.http.NameValuePair;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SenderTest {

    @Mock
    private TwilioRestClient twilioClient;

    @Mock
    private Account account;

    @Mock
    private MessageFactory messageFactory;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void sendSMSMessage() throws TwilioRestException {

        when(twilioClient.getAccount()).thenReturn(account);
        when(account.getMessageFactory()).thenReturn(messageFactory);

        Sender sender = new Sender(twilioClient);
        sender.send("555-5555", "message");

        verify(messageFactory).create(anyListOf(NameValuePair.class));
    }
}