package com.endava.rpg.persistence.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.endava.rpg.persistence.models.Character;

import javax.persistence.Query;

@Repository
public class CharacterDaoImpl extends GenericDao<CharacterDaoImpl> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreepDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    public Character getByName(String characterName){
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("FROM Character WHERE characterName" + "=:value");
            query.setParameter("value", characterName);
            return (Character) query.getResultList().get(0);
        } catch (Exception ex) {
            LOGGER.error("Error -> {}", ex.getMessage());
            return null;
        }
    }
}
