package com.endava.rpg.persistence.dao;

import com.endava.rpg.persistence.models.Arms;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ArmsDao extends Dao<Arms> {

    @Autowired
    private ArmsDao(SessionFactory sessionFactory) {
        super(Arms.class, sessionFactory);
    }
}
