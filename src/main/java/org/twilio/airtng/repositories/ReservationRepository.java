package org.twilio.airtng.repositories;

import org.twilio.airtng.models.Reservation;

public class ReservationRepository extends Repository<Reservation> {

    public ReservationRepository() {
        super(Reservation.class);
    }

}
