package com.endava.rpg.persistence.dao;

import com.endava.rpg.persistence.models.Creep;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CreepDao extends Dao<Creep> {

    @Autowired
    public CreepDao(SessionFactory sessionFactory) {
        super(Creep.class, sessionFactory);
    }
}
