package org.twilio.airtng.servlets;

import com.twilio.twiml.Dial;
import com.twilio.twiml.Number;
import com.twilio.twiml.Play;
import com.twilio.twiml.VoiceResponse;
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

        VoiceResponse voiceResponse = new VoiceResponse.Builder()
                .play(new Play.Builder("http://howtodocs.s3.amazonaws.com/howdy-tng.mp3").build())
                .dial(new Dial.Builder().number(new Number.Builder(outgoingNumber).build()).build())
                .build();

        respondTwiML(response, voiceResponse);
    }

}
