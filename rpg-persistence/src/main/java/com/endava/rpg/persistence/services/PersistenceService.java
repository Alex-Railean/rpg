package com.endava.rpg.persistence.services;

import com.endava.rpg.persistence.dao.*;
import com.endava.rpg.persistence.models.*;
import com.endava.rpg.persistence.models.Character;
import com.endava.rpg.persistence.services.utils.constants.BranchAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersistenceService {

    private final BranchDao BRANCH_DAO;

    private final CharacterDao CHARACTER;

    private final SpellDao SPELLS;

    private final CreepDao CREEPS;

    private final ProgressDao PROGRESS;

    private final TechnologiesDao TECHNOLOGIES;

    private final EffectCoreDao EFFECT;

    @Autowired
    private PersistenceService(BranchDao branch_dao,
                               CharacterDao character,
                               SpellDao spells,
                               CreepDao creeps,
                               ProgressDao progress,
                               TechnologiesDao technologies,
                               EffectCoreDao effect) {
        this.BRANCH_DAO = branch_dao;
        this.CHARACTER = character;
        this.SPELLS = spells;
        this.CREEPS = creeps;
        this.PROGRESS = progress;
        this.TECHNOLOGIES = technologies;
        this.EFFECT = effect;
    }

    public Character saveCharacter(Character character) {
        return CHARACTER.save(character);
    }

    public Character getCharacterByName(String name) {
        return CHARACTER.getSingleWhere("characterName", name);
    }

    public Character updateCharacter(Character character) {
        return CHARACTER.update(character);
    }

    public List<Spell> getAllSpells() {
        return SPELLS.getAll();
    }

    public Spell saveSpell(Spell spell) {
        return SPELLS.save(spell);
    }

    public EffectCore saveEffect(EffectCore e) {
        return EFFECT.save(e);
    }

    public Spell getSpellById(Integer id) {
        return SPELLS.getSingleWhere("spellId", id);
    }

    public Spell getSpellByName(String name) {
        return SPELLS.getSingleWhere("spellName", name);
    }

    public Creep saveCreep(Creep creep) {
        return CREEPS.save(creep);
    }

    public List<Creep> getAllCreeps() {
        return CREEPS.getAll();
    }

    public List<Creep> getCreepsFromLocation(String location) {
        return CREEPS.getAllWhere("creepLocation", location);
    }

    public Progress updateProgress(Progress progress) {
        return PROGRESS.update(progress);
    }

    public boolean deleteAllSpells() {
        return SPELLS.deleteAll();
    }

    public BranchEntity saveBranch(BranchEntity branch) {
        return BRANCH_DAO.save(branch.calculateTotalPoints());
    }

    public BranchEntity updateBranch(BranchEntity branch) {
        return BRANCH_DAO.update(branch.calculateTotalPoints());
    }

    public Character refreshChar(Character character) {
        return CHARACTER.refresh(character);
    }

    public Technologies getTechOf(Character character) {
        return TECHNOLOGIES.getSingleWhere("character", character);
    }

    public List<Spell> getTechSpells() {
        return SPELLS.getAllWhere("branch", BranchAttribute.TECHNOLOGIES.NAME);
    }

    public EffectCore getEffect(String name) {
        return EFFECT.getSingleWhere("name", name);
    }

    public List<EffectCore> getAllEffects() {
        return EFFECT.getAll();
    }
}
