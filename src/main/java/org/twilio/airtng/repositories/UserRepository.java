package org.twilio.airtng.repositories;

import org.twilio.airtng.models.User;

import javax.persistence.NoResultException;

public class UserRepository extends Repository<User> {

    public UserRepository() {
        super(User.class);
    }

    public User findByEmail(String email) {
        User user = null;

        try {
            user = (User) em.createQuery("SELECT u FROM User u WHERE u.email = :email")
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException ex) {
            System.out.println(ex.getMessage());
        }

        return user;
    }

    public User findByPhoneNumber(String phoneNumber) {
        User user = null;
        try {
            user = (User) em.createQuery(
                    String.format("SELECT e FROM %s e WHERE e.phoneNumber = :phone_number", entityType.getSimpleName()))
                    .setParameter("phone_number", phoneNumber)
                    .getSingleResult();
        } catch (NoResultException ex) {
            System.out.println(ex.getMessage());
        }

        return user;
    }
}
