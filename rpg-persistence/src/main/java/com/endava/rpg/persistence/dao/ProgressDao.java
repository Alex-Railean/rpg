package com.endava.rpg.persistence.dao;

import com.endava.rpg.persistence.models.Progress;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProgressDao extends Dao<Progress> {

    @Autowired
    public ProgressDao(SessionFactory sessionFactory) {
        super(Progress.class, sessionFactory);
    }
}
