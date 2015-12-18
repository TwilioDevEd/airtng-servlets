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
            reservation = (Reservation) em.createQuery(
                    String.format("SELECT e FROM %s e WHERE e.status = 0 AND user.id = :user_id", entityType.getSimpleName()))
                    .setParameter("user_id", userId)
                    .getSingleResult();
        } catch (NoResultException ex) {
            System.out.println(ex.getMessage());
        }

        return reservation;
    }
}
