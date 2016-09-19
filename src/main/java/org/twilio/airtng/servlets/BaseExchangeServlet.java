package org.twilio.airtng.servlets;

import com.twilio.twiml.TwiML;
import com.twilio.twiml.TwiMLException;
import org.twilio.airtng.lib.servlets.WebAppServlet;
import org.twilio.airtng.models.Reservation;
import org.twilio.airtng.repositories.ReservationRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class BaseExchangeServlet extends WebAppServlet {
    protected ReservationRepository reservationRepository;

    public BaseExchangeServlet(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    protected String gatherOutgoingPhoneNumber(String incomingPhoneNumber, String anonymousPhoneNumber) {
        String outgoingPhoneNumber = null;

        Reservation reservation = reservationRepository.findByAnonymousPhoneNumber(anonymousPhoneNumber);

        if (Objects.equals(reservation.getUser().getPhoneNumber(), incomingPhoneNumber)) {
            outgoingPhoneNumber = reservation.getVacationProperty().getUser().getPhoneNumber();
        } else {
            outgoingPhoneNumber = reservation.getUser().getPhoneNumber();
        }

        return outgoingPhoneNumber;
    }

    protected void respondTwiML(HttpServletResponse response, TwiML twiMLResponse)
            throws IOException {
        response.setContentType("text/xml");
        try {
            response.getWriter().write(twiMLResponse.toXml());
        } catch (TwiMLException e) {
            e.printStackTrace();
        }
    }
}
