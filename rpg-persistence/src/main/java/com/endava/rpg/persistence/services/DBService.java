package com.endava.rpg.persistence.services;

import com.endava.rpg.persistence.dao.impl.CreepDaoImpl;
import com.endava.rpg.persistence.dao.impl.ProgressDao;
import com.endava.rpg.persistence.dao.impl.SpellDaoImpl;
import com.endava.rpg.persistence.models.Character;
import com.endava.rpg.persistence.dao.impl.CharacterDaoImpl;
import com.endava.rpg.persistence.models.Creep;
import com.endava.rpg.persistence.models.Progress;
import com.endava.rpg.persistence.models.Spell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DBService {

    @Autowired
    private CharacterDaoImpl character;

    @Autowired
    private SpellDaoImpl spells;

    @Autowired
    private CreepDaoImpl creeps;

    @Autowired
    private ProgressDao progress;

    public Character saveCharacter(Character character){
        return this.character.save(character);
    }

    public Character getCharacterByName(String characterName){
        return this.character.getByName(characterName);
    }

    public Character updateCharacter(Character character){
        return this.character.update(character);
    }

    public List<Spell> getAllSpells(){
        return spells.getAll(Spell.class.getName());
    }

    public Spell saveSpell(Spell spell){
        return spells.save(spell);
    }

    public Spell getSpellById(Integer id){
        return spells.getById(id);
    }

    public Spell getSpellByName(String spellName){
        return spells.getByName(spellName);
    }

    public Creep saveCreep(Creep creep){
        return creeps.save(creep);
    }

    public List<Creep> getAllCreeps(){
        return creeps.getAll(Creep.class.getName());
    }

    public List<Creep> getAllCreepsFromLocation(String location){
        return creeps.getByLocation(location);
    }

    public Progress updateProgress(Progress progress){
        return this.progress.update(progress);
    }
}
