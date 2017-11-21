package org.twilio.airtng.servlets;

import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Body;
import com.twilio.twiml.messaging.Message;
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

        MessagingResponse messagingResponse = new MessagingResponse.Builder()
                .message(new Message.Builder()
                        .body(new Body.Builder(body).build()).to(outgoingNumber)
                        .build())
                .build();

        respondTwiML(response, messagingResponse);
    }
}
