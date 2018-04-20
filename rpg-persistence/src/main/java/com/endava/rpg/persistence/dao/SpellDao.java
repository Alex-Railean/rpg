package com.endava.rpg.persistence.dao;

import com.endava.rpg.persistence.models.BranchEntity;
import com.endava.rpg.persistence.models.Spell;
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
public class SpellDao extends Dao<Spell> {

    private final SessionFactory SESSION_FACTORY;

    private Logger LOGGER = LoggerFactory.getLogger(SpellDao.class);

    @Autowired
    private SpellDao(SessionFactory sessionFactory, SessionFactory session_factory) {
        super(Spell.class, sessionFactory);
        this.SESSION_FACTORY = session_factory;
    }

    public <B extends BranchEntity> List<Spell> getBranchSpells(String branchName) {
        List<Spell> list = new ArrayList<>();

        try (Session session = SESSION_FACTORY.openSession()) {
            Query query = session.createQuery("FROM Spell WHERE branch = :branchName");
            query.setParameter("branchName", branchName);
            list = query.getResultList();
        } catch (Exception ex) {
            LOGGER.error("Error -> {}", ex.getMessage());
            LOGGER.debug("Full error -> {}", ex);
        }
        return list;
    }
}
