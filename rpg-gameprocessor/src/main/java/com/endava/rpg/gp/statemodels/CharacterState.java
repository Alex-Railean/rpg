package com.endava.rpg.gp.statemodels;

import com.endava.rpg.gp.services.game.FormulaService;
import com.endava.rpg.persistence.models.Spell;
import org.springframework.stereotype.Component;

@Component
public class CharacterState extends State {

    private final FormulaService FORMULA;

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

    public CharacterState(FormulaService formula) {
        this.FORMULA = formula;
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
        this.strengthNextLevel = FORMULA.getNextLevelExp(strengthProgressLevel);
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
        this.agilityNextLevel = FORMULA.getNextLevelExp(agilityProgressLevel);
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
        this.intelligenceNextLevel = FORMULA.getNextLevelExp(intelligenceProgressLevel);
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
