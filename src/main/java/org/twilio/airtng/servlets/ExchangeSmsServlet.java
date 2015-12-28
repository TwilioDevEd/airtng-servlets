package org.twilio.airtng.servlets;

import com.twilio.sdk.verbs.*;
import org.twilio.airtng.repositories.ReservationRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExchangeSmsServlet extends BaseExchangeServlet {

    @SuppressWarnings("unused")
    public ExchangeSmsServlet() {
        this(new ReservationRepository());
    }

    public ExchangeSmsServlet(ReservationRepository reservationRepository) {
        super(reservationRepository);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String from = request.getParameter("From");
        String to = request.getParameter("To");
        String body = request.getParameter("Body");

        String outgoingNumber = gatherOutgoingPhoneNumber(from, to);

        TwiMLResponse twiMLResponse = new TwiMLResponse();
        Message message = new Message(body);
        message.setTo(outgoingNumber);
        try {
            twiMLResponse.append(message);
            respondTwiML(response, twiMLResponse);
        } catch (TwiMLException e) {
            e.printStackTrace();
        }
    }
}
