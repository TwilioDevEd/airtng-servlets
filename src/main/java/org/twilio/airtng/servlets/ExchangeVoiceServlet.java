package org.twilio.airtng.servlets;

import com.twilio.sdk.verbs.Dial;
import com.twilio.sdk.verbs.Play;
import com.twilio.sdk.verbs.TwiMLException;
import com.twilio.sdk.verbs.TwiMLResponse;
import org.twilio.airtng.repositories.ReservationRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExchangeVoiceServlet extends BaseExchangeServlet {

    @SuppressWarnings("unused")
    public ExchangeVoiceServlet() {
        this(new ReservationRepository());
    }

    public ExchangeVoiceServlet(ReservationRepository reservationRepository) {
        super(reservationRepository);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String from = request.getParameter("From");
        String to = request.getParameter("To");

        String outgoingNumber = gatherOutgoingPhoneNumber(from, to);

        TwiMLResponse twiMLResponse = new TwiMLResponse();
        Play play = new Play("http://howtodocs.s3.amazonaws.com/howdy-tng.mp3");
        Dial dial = new Dial(outgoingNumber);
        try {
            twiMLResponse.append(play);
            twiMLResponse.append(dial);
            respondTwiml(response, twiMLResponse);
        } catch (TwiMLException e) {
            e.printStackTrace();
        }
    }

}
