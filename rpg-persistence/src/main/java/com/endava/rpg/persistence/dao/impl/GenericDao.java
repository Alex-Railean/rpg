package com.endava.rpg.persistence.dao.impl;

import com.endava.rpg.persistence.models.Models;
import com.endava.rpg.persistence.services.utils.DBUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Component
public class GenericDao<T extends GenericDao<T>> {
    private Logger LOGGER = LoggerFactory.getLogger(GenericDao.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private DBUtils dbUtils;

    public <ModelType> List<ModelType> getAll(String tableName) {
        List<ModelType> list = new ArrayList<>();

        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("from " + tableName);
            list = query.getResultList();
        } catch (Exception ex) {
            LOGGER.info("Could not connect to DB");
        }
        return list;
    }

    public <ModelType extends Models> ModelType save(ModelType model) {
        return (ModelType) dbUtils.saveToDB(model);
    }

    public <ModelType extends Models> ModelType update(ModelType entity) {
        return (ModelType) dbUtils.update(entity);
    }
}
