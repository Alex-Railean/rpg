package com.endava.rpg.persistence.dao;

import com.endava.rpg.persistence.models.Spell;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SpellDao extends Dao<Spell> {

    @Autowired
    private SpellDao(SessionFactory sessionFactory) {
        super(Spell.class, sessionFactory);
    }
}
