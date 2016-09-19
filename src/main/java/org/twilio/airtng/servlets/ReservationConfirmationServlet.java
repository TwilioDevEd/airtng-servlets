package org.twilio.airtng.servlets;

import com.twilio.twiml.Body;
import com.twilio.twiml.Message;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.TwiMLException;
import org.twilio.airtng.lib.notifications.SmsNotifier;
import org.twilio.airtng.lib.phonenumber.Purchaser;
import org.twilio.airtng.lib.servlets.WebAppServlet;
import org.twilio.airtng.models.Reservation;
import org.twilio.airtng.models.User;
import org.twilio.airtng.repositories.ReservationRepository;
import org.twilio.airtng.repositories.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ReservationConfirmationServlet extends WebAppServlet {

    private UserRepository userRepository;
    private ReservationRepository reservationRepository;
    private SmsNotifier smsNotifier;
    private Purchaser phoneNumberPurchaser;

    public ReservationConfirmationServlet() {
        this(new UserRepository(), new ReservationRepository(), new SmsNotifier(), new Purchaser());
    }

    public ReservationConfirmationServlet(UserRepository userRepository
            , ReservationRepository reservationRepository
            , SmsNotifier smsNotifier
            , Purchaser phoneNumberPurchaser) {

        super();
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
        this.smsNotifier = smsNotifier;
        this.phoneNumberPurchaser = phoneNumberPurchaser;
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String phone = request.getParameter("From");
        String smsContent = request.getParameter("Body");

        String smsResponseText = "Sorry, it looks like you don't have any reservations to respond to.";

        try {
            User user = userRepository.findByPhoneNumber(phone);
            Reservation reservation = reservationRepository.findFirstPendantReservationsByUser(user.getId());
            if (reservation != null) {
                if (smsContent.contains("yes") || smsContent.contains("accept")) {
                    reservation.confirm();
                    String purchasedNumber = phoneNumberPurchaser.buyNumber(Integer.valueOf(user.getAreaCode()));
                    reservation.setAnonymousPhoneNumber(purchasedNumber);
                } else {
                    reservation.reject();
                }
                reservationRepository.update(reservation);

                smsResponseText = String.format("You have successfully %s the reservation", reservation.getStatus().toString());
                smsNotifier.notifyGuest(reservation);
            }
        } catch (Exception e) {
        }

        try {
            respondSms(response, smsResponseText);
        } catch (TwiMLException e) {
            e.printStackTrace();
        }
    }

    private void respondSms(HttpServletResponse response, String message)
            throws TwiMLException, IOException {
        MessagingResponse messagingResponse = new MessagingResponse.Builder()
                .message(new Message.Builder().body(new Body(message)).build())
                .build();
        response.setContentType("text/xml");
        response.getWriter().write(messagingResponse.toXml());
    }
}
