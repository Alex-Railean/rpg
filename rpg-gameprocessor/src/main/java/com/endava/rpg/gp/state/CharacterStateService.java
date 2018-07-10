package com.endava.rpg.gp.state;

import com.endava.rpg.gp.battle.spells.constants.AttributeType;
import com.endava.rpg.gp.battle.spells.constants.DefaultSpells;
import com.endava.rpg.gp.game.FormulaService;
import com.endava.rpg.gp.statemodels.CharacterState;
import com.endava.rpg.gp.statemodels.points.Attribute;
import com.endava.rpg.persistence.models.ActionBar;
import com.endava.rpg.persistence.models.Character;
import com.endava.rpg.persistence.models.Progress;
import com.endava.rpg.persistence.models.Spell;
import com.endava.rpg.persistence.services.PersistenceService;
import com.endava.rpg.persistence.services.utils.DescribedSpell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Map;

//TODO: implement hardDefinition, mediumDefinition and softDefinition
@Service
public class CharacterStateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CharacterStateService.class);

    private static CharacterState CHAR_STATE;

    private PersistenceService PS;

    @Autowired
    private CharacterStateService(CharacterState characterState, PersistenceService ps) {
        CHAR_STATE = characterState;
        this.PS = ps;
    }

    public static CharacterState getCharacter() {
        return CHAR_STATE;
    }

    public static void dispelEffects() {
        CHAR_STATE.getEffects().clear();
    }

    public static String getCharName() {
        return CHAR_STATE.getName();
    }

    public static int getLvl() {
        return CHAR_STATE.getLevel();
    }

    public static boolean isCharDead() {
        return CHAR_STATE.getHp().getCurrentValue() <= 0;
    }

    public CharacterState setNewBattle(Long battleId) {
        LOGGER.info("New Battle was Defined");
        return CHAR_STATE.setCurrentBattle(battleId);
    }

    public CharacterState resetBattle() {
        return CHAR_STATE.setCurrentBattle(0L);
    }

    public Long getBattle() {
        return CHAR_STATE.getCurrentBattle();
    }

    public CharacterState setNewLocation(String location) {
        LOGGER.info("New Location -> " + location);
        PS.updateCharacter(PS.getCharacterByName(CHAR_STATE.getName()).setLocation(location));
        return CHAR_STATE.setLocation(location);
    }

    public String getLocation() {
        return CHAR_STATE.getLocation();
    }

    public <M extends Model> M getCharacterModel(M model) {
        Map<Integer, DescribedSpell> actionBar = ActionBarService.getActionBarMap();
        model.addAttribute("characterName", CHAR_STATE.getName())
                .addAttribute("characterLevel", CHAR_STATE.getLevel())
                .addAttribute("hp", CHAR_STATE.getHp().getValue())
                .addAttribute("currentHp", CHAR_STATE.getHp().getCurrentValue())
                .addAttribute("mp", CHAR_STATE.getMp().getValue())
                .addAttribute("currentMp", CHAR_STATE.getMp().getCurrentValue())
                .addAttribute("energy", CHAR_STATE.getEnergy().getValue())
                .addAttribute("currentEnergy", CHAR_STATE.getEnergy().getCurrentValue())
                .addAttribute("actionBar", actionBar)
                .addAttribute("strengthLevel", CHAR_STATE.getStrength().getProgressLevel())
                .addAttribute("strength", CHAR_STATE.getStrength().getProgress())
                .addAttribute("strengthNextLevel", CHAR_STATE.getStrength().getNextLevel())
                .addAttribute("agilityLevel", CHAR_STATE.getAgility().getProgressLevel())
                .addAttribute("agility", CHAR_STATE.getAgility().getProgress())
                .addAttribute("agilityNextLevel", CHAR_STATE.getAgility().getNextLevel())
                .addAttribute("intellectLevel", CHAR_STATE.getIntellect().getProgressLevel())
                .addAttribute("intellect", CHAR_STATE.getIntellect().getProgress())
                .addAttribute("intellectNextLevel", CHAR_STATE.getIntellect().getNextLevel())
                .addAttribute("freePoints", CHAR_STATE.getFreePoints())
                .addAttribute("shield", CHAR_STATE.getShieldPoints())
                .addAttribute("effects", CharacterStateService.getCharacter().getDisplayEffects());

        return model;
    }

    // TODO: BUG -> inmemory and DB exp isn't synchronized
    public void updateProgress(Integer additionalExp, Attribute stateAttribute, String type) {

        if (additionalExp == 0) {
            return;
        }

        Character character = PS.getCharacterByName(CHAR_STATE.getName());
        Progress progress = character.getProgress();

        do {
            if (stateAttribute.isItNextLevel(additionalExp)) {
                additionalExp -= stateAttribute.getToNextLevel();

                switch (type) {
                    case AttributeType.STRENGTH:
                        progress.addStrengthProgressLevel(1).setStrengthProgress(0);
                        break;
                    case AttributeType.AGILITY:
                        progress.addAgilityProgressLevel(1).setAgilityProgress(0);
                        break;

                    case AttributeType.INTELLECT:
                        progress.addIntellectProgressLevel(1).setIntellectProgress(0);
                        break;
                }

                character.addFreePoints(1);
                LOGGER.info("Level Up!");
                refreshCharacter();

            } else {
                switch (type) {
                    case AttributeType.STRENGTH:
                        progress.addStrengthProgress(additionalExp);
                        break;
                    case AttributeType.AGILITY:
                        progress.addAgilityProgress(additionalExp);
                        break;

                    case AttributeType.INTELLECT:
                        progress.addIntellectProgress(additionalExp);
                        break;
                }

                stateAttribute.addProgress(additionalExp);
                additionalExp = 0;
            }
        } while (additionalExp > 0);

        PS.updateCharacter(character);

        // TODO: Use this only for Lvl Up
        refreshCharacter();
    }

    public CharacterState refreshCharacter() {
        return defineCharacter(CHAR_STATE.getName());
    }

    public CharacterState defineCharacter(String characterName) {
        Character character;

        if ((character = PS.getCharacterByName(characterName)) != null) {
            CharacterState characterState = definitionActions(character);
            LOGGER.info("An existing Character was defined");
            return characterState;
        }

        character = PS.saveCharacter(new Character(
                characterName,
                new Progress(),
                new ActionBar()));
        TalentService.createAll(character);
        PS.refreshChar(character);
        CharacterState characterState = definitionActions(character);
        LOGGER.info("A New Character was Created");
        return characterState;
    }

    public <M extends Model> M getHeaderData(M model) {
        model.addAttribute("characterName", CHAR_STATE.getName())
                .addAttribute("characterLevel", CHAR_STATE.getLevel());
        return model;
    }

    private CharacterState definitionActions(Character character) {
        TalentService.defineTalents(character);
        CharacterState characterState = reloadCharacter(character);
        TalentService.affect();
        return characterState;
    }

    private CharacterState reloadCharacter(Character character) {
        CHAR_STATE.getStrength().setProgressLevel(character.getProgress().getStrengthProgressLevel())
                .setProgress(character.getProgress().getStrengthProgress());

        CHAR_STATE.getAgility().setProgressLevel(character.getProgress().getAgilityProgressLevel())
                .setProgress(character.getProgress().getAgilityProgress());

        CHAR_STATE.getIntellect().setProgressLevel(character.getProgress().getIntellectProgressLevel())
                .setProgress(character.getProgress().getIntellectProgress());

        CHAR_STATE.setLocation(character.getLocation())
                .setFreePoints(character.getFreePoints())
                .setName(character.getCharacterName())
                .setLevel(calculateCharacterLevel())
                .setSpell(0, character.getActionBar().getSpell_1() == null ? PS.getSpellByName(DefaultSpells.SWORD_ATTACK) : character.getActionBar().getSpell_1())
                .setSpell(1, character.getActionBar().getSpell_2() == null ? PS.getSpellByName(DefaultSpells.BOW_ATTACK) : character.getActionBar().getSpell_2())
                .setSpell(2, character.getActionBar().getSpell_3() == null ? PS.getSpellByName(DefaultSpells.FIRE_BALL) : character.getActionBar().getSpell_3())
                .setSpell(3, character.getActionBar().getSpell_4() == null ? getDefaultSpell() : character.getActionBar().getSpell_4())
                .setSpell(4, character.getActionBar().getSpell_5() == null ? getDefaultSpell() : character.getActionBar().getSpell_5())
                .setSpell(5, character.getActionBar().getSpell_6() == null ? getDefaultSpell() : character.getActionBar().getSpell_6())
                .setSpell(6, character.getActionBar().getSpell_7() == null ? getDefaultSpell() : character.getActionBar().getSpell_7())
                .setSpell(7, character.getActionBar().getSpell_8() == null ? getDefaultSpell() : character.getActionBar().getSpell_8())
                .setSpell(8, character.getActionBar().getSpell_9() == null ? getDefaultSpell() : character.getActionBar().getSpell_9())
                .setSpell(9, character.getActionBar().getSpell_10() == null ? getDefaultSpell() : character.getActionBar().getSpell_10())
                .setSpell(10, character.getActionBar().getSpell_11() == null ? getDefaultSpell() : character.getActionBar().getSpell_11())
                .setSpell(11, character.getActionBar().getSpell_12() == null ? getDefaultSpell() : character.getActionBar().getSpell_12());

        CHAR_STATE.getHp().setValue(FormulaService.getCharacterHp()).refresh();
        CHAR_STATE.getMp().setValue(FormulaService.getCharacterMp()).refresh();
        CHAR_STATE.getEnergy().refresh();

        LOGGER.info("Character has been reloaded");
        return CHAR_STATE;
    }

    private Spell getDefaultSpell() {
        return PS.getSpellByName("No spell");
    }

    private Integer calculateCharacterLevel() {
        return CHAR_STATE.getAttributes().stream()
                .mapToInt(Attribute::getProgressLevel)
                .sum() - 2;
    }
}
