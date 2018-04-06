package com.endava.rpg.persistence.dao;

import com.endava.rpg.persistence.models.Aspects;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AspectsDao extends Dao<Aspects> {

    @Autowired
    private AspectsDao(SessionFactory sessionFactory) {
        super(Aspects.class, sessionFactory);
    }
}
