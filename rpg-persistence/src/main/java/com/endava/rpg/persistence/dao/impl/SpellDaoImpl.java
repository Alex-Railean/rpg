package com.endava.rpg.persistence.dao.impl;

import com.endava.rpg.persistence.models.Spell;
import com.endava.rpg.persistence.services.utils.DBUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

@Repository
public class SpellDaoImpl extends GenericDao<SpellDaoImpl> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpellDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private DBUtils dbUtils;

    public Spell getByName(String spellName){
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("FROM Spell WHERE spellName" + "=:value");
            query.setParameter("value", spellName);
            return (Spell) query.getResultList().get(0);
        } catch (Exception ex) {
            LOGGER.error("Error -> {}", ex.getMessage());
            return null;
        }
    }

    public Spell getById(Integer id) {
        return (Spell) dbUtils.getEntryByValueFromDB(Spell.class.getName(), "spellId", id).get(0);
    }
}
