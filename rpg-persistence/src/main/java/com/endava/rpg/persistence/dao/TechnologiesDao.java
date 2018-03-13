package com.endava.rpg.persistence.dao;

import com.endava.rpg.persistence.models.Technologies;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TechnologiesDao extends Dao<Technologies> {

    @Autowired
    private TechnologiesDao(SessionFactory sessionFactory) {
        super(Technologies.class, sessionFactory);
    }
}
