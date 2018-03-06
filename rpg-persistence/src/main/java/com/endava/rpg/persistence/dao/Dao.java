package com.endava.rpg.persistence.dao;

import com.endava.rpg.persistence.models.TableMapping;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class Dao<R extends TableMapping> {
    private Logger LOGGER = LoggerFactory.getLogger(Dao.class);

    private Class<R> RTC;

    private final SessionFactory sessionFactory;

    Dao(Class<R> representedTableClass, SessionFactory sessionFactory) {
        this.RTC = representedTableClass;
        this.sessionFactory = sessionFactory;
    }

    public <V> R getSingleWhere(String column, V value) {
        List<R> entities = getAllWhere(column, value);
        return entities.size() > 0 ? entities.get(0) : null;
    }

    public <V> List<R> getAllWhere(String columnName, V value) {
        List<R> list = new ArrayList<>();

        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("FROM " + RTC.getSimpleName() + " WHERE " + columnName + " = :columnValue");
            query.setParameter("columnValue", value);
            list = query.getResultList();
        } catch (Exception ex) {
            LOGGER.error("Error -> {}", ex.getMessage());
            LOGGER.debug("Full error -> {}", ex);
        }
        return list;
    }

    public List<R> getAll() {
        List<R> list = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("FROM " + RTC.getSimpleName());
            list = query.getResultList();
        } catch (Exception ex) {
            LOGGER.error("Error -> {}", ex.getMessage());
            LOGGER.debug("Full error -> {}", ex);
        }

        return list;
    }

    public R save(R entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            LOGGER.info(entity.getClass().getSimpleName() + " was successfully saved");
        } catch (Exception ex) {
            LOGGER.error("Error -> {}", ex.getMessage());
            LOGGER.debug("Full error -> {}", ex);
            return null;
        }

        return entity;
    }

    public R update(R entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
            LOGGER.info(entity.getClass().getSimpleName() + " was successfully updated");
        } catch (Exception ex) {
            LOGGER.error("Error -> {}", ex.getMessage());
            LOGGER.debug("Full error -> {}", ex);
            return null;
        }

        return entity;
    }
}
