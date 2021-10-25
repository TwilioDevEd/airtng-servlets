package org.twilio.airtng.repositories;

import io.github.cdimascio.dotenv.Dotenv;
import org.twilio.airtng.lib.Config;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public abstract class Repository<T> {

    protected final EntityManager em;
    protected final Class<T> entityType;

    public Repository(Class<T> entity) {

        entityType = entity;

        Map<String, String> properties = getProperties();
        em = Persistence
                .createEntityManagerFactory("airtng", properties)
                .createEntityManager();
    }

    /**
     * Returns all instances of the type.
     *
     * @return all entities
     */
    @SuppressWarnings("unchecked")
    public Iterable<T> findAll() {
        Query query = em.createQuery(
                String.format("SELECT e FROM %s e", entityType.getSimpleName()));

        return query.getResultList();
    }

    /**
     * Retrieves an entity by its id.
     *
     * @param id   The given id.
     *
     * @return the entity with the given id
     */
    public T find(long id) {
        T entity = em.find(entityType, id);
        em.refresh(entity);

        return entity;
    }

    /**
     * Saves a given entity. Use the returned instance for further operations.
     *
     * @param entity   The entity
     *
     * @return the saved entity
     */
    public T create(T entity) {
        getTransaction().begin();
        em.persist(entity);
        getTransaction().commit();

        return entity;
    }

    /**
     * Updates a given entity. Use the returned instance for further operations.
     *
     * @param entity   The entity
     *
     * @return the updated entity
     */
    public T update(T entity) {
        getTransaction().begin();
        T updatedEntity = em.merge(entity);
        getTransaction().commit();

        return updatedEntity;
    }

    /**
     * Deletes the entity.
     *
     * @param entity   The entity
     */
    public void delete(T entity) {
        getTransaction().begin();
        em.remove(entity);
        getTransaction().commit();
    }

    private EntityTransaction getTransaction() {
        return em.getTransaction();
    }

    private Map<String, String> getProperties() {
        Dotenv dotenv = Config.getDotenv();
        Map<String, String> config = new HashMap<>();

        if (dotenv.get("JDBC_URL") != null) {
            config.put("javax.persistence.jdbc.url", dotenv.get("JDBC_URL"));
        }

        if (dotenv.get("DB_USERNAME") != null) {
            config.put("javax.persistence.jdbc.user", dotenv.get("DB_USERNAME"));
        }

        if (dotenv.get("DB_PASSWORD") != null) {
            config.put("javax.persistence.jdbc.password", dotenv.get("DB_PASSWORD"));
        }

        return config;
    }
}
