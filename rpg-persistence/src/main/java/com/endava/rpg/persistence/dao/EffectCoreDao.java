package com.endava.rpg.persistence.dao;

import com.endava.rpg.persistence.models.EffectCore;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EffectCoreDao extends Dao<EffectCore> {

    @Autowired
    private EffectCoreDao(SessionFactory sessionFactory) {
        super(EffectCore.class, sessionFactory);
    }
}
