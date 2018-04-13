package com.endava.rpg.persistence.dao;

import com.endava.rpg.persistence.models.Shadow;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ShadowDao extends Dao<Shadow> {

    @Autowired
    private ShadowDao(SessionFactory sessionFactory) {
        super(Shadow.class, sessionFactory);
    }
}
