package org.twilio.airtng.repositories;

import org.twilio.airtng.models.Reservation;

import javax.persistence.NoResultException;

public class ReservationRepository extends Repository<Reservation> {

    public ReservationRepository() {
        super(Reservation.class);
    }

    public Reservation findFirstPendantReservationsByUser(long userId) {
        Reservation reservation = null;
        try {
            reservation = (Reservation)
                    em.createQuery("SELECT e FROM Reservation e WHERE e.status = 0 AND e.vacationProperty.user.id = :user_id")
                            .setMaxResults(1)
                    .setParameter("user_id", userId)
                    .getSingleResult();
        } catch (NoResultException ex) {
            System.out.println(ex.getMessage());
        }

        return reservation;
    }

    public Reservation findByAnonymousPhoneNumber(String anonymousPhoneNumber) {
        Reservation reservation = null;
        try {
            reservation = (Reservation)
                    em.createQuery("SELECT e FROM Reservation e WHERE e.anonymousPhoneNumber = :anonymous_phone_number")
                            .setMaxResults(1)
                            .setParameter("anonymous_phone_number", anonymousPhoneNumber)
                            .getSingleResult();
        } catch (NoResultException ex) {
            System.out.println(ex.getMessage());
        }

        return reservation;
    }
}
