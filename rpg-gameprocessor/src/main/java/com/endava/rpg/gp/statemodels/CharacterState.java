package com.endava.rpg.gp.statemodels;

import com.endava.rpg.gp.services.game.FormulaService;
import com.endava.rpg.persistence.models.ActionBar;
import com.endava.rpg.persistence.models.Character;
import com.endava.rpg.persistence.models.Progress;
import com.endava.rpg.persistence.models.Spell;
import com.endava.rpg.persistence.services.PersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CharacterState extends State {
    private static final Logger LOGGER = LoggerFactory.getLogger(CharacterState.class);

    @Autowired
    private PersistenceService PS;

    @Autowired
    private FormulaService FORMULA;

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

    private Integer hpRegeneration = 3;

    private Integer mpRegeneration = 5;

    private Integer energy = 100;

    private Integer energyRegeneration = 10;

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

    //TODO: Investigate the problem
//    @Autowired
//    public CharacterState(PersistenceService dbs, FormulaService formulaService) {
//        this.PS = dbs;
//        this.FORMULA = formulaService;
//    }

    public CharacterState defineCharacter(String characterName) {
        reloadCharacter(PS.getCharacterByName(characterName));
        LOGGER.info("Defined an Existing Character");
        return this;
    }

    public CharacterState defineNewCharacter(String characterName) {
        reloadCharacter(PS.saveCharacter(new Character(
                characterName,
                new Progress(),
                new ActionBar())));
        LOGGER.info("Defined a New Character");
        return this;
    }

    private void reloadCharacter(Character character) {
        this.characterName = character.getCharacterName();
        this.location = character.getLocation();
        this.strengthProgressLevel = character.getProgress().getStrengthProgressLevel();
        this.strengthNextLevel = FORMULA.getNextLevelExp(strengthProgressLevel);
        this.strengthProgress = character.getProgress().getStrengthProgress();
        this.agilityProgressLevel = character.getProgress().getAgilityProgressLevel();
        this.agilityNextLevel = FORMULA.getNextLevelExp(agilityProgressLevel);
        this.agilityProgress = character.getProgress().getAgilityProgress();
        this.intelligenceProgressLevel = character.getProgress().getIntelligenceProgressLevel();
        this.intelligenceNextLevel = FORMULA.getNextLevelExp(intelligenceProgressLevel);
        this.intelligenceProgress = character.getProgress().getIntelligenceProgress();
        this.characterLevel = calculateCharacterLevel();
        super.hp = FORMULA.getCharacterHp();
        super.currentHp = hp;
        super.mp = FORMULA.getCharacterMp();
        super.currentMp = mp;
        super.currentEnergy = energy;
        super.spell_1 = character.getActionBar().getSpell_1() == null ? getDefaultSpell(1) : character.getActionBar().getSpell_1();
        super.spell_2 = character.getActionBar().getSpell_2() == null ? getDefaultSpell(2) : character.getActionBar().getSpell_2();
        super.spell_3 = character.getActionBar().getSpell_3() == null ? getDefaultSpell(3) : character.getActionBar().getSpell_3();
        this.spell_4 = character.getActionBar().getSpell_4() == null ? getDefaultSpell() : character.getActionBar().getSpell_4();
        this.spell_5 = character.getActionBar().getSpell_5() == null ? getDefaultSpell() : character.getActionBar().getSpell_5();
        this.spell_6 = character.getActionBar().getSpell_6() == null ? getDefaultSpell() : character.getActionBar().getSpell_6();
        this.spell_7 = character.getActionBar().getSpell_7() == null ? getDefaultSpell() : character.getActionBar().getSpell_7();
        this.spell_8 = character.getActionBar().getSpell_8() == null ? getDefaultSpell() : character.getActionBar().getSpell_8();
        this.spell_9 = character.getActionBar().getSpell_9() == null ? getDefaultSpell() : character.getActionBar().getSpell_9();
        this.spell_10 = character.getActionBar().getSpell_10() == null ? getDefaultSpell() : character.getActionBar().getSpell_10();
        this.spell_11 = character.getActionBar().getSpell_11() == null ? getDefaultSpell() : character.getActionBar().getSpell_11();
        this.spell_12 = character.getActionBar().getSpell_12() == null ? getDefaultSpell() : character.getActionBar().getSpell_12();
        LOGGER.info("Character was reloaded");
    }

    private Spell getDefaultSpell(Integer spellPlace) {
        return PS.getSpellById(spellPlace);
    }

    private Spell getDefaultSpell() {
        return PS.getSpellById(4);
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

    public CharacterState setCharacterLevel(Integer characterLevel) {
        this.characterLevel = characterLevel;
        return this;
    }

    public CharacterState setCharacterName(String characterName) {
        this.characterName = characterName;
        return this;
    }

    public Integer getStrengthProgressLevel() {
        return strengthProgressLevel;
    }

    public CharacterState setStrengthProgressLevel(Integer strengthProgressLevel) {
        this.strengthProgressLevel = strengthProgressLevel;
        return this;
    }

    public Integer getStrengthProgress() {
        return strengthProgress;
    }

    public CharacterState setStrengthProgress(Integer strengthProgress) {
        this.strengthProgress = strengthProgress;
        return this;
    }

    public Integer getAgilityProgressLevel() {
        return agilityProgressLevel;
    }

    public CharacterState setAgilityProgressLevel(Integer agilityProgressLevel) {
        this.agilityProgressLevel = agilityProgressLevel;
        return this;
    }

    public Integer getAgilityProgress() {
        return agilityProgress;
    }

    public CharacterState setAgilityProgress(Integer agilityProgress) {
        this.agilityProgress = agilityProgress;
        return this;
    }

    public Integer getIntelligenceProgressLevel() {
        return intelligenceProgressLevel;
    }

    public CharacterState setIntelligenceProgressLevel(Integer intelligenceProgressLevel) {
        this.intelligenceProgressLevel = intelligenceProgressLevel;
        return this;
    }

    public Integer getIntelligenceProgress() {
        return intelligenceProgress;
    }

    public CharacterState setIntelligenceProgress(Integer intelligenceProgress) {
        this.intelligenceProgress = intelligenceProgress;
        return this;
    }

    public Spell getSpell_4() {
        return spell_4;
    }

    public CharacterState setSpell_4(Spell spell_4) {
        this.spell_4 = spell_4;
        return this;
    }

    public Spell getSpell_5() {
        return spell_5;
    }

    public CharacterState setSpell_5(Spell spell_5) {
        this.spell_5 = spell_5;
        return this;
    }

    public Spell getSpell_6() {
        return spell_6;
    }

    public CharacterState setSpell_6(Spell spell_6) {
        this.spell_6 = spell_6;
        return this;
    }

    public Spell getSpell_7() {
        return spell_7;
    }

    public CharacterState setSpell_7(Spell spell_7) {
        this.spell_7 = spell_7;
        return this;
    }

    public Spell getSpell_8() {
        return spell_8;
    }

    public CharacterState setSpell_8(Spell spell_8) {
        this.spell_8 = spell_8;
        return this;
    }

    public Spell getSpell_9() {
        return spell_9;
    }

    public CharacterState setSpell_9(Spell spell_9) {
        this.spell_9 = spell_9;
        return this;
    }

    public Spell getSpell_10() {
        return spell_10;
    }

    public CharacterState setSpell_10(Spell spell_10) {
        this.spell_10 = spell_10;
        return this;
    }

    public Spell getSpell_11() {
        return spell_11;
    }

    public CharacterState setSpell_11(Spell spell_11) {
        this.spell_11 = spell_11;
        return this;
    }

    public Spell getSpell_12() {
        return spell_12;
    }

    public CharacterState setSpell_12(Spell spell_12) {
        this.spell_12 = spell_12;
        return this;
    }

    public Integer getStrengthNextLevel() {
        return strengthNextLevel;
    }

    public CharacterState setStrengthNextLevel(Integer strengthNextLevel) {
        this.strengthNextLevel = strengthNextLevel;
        return this;
    }

    public Integer getAgilityNextLevel() {
        return agilityNextLevel;
    }

    public CharacterState setAgilityNextLevel(Integer agilityNextLevel) {
        this.agilityNextLevel = agilityNextLevel;
        return this;
    }

    public Integer getIntelligenceNextLevel() {
        return intelligenceNextLevel;
    }

    public CharacterState setIntelligenceNextLevel(Integer intelligenceNextLevel) {
        this.intelligenceNextLevel = intelligenceNextLevel;
        return this;
    }

    public Long getCurrentBattle() {
        return currentBattle;
    }

    public CharacterState setCurrentBattle(Long currentBattle) {
        this.currentBattle = currentBattle;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public CharacterState setLocation(String location) {
        this.location = location;
        return this;
    }

    @Override
    public Integer getHpRegeneration() {
        return hpRegeneration;
    }

    @Override
    public CharacterState setHpRegeneration(Integer hpRegeneration) {
        this.hpRegeneration = hpRegeneration;
        return this;
    }

    @Override
    public Integer getMpRegeneration() {
        return mpRegeneration;
    }

    @Override
    public CharacterState setMpRegeneration(Integer mpRegeneration) {
        this.mpRegeneration = mpRegeneration;
        return this;
    }

    @Override
    public Integer getEnergy() {
        return energy;
    }

    @Override
    public CharacterState setEnergy(Integer energy) {
        this.energy = energy;
        return this;
    }

    @Override
    public Integer getEnergyRegeneration() {
        return energyRegeneration;
    }

    @Override
    public CharacterState setEnergyRegeneration(Integer energyRegeneration) {
        this.energyRegeneration = energyRegeneration;
        return this;
    }

    @Override
    public Double getCriticalDmgCoefficient() {
        return criticalDmgCoefficient;
    }

    @Override
    public CharacterState setCriticalDmgCoefficient(Double criticalDmgCoefficient) {
        this.criticalDmgCoefficient = criticalDmgCoefficient;
        return this;
    }
}
