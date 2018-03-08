package com.endava.rpg.persistence.dao;

import com.endava.rpg.persistence.models.Character;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CharacterDao extends Dao<Character> {

    @Autowired
    private CharacterDao(SessionFactory sessionFactory) {
        super(Character.class, sessionFactory);
    }
}
