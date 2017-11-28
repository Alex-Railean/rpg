package com.endava.rpg.persistence.dao.impl;

import com.endava.rpg.persistence.models.Creep;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class CreepDaoImpl extends GenericDao<CharacterDaoImpl> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreepDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    public List<Creep> getByLocation(String locationName) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("FROM Creep WHERE creepLocation" + "=:value");
            query.setParameter("value", locationName);
            return (List<Creep>) query.getResultList();
        } catch (Exception ex) {
            LOGGER.error("Error -> {}", ex.getMessage());
            return null;
        }
    }
}
