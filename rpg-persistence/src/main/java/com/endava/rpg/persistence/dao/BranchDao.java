package com.endava.rpg.persistence.dao;

import com.endava.rpg.persistence.models.BranchEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BranchDao extends Dao<BranchEntity> {

    @Autowired
    private BranchDao(SessionFactory sessionFactory) {
        super(BranchEntity.class, sessionFactory);
    }
}
