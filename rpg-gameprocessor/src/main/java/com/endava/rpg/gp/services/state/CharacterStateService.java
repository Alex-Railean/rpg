package com.endava.rpg.gp.services.state;

import com.endava.rpg.gp.services.game.FormulaService;
import com.endava.rpg.gp.statemodels.CharacterState;
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

import javax.annotation.PostConstruct;
import java.util.Map;

@Service
public class CharacterStateService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CharacterStateService.class);

    private PersistenceService ps;

    private CharacterState characterState;

    private ActionBarService actionBarService;

    private FormulaService formula;

    @PostConstruct
    public Character saveTestCharacter() {
        if ((ps.getCharacterByName("Admin") == null)) {
            LOGGER.info("Test Admin User is Created");
            return ps.saveCharacter(new Character(
                    "Admin",
                    new Progress(),
                    new ActionBar()));

        }
        return ps.getCharacterByName("Admin");
    }

    public CharacterState getCharacterState() {
        return characterState;
    }

    public CharacterState setNewBattle(Long battleId) {
        LOGGER.info("New Battle was Defined");
        return characterState.setCurrentBattle(battleId);
    }

    public Long getBattle() {
        return characterState.getCurrentBattle();
    }

    public CharacterState setNewLocation(String location) {
        LOGGER.info("New Location -> " + location);
        ps.updateCharacter(ps.getCharacterByName(characterState.getCharacterName()).setLocation(location));
        return characterState.setLocation(location);
    }

    public String getLocation() {
        return characterState.getLocation();
    }

    public Model getCharacterModel(Model model) {
        Map<Integer, Spell> actionBar = actionBarService.getActionBarMap();
        model.addAttribute("characterName", characterState.getCharacterName());
        model.addAttribute("characterLevel", characterState.getCharacterLevel());
        model.addAttribute("hp", characterState.getHp());
        model.addAttribute("currentHp", characterState.getCurrentHp());
        model.addAttribute("mp", characterState.getMp());
        model.addAttribute("currentMp", characterState.getCurrentMp());
        model.addAttribute("energy", characterState.getEnergy());
        model.addAttribute("currentEnergy", characterState.getCurrentEnergy());
        model.addAttribute("actionBar", actionBar);
        model.addAttribute("strengthLevel", characterState.getStrengthProgressLevel());
        model.addAttribute("strength", characterState.getStrengthProgress());
        model.addAttribute("strengthNextLevel", characterState.getStrengthNextLevel());
        model.addAttribute("agilityLevel", characterState.getAgilityProgressLevel());
        model.addAttribute("agility", characterState.getAgilityProgress());
        model.addAttribute("agilityNextLevel", characterState.getAgilityNextLevel());
        model.addAttribute("intelligenceLevel", characterState.getIntelligenceProgressLevel());
        model.addAttribute("intelligence", characterState.getIntelligenceProgress());
        model.addAttribute("intelligenceNextLevel", characterState.getIntelligenceNextLevel());
        model.addAttribute("shield", characterState.getShieldPoints());

        return model;
    }

    public boolean isCharacterDead() {
        return characterState.getCurrentHp() <= 0;
    }

    public void updateStrengthProgress(Integer strengthEpx) {
        Progress progress = ps.getCharacterByName(characterState.getCharacterName()).getProgress();
        if (characterState.getStrengthProgress() + strengthEpx < characterState.getStrengthNextLevel()) {
            progress.setStrengthProgress(progress.getStrengthProgress() + strengthEpx);
            ps.updateProgress(progress);
            characterState.setStrengthProgress(progress.getStrengthProgress());
        } else {
            ps.updateProgress(progress
                    .setStrengthProgressLevel(progress.getStrengthProgressLevel() + 1)
                    .setStrengthProgress(progress.getStrengthProgress() + strengthEpx - characterState.getStrengthNextLevel()));
            defineCharacter(characterState.getCharacterName());
            LOGGER.info("Level Up!");
        }
    }

    public void updateAgilityProgress(Integer agilityExp) {
        Progress progress = ps.getCharacterByName(characterState.getCharacterName()).getProgress();
        if (characterState.getAgilityProgress() + agilityExp < characterState.getAgilityNextLevel()) {
            progress.setAgilityProgress(progress.getAgilityProgress() + agilityExp);
            ps.updateProgress(progress);
            characterState.setAgilityProgress(progress.getAgilityProgress());
        } else {
            ps.updateProgress(progress
                    .setAgilityProgressLevel(progress.getAgilityProgressLevel() + 1)
                    .setAgilityProgress(progress.getAgilityProgress() + agilityExp - characterState.getAgilityNextLevel()));
            defineCharacter(characterState.getCharacterName());
            LOGGER.info("Level Up!");
        }
    }

    public void updateIntelligenceProgress(Integer intelligenceExp) {
        Progress progress = ps.getCharacterByName(characterState.getCharacterName()).getProgress();
        if (characterState.getIntelligenceProgress() + intelligenceExp < characterState.getIntelligenceNextLevel()) {
            progress.setIntelligenceProgress(progress.getIntelligenceProgress() + intelligenceExp);
            ps.updateProgress(progress);
            characterState.setIntelligenceProgress(progress.getIntelligenceProgress());
        } else {
            ps.updateProgress(progress
                    .setIntelligenceProgressLevel(progress.getIntelligenceProgressLevel() + 1)
                    .setIntelligenceProgress(progress.getIntelligenceProgress() + intelligenceExp - characterState.getIntelligenceNextLevel()));
            defineCharacter(characterState.getCharacterName());
            LOGGER.info("Level Up!");
        }

    }

    public CharacterState defineCharacter(String characterName) {
        if (ps.getCharacterByName(characterName) != null) {
            LOGGER.info("Defined an Existing Character");
            return reloadCharacter(ps.getCharacterByName(characterName));
        }

        LOGGER.info("Defined a New Character");
        return reloadCharacter(ps.saveCharacter(new Character(
                characterName,
                new Progress(),
                new ActionBar())));
    }

    private CharacterState reloadCharacter(Character character) {
        LOGGER.info("Character was reloaded");
        characterState.setCharacterName(character.getCharacterName())
                .setLocation(character.getLocation())
                .setStrengthProgressLevel(character.getProgress().getStrengthProgressLevel())
                .setStrengthProgress(character.getProgress().getStrengthProgress())
                .setAgilityProgressLevel(character.getProgress().getAgilityProgressLevel())
                .setAgilityProgress(character.getProgress().getAgilityProgress())
                .setIntelligenceProgressLevel(character.getProgress().getIntelligenceProgressLevel())
                .setIntelligenceProgress(character.getProgress().getIntelligenceProgress())
                .setCharacterLevel(calculateCharacterLevel())
                .setSpell_4(character.getActionBar().getSpell_4() == null ? getDefaultSpell() : character.getActionBar().getSpell_4())
                .setSpell_5(character.getActionBar().getSpell_5() == null ? getDefaultSpell() : character.getActionBar().getSpell_5())
                .setSpell_6(character.getActionBar().getSpell_6() == null ? getDefaultSpell() : character.getActionBar().getSpell_6())
                .setSpell_7(character.getActionBar().getSpell_7() == null ? getDefaultSpell() : character.getActionBar().getSpell_7())
                .setSpell_8(character.getActionBar().getSpell_8() == null ? getDefaultSpell() : character.getActionBar().getSpell_8())
                .setSpell_9(character.getActionBar().getSpell_9() == null ? getDefaultSpell() : character.getActionBar().getSpell_9())
                .setSpell_10(character.getActionBar().getSpell_10() == null ? getDefaultSpell() : character.getActionBar().getSpell_10())
                .setSpell_11(character.getActionBar().getSpell_11() == null ? getDefaultSpell() : character.getActionBar().getSpell_11())
                .setSpell_12(character.getActionBar().getSpell_12() == null ? getDefaultSpell() : character.getActionBar().getSpell_12())
                .setHp(formula.getCharacterHp())
                .setCurrentHp(characterState.getHp())
                .setMp(formula.getCharacterMp())
                .setCurrentMp(characterState.getMp())
                .setCurrentEnergy(characterState.getEnergy())
                .setSpell_1(character.getActionBar().getSpell_1() == null ? getDefaultSpell(1) : character.getActionBar().getSpell_1())
                .setSpell_2(character.getActionBar().getSpell_2() == null ? getDefaultSpell(2) : character.getActionBar().getSpell_2())
                .setSpell_3(character.getActionBar().getSpell_3() == null ? getDefaultSpell(3) : character.getActionBar().getSpell_3());
        return characterState;
    }

    private Spell getDefaultSpell(Integer spellPlace) {
        return ps.getSpellById(spellPlace);
    }

    private Spell getDefaultSpell() {
        return ps.getSpellByName("Nospell");
    }

    private Integer calculateCharacterLevel() {
        return characterState.getStrengthProgressLevel() +
                characterState.getAgilityProgressLevel() +
                characterState.getIntelligenceProgressLevel() - 2;
    }

    @Autowired
    private void setPs(PersistenceService ps) {
        this.ps = ps;
    }

    @Autowired
    private void setCharacterState(CharacterState characterState) {
        this.characterState = characterState;
    }

    @Autowired
    private void setActionBarService(ActionBarService actionBarService) {
        this.actionBarService = actionBarService;
    }

    @Autowired
    private void setFormula(FormulaService formula) {
        this.formula = formula;
    }
}
