package com.endava.rpg.persistence.services;

import com.endava.rpg.persistence.dao.CreepDao;
import com.endava.rpg.persistence.dao.ProgressDao;
import com.endava.rpg.persistence.dao.SpellDao;
import com.endava.rpg.persistence.models.Character;
import com.endava.rpg.persistence.dao.CharacterDao;
import com.endava.rpg.persistence.models.Creep;
import com.endava.rpg.persistence.models.Progress;
import com.endava.rpg.persistence.models.Spell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersistenceService {

    private final CharacterDao CHARACTER;

    private final SpellDao SPELLS;

    private final CreepDao CREEPS;

    private final ProgressDao PROGRESS;

    @Autowired
    public PersistenceService(CharacterDao character, SpellDao spells, CreepDao creeps, ProgressDao progress) {
        this.CHARACTER = character;
        this.SPELLS = spells;
        this.CREEPS = creeps;
        this.PROGRESS = progress;
    }

    public Character saveCharacter(Character character){
        return CHARACTER.save(character);
    }

    public Character getCharacterByName(String name){
        return CHARACTER.getSingleWhere("characterName", name);
    }

    public Character updateCharacter(Character character){
        return CHARACTER.update(character);
    }

    public List<Spell> getAllSpells(){
        return SPELLS.getAll();
    }

    public Spell saveSpell(Spell spell){
        return SPELLS.save(spell);
    }

    public Spell getSpellById(Integer id){
        return SPELLS.getSingleWhere("spellId", id);
    }

    public Spell getSpellByName(String name){
        return SPELLS.getSingleWhere("spellName", name);
    }

    public Creep saveCreep(Creep creep){
        return CREEPS.save(creep);
    }

    public List<Creep> getAllCreeps(){
        return CREEPS.getAll();
    }

    public List<Creep> getCreepsFromLocation(String location){
        return CREEPS.getAllWhere("creepLocation", location);
    }

    public Progress updateProgress(Progress progress){
        return PROGRESS.update(progress);
    }
}
