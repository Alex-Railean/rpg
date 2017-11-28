package com.endava.rpg.gp.statemodels;

import com.endava.rpg.gp.services.game.FormulaService;
import com.endava.rpg.persistence.models.ActionBar;
import com.endava.rpg.persistence.models.Character;
import com.endava.rpg.persistence.models.Progress;
import com.endava.rpg.persistence.models.Spell;
import com.endava.rpg.persistence.services.DBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CurrentCharacter {
    private static final Logger LOGGER = LoggerFactory.getLogger(CurrentCharacter.class);

    @Autowired
    private DBService dbs;

    @Autowired
    private FormulaService formulaService;

    private String characterName;

    private Integer characterLevel;

    private Integer strengthProgressLevel;

    private Integer strengthNextLevel;

    private Integer strengthProgress;

    private Integer agilityProgressLevel;

    private Integer agilityNextLevel;

    private Integer agilityProgress;

    private Integer intelligenceNextLevel;

    private Integer intelligenceProgressLevel;

    private Integer intelligenceProgress;

    private Integer hp;

    private Integer currentHp;

    private Integer hpRegeneration = 3;

    private Integer mp;

    private Integer currentMp;

    private Integer mpRegeneration = 5;

    private Integer energy = 100;

    private Integer currentEnergy = 100;

    private Integer energyRegeneration = 10;

    private Spell spell_1;

    private Spell spell_2;

    private Spell spell_3;

    private Spell spell_4;

    private Spell spell_5;

    private Spell spell_6;

    private Spell spell_7;

    private Spell spell_8;

    private Spell spell_9;

    private Spell spell_10;

    private Spell spell_11;

    private Spell spell_12;

    private Long currentBattle;

    private String location;

    private Double criticalDmgCoefficient = 1.8;

    public CurrentCharacter defineCharacter(String characterName) {
        reloadCharacter(dbs.getCharacterByName(characterName));
        LOGGER.info("Defined an Existing Character");
        return this;
    }

    public CurrentCharacter defineNewCharacter(String characterName) {
        reloadCharacter(dbs.saveCharacter(new Character(
                characterName,
                new Progress(),
                new ActionBar())));
        LOGGER.info("Defined a New Character");
        return this;
    }

    private void reloadCharacter(Character character) {
        this.characterName = character.getCharacterName();
        this.location = character.getLocation();
        this.strengthProgressLevel = character.getProgressId().getStrengthProgressLevel();
        this.strengthNextLevel = formulaService.getNextLevelExp(strengthProgressLevel);
        this.strengthProgress = character.getProgressId().getStrengthProgress();
        this.agilityProgressLevel = character.getProgressId().getAgilityProgressLevel();
        this.agilityNextLevel = formulaService.getNextLevelExp(agilityProgressLevel);
        this.agilityProgress = character.getProgressId().getAgilityProgress();
        this.intelligenceProgressLevel = character.getProgressId().getIntelligenceProgressLevel();
        this.intelligenceNextLevel = formulaService.getNextLevelExp(intelligenceProgressLevel);
        this.intelligenceProgress = character.getProgressId().getIntelligenceProgress();
        this.characterLevel = calculateCharacterLevel();
        this.hp = formulaService.getCharacterHp();
        this.currentHp = hp;
        this.mp = formulaService.getCharacterMp();
        this.currentMp = mp;
        this.currentEnergy = energy;
        this.spell_1 = character.getActionBar().getSpellId_1() == null ? getDefaultSpell(1) : character.getActionBar().getSpellId_1();
        this.spell_2 = character.getActionBar().getSpellId_2() == null ? getDefaultSpell(2) : character.getActionBar().getSpellId_2();
        this.spell_3 = character.getActionBar().getSpellId_3() == null ? getDefaultSpell(3) : character.getActionBar().getSpellId_3();
        this.spell_4 = character.getActionBar().getSpellId_4() == null ? getDefaultSpell() : character.getActionBar().getSpellId_4();
        this.spell_5 = character.getActionBar().getSpellId_5() == null ? getDefaultSpell() : character.getActionBar().getSpellId_5();
        this.spell_6 = character.getActionBar().getSpellId_6() == null ? getDefaultSpell() : character.getActionBar().getSpellId_6();
        this.spell_7 = character.getActionBar().getSpellId_7() == null ? getDefaultSpell() : character.getActionBar().getSpellId_7();
        this.spell_8 = character.getActionBar().getSpellId_8() == null ? getDefaultSpell() : character.getActionBar().getSpellId_8();
        this.spell_9 = character.getActionBar().getSpellId_9() == null ? getDefaultSpell() : character.getActionBar().getSpellId_9();
        this.spell_10 = character.getActionBar().getSpellId_10() == null ? getDefaultSpell() : character.getActionBar().getSpellId_10();
        this.spell_11 = character.getActionBar().getSpellId_11() == null ? getDefaultSpell() : character.getActionBar().getSpellId_11();
        this.spell_12 = character.getActionBar().getSpellId_12() == null ? getDefaultSpell() : character.getActionBar().getSpellId_12();
        LOGGER.info("Character is Reloaded");
    }

    private Spell getDefaultSpell(Integer spellPlace) {
        return dbs.getSpellById(spellPlace);
    }

    private Spell getDefaultSpell() {
        return dbs.getSpellById(4);
    }

    private Integer calculateCharacterLevel() {
        return getStrengthProgressLevel() + getAgilityProgressLevel() + getIntelligenceProgressLevel() - 2;
    }

    public String getCharacterName() {
        return characterName;
    }

    public Integer getCharacterLevel() {
        return characterLevel;
    }

    public CurrentCharacter setCharacterLevel(Integer characterLevel) {
        this.characterLevel = characterLevel;
        return this;
    }

    public CurrentCharacter setCharacterName(String characterName) {
        this.characterName = characterName;
        return this;
    }

    public Integer getStrengthProgressLevel() {
        return strengthProgressLevel;
    }

    public CurrentCharacter setStrengthProgressLevel(Integer strengthProgressLevel) {
        this.strengthProgressLevel = strengthProgressLevel;
        return this;
    }

    public Integer getStrengthProgress() {
        return strengthProgress;
    }

    public CurrentCharacter setStrengthProgress(Integer strengthProgress) {
        this.strengthProgress = strengthProgress;
        return this;
    }

    public Integer getAgilityProgressLevel() {
        return agilityProgressLevel;
    }

    public CurrentCharacter setAgilityProgressLevel(Integer agilityProgressLevel) {
        this.agilityProgressLevel = agilityProgressLevel;
        return this;
    }

    public Integer getAgilityProgress() {
        return agilityProgress;
    }

    public CurrentCharacter setAgilityProgress(Integer agilityProgress) {
        this.agilityProgress = agilityProgress;
        return this;
    }

    public Integer getIntelligenceProgressLevel() {
        return intelligenceProgressLevel;
    }

    public CurrentCharacter setIntelligenceProgressLevel(Integer intelligenceProgressLevel) {
        this.intelligenceProgressLevel = intelligenceProgressLevel;
        return this;
    }

    public Integer getIntelligenceProgress() {
        return intelligenceProgress;
    }

    public CurrentCharacter setIntelligenceProgress(Integer intelligenceProgress) {
        this.intelligenceProgress = intelligenceProgress;
        return this;
    }

    public Integer getHp() {
        return hp;
    }

    public Integer getMp() {
        return mp;
    }

    public Integer getEnergy() {
        return energy;
    }

    public Spell getSpell_1() {
        return spell_1;
    }

    public CurrentCharacter setSpell_1(Spell spell_1) {
        this.spell_1 = spell_1;
        return this;
    }

    public Spell getSpell_2() {
        return spell_2;
    }

    public CurrentCharacter setSpell_2(Spell spell_2) {
        this.spell_2 = spell_2;
        return this;
    }

    public Spell getSpell_3() {
        return spell_3;
    }

    public CurrentCharacter setSpell_3(Spell spell_3) {
        this.spell_3 = spell_3;
        return this;
    }

    public Spell getSpell_4() {
        return spell_4;
    }

    public CurrentCharacter setSpell_4(Spell spell_4) {
        this.spell_4 = spell_4;
        return this;
    }

    public Spell getSpell_5() {
        return spell_5;
    }

    public CurrentCharacter setSpell_5(Spell spell_5) {
        this.spell_5 = spell_5;
        return this;
    }

    public Spell getSpell_6() {
        return spell_6;
    }

    public CurrentCharacter setSpell_6(Spell spell_6) {
        this.spell_6 = spell_6;
        return this;
    }

    public Spell getSpell_7() {
        return spell_7;
    }

    public CurrentCharacter setSpell_7(Spell spell_7) {
        this.spell_7 = spell_7;
        return this;
    }

    public Spell getSpell_8() {
        return spell_8;
    }

    public CurrentCharacter setSpell_8(Spell spell_8) {
        this.spell_8 = spell_8;
        return this;
    }

    public Spell getSpell_9() {
        return spell_9;
    }

    public CurrentCharacter setSpell_9(Spell spell_9) {
        this.spell_9 = spell_9;
        return this;
    }

    public Spell getSpell_10() {
        return spell_10;
    }

    public CurrentCharacter setSpell_10(Spell spell_10) {
        this.spell_10 = spell_10;
        return this;
    }

    public Spell getSpell_11() {
        return spell_11;
    }

    public CurrentCharacter setSpell_11(Spell spell_11) {
        this.spell_11 = spell_11;
        return this;
    }

    public Spell getSpell_12() {
        return spell_12;
    }

    public CurrentCharacter setSpell_12(Spell spell_12) {
        this.spell_12 = spell_12;
        return this;
    }

    public Integer getStrengthNextLevel() {
        return strengthNextLevel;
    }

    public CurrentCharacter setStrengthNextLevel(Integer strengthNextLevel) {
        this.strengthNextLevel = strengthNextLevel;
        return this;
    }

    public Integer getAgilityNextLevel() {
        return agilityNextLevel;
    }

    public CurrentCharacter setAgilityNextLevel(Integer agilityNextLevel) {
        this.agilityNextLevel = agilityNextLevel;
        return this;
    }

    public Integer getIntelligenceNextLevel() {
        return intelligenceNextLevel;
    }

    public CurrentCharacter setIntelligenceNextLevel(Integer intelligenceNextLevel) {
        this.intelligenceNextLevel = intelligenceNextLevel;
        return this;
    }

    public CurrentCharacter setHp(Integer hp) {
        this.hp = hp;
        return this;
    }

    public CurrentCharacter setMp(Integer mp) {
        this.mp = mp;
        return this;
    }

    public CurrentCharacter setEnergy(Integer energy) {
        this.energy = energy;
        return this;
    }

    public Integer getHpRegeneration() {
        return hpRegeneration;
    }

    public CurrentCharacter setHpRegeneration(Integer hpRegeneration) {
        this.hpRegeneration = hpRegeneration;
        return this;
    }

    public Integer getMpRegeneration() {
        return mpRegeneration;
    }

    public CurrentCharacter setMpRegeneration(Integer mpRegeneration) {
        this.mpRegeneration = mpRegeneration;
        return this;
    }

    public Integer getEnergyRegeneration() {
        return energyRegeneration;
    }

    public CurrentCharacter setEnergyRegeneration(Integer energyRegeneration) {
        this.energyRegeneration = energyRegeneration;
        return this;
    }

    public Long getCurrentBattle() {
        return currentBattle;
    }

    public CurrentCharacter setCurrentBattle(Long currentBattle) {
        this.currentBattle = currentBattle;
        return this;
    }

    public Integer getCurrentHp() {
        return currentHp;
    }

    public CurrentCharacter setCurrentHp(Integer currentHp) {
        this.currentHp = currentHp;
        return this;
    }

    public Integer getCurrentMp() {
        return currentMp;
    }

    public CurrentCharacter setCurrentMp(Integer currentMp) {
        this.currentMp = currentMp;
        return this;
    }

    public Integer getCurrentEnergy() {
        return currentEnergy;
    }

    public CurrentCharacter setCurrentEnergy(Integer currentEnergy) {
        this.currentEnergy = currentEnergy;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public CurrentCharacter setLocation(String location) {
        this.location = location;
        return this;
    }

    public Double getCriticalDmgCoefficient() {
        return criticalDmgCoefficient;
    }

    public CurrentCharacter setCriticalDmgCoefficient(Double criticalDmgCoefficient) {
        this.criticalDmgCoefficient = criticalDmgCoefficient;
        return this;
    }
}
