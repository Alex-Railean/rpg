package com.endava.rpg.persistence.dao;

import com.endava.rpg.persistence.models.Magic;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MagicDao extends Dao<Magic> {

    @Autowired
    private MagicDao(SessionFactory sessionFactory) {
        super(Magic.class, sessionFactory);
    }
}
