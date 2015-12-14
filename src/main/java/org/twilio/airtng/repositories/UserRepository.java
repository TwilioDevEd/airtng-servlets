package org.twilio.airtng.repositories;

import org.twilio.airtng.models.User;

import javax.persistence.NoResultException;

public class UserRepository extends Repository<User> {

    public UserRepository() {
        super(User.class);
    }

    public User find(long id) {
        em.getEntityManagerFactory().getCache().evictAll();
        User user = em.find(User.class, id);
        em.refresh(user);

        return user;
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


}
