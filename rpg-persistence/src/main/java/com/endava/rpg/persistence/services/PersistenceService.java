package com.endava.rpg.persistence.services;

import com.endava.rpg.persistence.dao.*;
import com.endava.rpg.persistence.models.*;
import com.endava.rpg.persistence.models.Character;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersistenceService {

    private final BranchDao BRANCHES;

    private final CharacterDao CHARACTER;

    private final SpellDao SPELLS;

    private final CreepDao CREEPS;

    private final EffectCoreDao EFFECTS;

    @Autowired
    private PersistenceService(BranchDao branch,
                               CharacterDao character,
                               SpellDao spells,
                               CreepDao creeps,
                               EffectCoreDao effect) {
        this.BRANCHES = branch;
        this.CHARACTER = character;
        this.SPELLS = spells;
        this.CREEPS = creeps;
        this.EFFECTS = effect;
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
        return EFFECTS.save(e);
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

    public BranchEntity saveBranch(BranchEntity branch) {
        return BRANCHES.save(branch.calculateTotalPoints());
    }

    public BranchEntity updateBranch(BranchEntity branch) {
        return BRANCHES.update(branch.calculateTotalPoints());
    }

    public Character refreshChar(Character character) {
        return CHARACTER.refresh(character);
    }

    public <E extends BranchEntity> E getBranchOf(Class<E> entity, Character c) {
        return BRANCHES.getSingleWhere(entity, "character", c);
    }

    public List<Spell> getBranchSpells(String branchName) {
        return SPELLS.getBranchSpells(branchName);
    }

    public EffectCore getEffect(String name) {
        return EFFECTS.getSingleWhere("name", name);
    }

    public List<EffectCore> getAllEffects() {
        return EFFECTS.getAll();
    }
}
