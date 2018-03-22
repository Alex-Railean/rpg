package com.endava.rpg.gp.services.state;

import com.endava.rpg.gp.services.battle.spells.constants.AttributeType;
import com.endava.rpg.gp.services.battle.spells.constants.DefaultSpells;
import com.endava.rpg.gp.services.game.FormulaService;
import com.endava.rpg.gp.statemodels.CharacterState;
import com.endava.rpg.gp.statemodels.points.Attribute;
import com.endava.rpg.persistence.models.ActionBar;
import com.endava.rpg.persistence.models.Character;
import com.endava.rpg.persistence.models.Progress;
import com.endava.rpg.persistence.models.Spell;
import com.endava.rpg.persistence.services.PersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Map;

@Service
public class CharacterStateService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CharacterStateService.class);

    private PersistenceService ps;

    private CharacterState characterState;

    private ActionBarService actionBarService;

    private FormulaService formula;

    private TalentService talent;

    public CharacterState getCharacterState() {
        return characterState;
    }

    @Autowired
    private void setCharacterState(CharacterState characterState) {
        this.characterState = characterState;
    }

    public CharacterState setNewBattle(Long battleId) {
        LOGGER.info("New Battle was Defined");
        return characterState.setCurrentBattle(battleId);
    }

    public CharacterState resetBattle() {
        return characterState.setCurrentBattle(0L);
    }

    public Long getBattle() {
        return characterState.getCurrentBattle();
    }

    public CharacterState setNewLocation(String location) {
        LOGGER.info("New Location -> " + location);
        ps.updateCharacter(ps.getCharacterByName(characterState.getName()).setLocation(location));
        return characterState.setLocation(location);
    }

    public String getLocation() {
        return characterState.getLocation();
    }

    public <M extends Model> M getCharacterModel(M model) {
        Map<Integer, Spell> actionBar = actionBarService.getActionBarMap();
        model.addAttribute("characterName", characterState.getName())
                .addAttribute("characterLevel", characterState.getLevel())
                .addAttribute("hp", characterState.getHp().getValue())
                .addAttribute("currentHp", characterState.getHp().getCurrentValue())
                .addAttribute("mp", characterState.getMp().getValue())
                .addAttribute("currentMp", characterState.getMp().getCurrentValue())
                .addAttribute("energy", characterState.getEnergy().getValue())
                .addAttribute("currentEnergy", characterState.getEnergy().getCurrentValue())
                .addAttribute("actionBar", actionBar)
                .addAttribute("strengthLevel", characterState.getStrength().getProgressLevel())
                .addAttribute("strength", characterState.getStrength().getProgress())
                .addAttribute("strengthNextLevel", characterState.getStrength().getToNextLevel())
                .addAttribute("agilityLevel", characterState.getAgility().getProgressLevel())
                .addAttribute("agility", characterState.getAgility().getProgress())
                .addAttribute("agilityNextLevel", characterState.getAgility().getToNextLevel())
                .addAttribute("intelligenceLevel", characterState.getIntelligence().getProgressLevel())
                .addAttribute("intelligence", characterState.getIntelligence().getProgress())
                .addAttribute("intelligenceNextLevel", characterState.getIntelligence().getToNextLevel())
                .addAttribute("freePoints", characterState.getFreePoints())
                .addAttribute("shield", characterState.getShieldPoints());

        return model;
    }

    public boolean isCharacterDead() {
        return characterState.getHp().getCurrentValue() <= 0;
    }

    public boolean updateProgress(Integer additionalExp, Attribute stateAttribute, String type) {
        if (additionalExp == 0) {
            return true;
        }

        Character character = ps.getCharacterByName(characterState.getName());
        Progress progress = character.getProgress();

        if (stateAttribute.isItNextLevel(additionalExp)) {

            switch (type) {
                case AttributeType.STRENGTH:
                    progress.addStrengthProgressLevel(1)
                            .addStrengthProgress(additionalExp - stateAttribute.getToNextLevel());
                    break;
                case AttributeType.AGILITY:
                    progress.addAgilityProgressLevel(1)
                            .addAgilityProgress(additionalExp - stateAttribute.getToNextLevel());
                    break;

                case AttributeType.INTELLIGENCE:
                    progress.addIntelligenceProgressLevel(1)
                            .addIntelligenceProgress(additionalExp - stateAttribute.getToNextLevel());
                    break;
            }

            character.addFreePoints(1);
            LOGGER.info("Level Up!");

        } else {
            switch (type) {
                case AttributeType.STRENGTH:
                    progress.addStrengthProgress(additionalExp);
                    break;
                case AttributeType.AGILITY:
                    progress.addAgilityProgress(additionalExp);
                    break;

                case AttributeType.INTELLIGENCE:
                    progress.addIntelligenceProgress(additionalExp);
                    break;
            }

            stateAttribute.addProgress(additionalExp);
        }

        ps.updateCharacter(character);

        // TODO: Use this only for Lvl Up
        refreshCharacter();

        return stateAttribute.isLevelStable();
    }

    public CharacterState refreshCharacter() {
        return defineCharacter(characterState.getName());
    }

    public CharacterState defineCharacter(String characterName) {
        Character character;

        if ((character = ps.getCharacterByName(characterName)) != null) {
            talent.defineTalents(character);
            CharacterState characterState = reloadCharacter(character);
            talent.affect();
            LOGGER.info("An existing Character was defined");
            return characterState;
        }

        character = ps.saveCharacter(new Character(
                characterName,
                new Progress(),
                new ActionBar()));
        talent.createAll(character);
        ps.refreshChar(character);
        talent.defineTalents(character);
        CharacterState characterState = reloadCharacter(character);
        talent.affect();
        LOGGER.info("A New Character was Created");
        return characterState;
    }

    public <M extends Model> M getHeaderData(M model) {
        model.addAttribute("characterName", getCharacterState().getName())
                .addAttribute("characterLevel", getCharacterState().getLevel());
        return model;
    }

    private CharacterState reloadCharacter(Character character) {
        characterState.getStrength().setProgressLevel(character.getProgress().getStrengthProgressLevel())
                .setProgress(character.getProgress().getStrengthProgress());

        characterState.getAgility().setProgressLevel(character.getProgress().getAgilityProgressLevel())
                .setProgress(character.getProgress().getAgilityProgress());

        characterState.getIntelligence().setProgressLevel(character.getProgress().getIntelligenceProgressLevel())
                .setProgress(character.getProgress().getIntelligenceProgress());

        characterState.setLocation(character.getLocation())
                .setFreePoints(character.getFreePoints())
                .setName(character.getCharacterName())
                .setLevel(calculateCharacterLevel())
                .setSpell(0, character.getActionBar().getSpell_1() == null ? ps.getSpellByName(DefaultSpells.SWORD_ATTACK) : character.getActionBar().getSpell_1())
                .setSpell(1, character.getActionBar().getSpell_2() == null ? ps.getSpellByName(DefaultSpells.BOW_ATTACK) : character.getActionBar().getSpell_2())
                .setSpell(2, character.getActionBar().getSpell_3() == null ? ps.getSpellByName(DefaultSpells.FIRE_BALL) : character.getActionBar().getSpell_3())
                .setSpell(3, character.getActionBar().getSpell_4() == null ? getDefaultSpell() : character.getActionBar().getSpell_4())
                .setSpell(4, character.getActionBar().getSpell_5() == null ? getDefaultSpell() : character.getActionBar().getSpell_5())
                .setSpell(5, character.getActionBar().getSpell_6() == null ? getDefaultSpell() : character.getActionBar().getSpell_6())
                .setSpell(6, character.getActionBar().getSpell_7() == null ? getDefaultSpell() : character.getActionBar().getSpell_7())
                .setSpell(7, character.getActionBar().getSpell_8() == null ? getDefaultSpell() : character.getActionBar().getSpell_8())
                .setSpell(8, character.getActionBar().getSpell_9() == null ? getDefaultSpell() : character.getActionBar().getSpell_9())
                .setSpell(9, character.getActionBar().getSpell_10() == null ? getDefaultSpell() : character.getActionBar().getSpell_10())
                .setSpell(10, character.getActionBar().getSpell_11() == null ? getDefaultSpell() : character.getActionBar().getSpell_11())
                .setSpell(11, character.getActionBar().getSpell_12() == null ? getDefaultSpell() : character.getActionBar().getSpell_12());

        characterState.getHp().setValue(formula.getCharacterHp()).refresh();
        characterState.getMp().setValue(formula.getCharacterMp()).refresh();
        characterState.getEnergy().refresh();

        LOGGER.info("Character has been reloaded");
        return characterState;
    }

    public int getCharacterLevel() {
        return characterState.getLevel();
    }

    public String getCharacterName() {
        return characterState.getName();
    }

    private Spell getDefaultSpell() {
        return ps.getSpellByName("No spell");
    }

    private Integer calculateCharacterLevel() {
        return characterState.getAttributes().stream().mapToInt(Attribute::getProgressLevel).sum() - 2;
    }

    @Autowired
    private void setPs(PersistenceService ps) {
        this.ps = ps;
    }

    @Autowired
    private void setActionBarService(ActionBarService actionBarService) {
        this.actionBarService = actionBarService;
    }

    @Autowired
    private void setFormula(FormulaService formula) {
        this.formula = formula;
    }

    @Autowired
    public void setTalent(TalentService talent) {
        this.talent = talent;
    }
}
