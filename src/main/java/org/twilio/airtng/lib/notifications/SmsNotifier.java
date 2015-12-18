package org.twilio.airtng.lib.notifications;

import org.twilio.airtng.lib.sms.Sender;
import org.twilio.airtng.models.Reservation;

public class SmsNotifier {
    private Sender smsSender;

    public SmsNotifier() {
        this(new Sender());
    }

    public SmsNotifier(Sender smsSender) {
        this.smsSender = smsSender;
    }

    public void notifyHost(Reservation reservation) {
        String phoneNumber = String.format("+%s%s",
                reservation.getVacationProperty().getUser().getCountryCode(),
                reservation.getVacationProperty().getUser().getPhoneNumber());

        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append(String.format("You have a new reservation request from %s for %s:\n",
                reservation.getUser().getName(),
                reservation.getVacationProperty().getDescription()));

        messageBuilder.append(String.format("%s \n", reservation.getMessage()));

        messageBuilder.append("Reply [accept] or [reject]");


        smsSender.send(phoneNumber, messageBuilder.toString());
    }

    public void notifyGuest(Reservation reservation) {
        String phoneNumber = String.format("+%s%s",
                reservation.getUser().getCountryCode(),
                reservation.getUser().getPhoneNumber());

        String message = String.format("Your recent request to stay at %s was %s:\n",
                reservation.getVacationProperty().getDescription(),
                reservation.getStatus().toString());


        smsSender.send(phoneNumber, message);
    }
}
