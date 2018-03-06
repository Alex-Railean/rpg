package com.endava.rpg.gp.services.state;

import com.endava.rpg.gp.statemodels.CharacterState;
import com.endava.rpg.persistence.models.ActionBar;
import com.endava.rpg.persistence.models.Character;
import com.endava.rpg.persistence.models.Progress;
import com.endava.rpg.persistence.models.Spell;
import com.endava.rpg.persistence.services.PersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service
public class CharacterStateService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CharacterStateService.class);

    @Autowired
    private PersistenceService PS;

    @Autowired
    private CharacterState CHAR_STATE;

    @Autowired
    private ActionBarService ACTION_BAR_SERVICE;

    // TODO: To investigate the problem
//    @Autowired
//    public CharacterStateService(PersistenceService PS, CharacterState characterState, ActionBarService actionBarService) {
//        this.PS = PS;
//        this.CHAR_STATE = characterState;
//        this.ACTION_BAR_SERVICE = actionBarService;
//    }

    @PostConstruct
    public Character saveTestCharacter() {
        if ((PS.getCharacterByName("Admin") == null)) {
            LOGGER.info("Test Admin User is Created");
            return PS.saveCharacter(new Character(
                    "Admin",
                    new Progress(),
                    new ActionBar()));

        }
        return PS.getCharacterByName("Admin");
    }

    public CharacterState defineCharacter(String characterName) {
        if (PS.getCharacterByName(characterName) != null) {
            return CHAR_STATE.defineCharacter(characterName);
        }
        return CHAR_STATE.defineNewCharacter(characterName);

    }

    public CharacterState getCharacterState() {
        return CHAR_STATE;
    }

    public CharacterState setNewBattle(Long battleId) {
        LOGGER.info("New Battle was Defined");
        return CHAR_STATE.setCurrentBattle(battleId);
    }

    public Long getBattle() {
        return CHAR_STATE.getCurrentBattle();
    }

    public CharacterState setNewLocation(String location) {
        LOGGER.info("New Location -> " + location);
        PS.updateCharacter(PS.getCharacterByName(CHAR_STATE.getCharacterName()).setLocation(location));
        return CHAR_STATE.setLocation(location);
    }

    public String getLocation() {
        return CHAR_STATE.getLocation();
    }

    public Model getCharacterModel(Model model) {
        Map<Integer, Spell> actionBar = ACTION_BAR_SERVICE.getActionBarMap();
        model.addAttribute("characterName", CHAR_STATE.getCharacterName());
        model.addAttribute("characterLevel", CHAR_STATE.getCharacterLevel());
        model.addAttribute("hp", CHAR_STATE.getHp());
        model.addAttribute("currentHp", CHAR_STATE.getCurrentHp());
        model.addAttribute("mp", CHAR_STATE.getMp());
        model.addAttribute("currentMp", CHAR_STATE.getCurrentMp());
        model.addAttribute("energy", CHAR_STATE.getEnergy());
        model.addAttribute("currentEnergy", CHAR_STATE.getCurrentEnergy());
        model.addAttribute("actionBar", actionBar);
        model.addAttribute("strengthLevel", CHAR_STATE.getStrengthProgressLevel());
        model.addAttribute("strength", CHAR_STATE.getStrengthProgress());
        model.addAttribute("strengthNextLevel", CHAR_STATE.getStrengthNextLevel());
        model.addAttribute("agilityLevel", CHAR_STATE.getAgilityProgressLevel());
        model.addAttribute("agility", CHAR_STATE.getAgilityProgress());
        model.addAttribute("agilityNextLevel", CHAR_STATE.getAgilityNextLevel());
        model.addAttribute("intelligenceLevel", CHAR_STATE.getIntelligenceProgressLevel());
        model.addAttribute("intelligence", CHAR_STATE.getIntelligenceProgress());
        model.addAttribute("intelligenceNextLevel", CHAR_STATE.getIntelligenceNextLevel());
        model.addAttribute("shield", CHAR_STATE.getShieldPoints());

        return model;
    }

    public boolean isCharacterDead() {
        return CHAR_STATE.getCurrentHp() <= 0;
    }

    public void updateStrengthProgress(Integer strengthEpx) {
        Progress progress = PS.getCharacterByName(CHAR_STATE.getCharacterName()).getProgress();
        if (CHAR_STATE.getStrengthProgress() + strengthEpx < CHAR_STATE.getStrengthNextLevel()) {
            progress.setStrengthProgress(progress.getStrengthProgress() + strengthEpx);
            PS.updateProgress(progress);
            CHAR_STATE.setStrengthProgress(progress.getStrengthProgress());
        } else {
            PS.updateProgress(progress
                    .setStrengthProgressLevel(progress.getStrengthProgressLevel() + 1)
                    .setStrengthProgress(progress.getStrengthProgress() + strengthEpx - CHAR_STATE.getStrengthNextLevel()));
            defineCharacter(CHAR_STATE.getCharacterName());
            LOGGER.info("Level Up!");
        }
    }

    public void updateAgilityProgress(Integer agilityExp) {
        Progress progress = PS.getCharacterByName(CHAR_STATE.getCharacterName()).getProgress();
        if (CHAR_STATE.getAgilityProgress() + agilityExp < CHAR_STATE.getAgilityNextLevel()) {
            progress.setAgilityProgress(progress.getAgilityProgress() + agilityExp);
            PS.updateProgress(progress);
            CHAR_STATE.setAgilityProgress(progress.getAgilityProgress());
        } else {
            PS.updateProgress(progress
                    .setAgilityProgressLevel(progress.getAgilityProgressLevel() + 1)
                    .setAgilityProgress(progress.getAgilityProgress() + agilityExp - CHAR_STATE.getAgilityNextLevel()));
            defineCharacter(CHAR_STATE.getCharacterName());
            LOGGER.info("Level Up!");
        }
    }

    public void updateIntelligenceProgress(Integer intelligenceExp) {
        Progress progress = PS.getCharacterByName(CHAR_STATE.getCharacterName()).getProgress();
        if (CHAR_STATE.getIntelligenceProgress() + intelligenceExp < CHAR_STATE.getIntelligenceNextLevel()) {
            progress.setIntelligenceProgress(progress.getIntelligenceProgress() + intelligenceExp);
            PS.updateProgress(progress);
            CHAR_STATE.setIntelligenceProgress(progress.getIntelligenceProgress());
        } else {
            PS.updateProgress(progress
                    .setIntelligenceProgressLevel(progress.getIntelligenceProgressLevel() + 1)
                    .setIntelligenceProgress(progress.getIntelligenceProgress() + intelligenceExp - CHAR_STATE.getIntelligenceNextLevel()));
            defineCharacter(CHAR_STATE.getCharacterName());
            LOGGER.info("Level Up!");
        }

    }
}
