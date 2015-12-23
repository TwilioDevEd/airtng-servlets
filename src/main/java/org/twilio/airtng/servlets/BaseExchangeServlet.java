package org.twilio.airtng.servlets;

import com.twilio.sdk.verbs.TwiMLException;
import com.twilio.sdk.verbs.TwiMLResponse;
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
        }

        if (Objects.equals(reservation.getVacationProperty().getUser().getPhoneNumber(), incomingPhoneNumber)) {
            outgoingPhoneNumber = reservation.getUser().getPhoneNumber();
        }

        return outgoingPhoneNumber;
    }

    protected void respondTwiml(HttpServletResponse response, TwiMLResponse twiMLResponse)
            throws TwiMLException, IOException {
        response.setContentType("text/xml");
        response.getWriter().write(twiMLResponse.toXML());
    }
}
