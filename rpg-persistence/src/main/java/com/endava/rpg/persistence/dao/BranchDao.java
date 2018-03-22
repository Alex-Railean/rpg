package com.endava.rpg.persistence.dao;

import com.endava.rpg.persistence.models.BranchEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BranchDao extends Dao<BranchEntity> {

    @Autowired
    private BranchDao(SessionFactory sessionFactory) {
        super(BranchEntity.class, sessionFactory);
    }

    public <V> BranchEntity getSingleWhere(String columnName, V value) {
        throw new UnsupportedOperationException();
    }

    public <V> List<BranchEntity> getAllWhere(String columnName, V value) {
        throw new UnsupportedOperationException();
    }

    public List<BranchEntity> getAll() {
        throw new UnsupportedOperationException();
    }

    public boolean deleteAll() {
        throw new UnsupportedOperationException();
    }
}
