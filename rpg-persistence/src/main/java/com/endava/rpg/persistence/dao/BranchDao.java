package com.endava.rpg.persistence.dao;

import com.endava.rpg.persistence.models.BranchEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BranchDao extends Dao<BranchEntity> {
    private final SessionFactory SESSION_FACTORY;

    private Logger LOGGER = LoggerFactory.getLogger(BranchDao.class);

    @Autowired
    private BranchDao(SessionFactory sessionFactory) {
        super(BranchEntity.class, sessionFactory);
        this.SESSION_FACTORY = sessionFactory;
    }

    public <E extends BranchEntity, V> E getSingleWhere(Class<E> entity, String columnName, V value) {
        List<E> entities = getAllWhere(entity, columnName, value);
        return entities.size() > 0 ? entities.get(0) : null;
    }

    private <E extends BranchEntity, V> List<E> getAllWhere(Class<E> entity, String columnName, V value) {
        List<E> list = new ArrayList<>();

        try (Session session = SESSION_FACTORY.openSession()) {
            Query query = session.createQuery("FROM " + entity.getSimpleName() + " WHERE " + columnName + " = :columnValue");
            query.setParameter("columnValue", value);
            list = query.getResultList();
        } catch (Exception ex) {
            LOGGER.error("Error -> {}", ex.getMessage());
            LOGGER.debug("Full error -> {}", ex);
        }
        return list;
    }

    public <V> BranchEntity getSingleWhere(String columnName, V value) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    public <V> List<BranchEntity> getAllWhere(String columnName, V value) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    public List<BranchEntity> getAll() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    public boolean deleteAll() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
