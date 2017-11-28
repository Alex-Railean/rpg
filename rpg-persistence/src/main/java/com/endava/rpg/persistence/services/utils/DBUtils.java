package com.endava.rpg.persistence.services.utils;

import com.endava.rpg.persistence.models.Models;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.List;

@Component
public class DBUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(DBUtils.class);

    @Autowired
    private SessionFactory sessionFactory;

    public DBUtils() {
    }

    public <T, N> List<N> getEntryByValueFromDB(String tableName, String columnName, T value) {
        List<N> list = null;

        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("from " + tableName + " where " + columnName + " = :value");
            query.setParameter("value", value);
            list = query.getResultList();
        } catch (Exception ex) {
            LOGGER.error("Error -> ", ex.getMessage());
        }
        return list;
    }

    public Models saveToDB(Models entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            LOGGER.info(entity.getClass().getName() + " Successfully saved");
        } catch (Exception ex) {
            LOGGER.error("Error: {}", ex.getMessage());
            return null;
        }
        return entity;
    }

    public Models update(Models entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
            LOGGER.info(entity.getClass().getName() + " Successfully updated");
        } catch (Exception ex) {
            LOGGER.error("Error: {}", ex.getMessage());
            return null;
        }
        return entity;
    }
}
